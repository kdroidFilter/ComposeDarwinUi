#import <Cocoa/Cocoa.h>
#include <jni.h>
#include <math.h>

static const jint MIN_SIZE_PX = 4;
static const jint MAX_SIZE_PX = 1024;
static const int MAX_FIT_PASSES = 4;

static jint clampSize(jint sizePx) {
    if (sizePx < MIN_SIZE_PX) return MIN_SIZE_PX;
    if (sizePx > MAX_SIZE_PX) return MAX_SIZE_PX;
    return sizePx;
}

static NSString *nsStringFromJava(JNIEnv *env, jstring value) {
    if (value == NULL) return nil;
    const char *utf = (*env)->GetStringUTFChars(env, value, NULL);
    if (utf == NULL) return nil;
    NSString *result = [NSString stringWithUTF8String:utf];
    (*env)->ReleaseStringUTFChars(env, value, utf);
    return result;
}

static jbyteArray toJByteArray(JNIEnv *env, NSData *data) {
    if (data == nil || data.length == 0) return NULL;
    jsize len = (jsize)data.length;
    jbyteArray out = (*env)->NewByteArray(env, len);
    if (out == NULL) return NULL;
    (*env)->SetByteArrayRegion(env, out, 0, len, (const jbyte *)data.bytes);
    return out;
}

static NSImage *configuredImage(NSImage *image, CGFloat pointSize) {
    NSImageSymbolConfiguration *config =
        [NSImageSymbolConfiguration configurationWithPointSize:pointSize
                                                        weight:NSFontWeightRegular];
    NSImage *configured = [image imageWithSymbolConfiguration:config];
    return configured != nil ? configured : image;
}

// Rasterize at 1x into an explicit bitmap so GraalVM native-image (no Retina
// AppKit context) and the JVM produce the same pixel size.
static NSBitmapImageRep *rasterize(NSImage *image, CGFloat pointSize) {
    NSImage *configured = configuredImage(image, pointSize);
    NSSize srcSize = [configured size];
    if (srcSize.width <= 0 || srcSize.height <= 0) {
        srcSize = NSMakeSize(pointSize, pointSize);
        [configured setSize:srcSize];
    }

    NSInteger w = (NSInteger)llround((double)srcSize.width);
    NSInteger h = (NSInteger)llround((double)srcSize.height);
    if (w < 1) w = 1;
    if (h < 1) h = 1;

    NSBitmapImageRep *rep = [[NSBitmapImageRep alloc]
        initWithBitmapDataPlanes:NULL
                      pixelsWide:w
                     pixelsHigh:h
                  bitsPerSample:8
                samplesPerPixel:4
                       hasAlpha:YES
                       isPlanar:NO
                 colorSpaceName:NSCalibratedRGBColorSpace
                    bytesPerRow:0
                   bitsPerPixel:32];
    if (rep == nil) return nil;

    [NSGraphicsContext saveGraphicsState];
    NSGraphicsContext *ctx = [NSGraphicsContext graphicsContextWithBitmapImageRep:rep];
    [NSGraphicsContext setCurrentContext:ctx];
    [configured drawInRect:NSMakeRect(0, 0, (CGFloat)w, (CGFloat)h)
                  fromRect:NSZeroRect
                 operation:NSCompositingOperationSourceOver
                  fraction:1.0
            respectFlipped:YES
                     hints:nil];
    [NSGraphicsContext restoreGraphicsState];
    return rep;
}

JNIEXPORT jboolean JNICALL
Java_dev_nucleusframework_macoscompose_icons_NativeSFSymbolBridge_nativeExists(
    JNIEnv *env, jclass clazz, jstring name) {
    (void)clazz;
    @autoreleasepool {
        NSString *nsName = nsStringFromJava(env, name);
        if (nsName.length == 0) return JNI_FALSE;
        NSImage *image = [NSImage imageWithSystemSymbolName:nsName accessibilityDescription:nil];
        return image != nil ? JNI_TRUE : JNI_FALSE;
    }
}

JNIEXPORT jbyteArray JNICALL
Java_dev_nucleusframework_macoscompose_icons_NativeSFSymbolBridge_nativeLoadSymbol(
    JNIEnv *env, jclass clazz, jstring name, jint sizePx) {
    (void)clazz;
    @autoreleasepool {
        NSString *nsName = nsStringFromJava(env, name);
        if (nsName.length == 0) return NULL;

        NSImage *image = [NSImage imageWithSystemSymbolName:nsName accessibilityDescription:nil];
        if (image == nil) return NULL;

        jint target = clampSize(sizePx);
        CGFloat pointSize = (CGFloat)target;
        NSBitmapImageRep *rep = rasterize(image, pointSize);
        if (rep == nil) return NULL;

        for (int pass = 1; pass < MAX_FIT_PASSES; pass++) {
            NSInteger largest = MAX([rep pixelsWide], [rep pixelsHigh]);
            if (largest <= 0) break;
            CGFloat ratio = (CGFloat)target / (CGFloat)largest;
            if (fabs((double)ratio - 1.0) <= 0.08) break;
            pointSize *= ratio;
            if (pointSize < (CGFloat)MIN_SIZE_PX) pointSize = (CGFloat)MIN_SIZE_PX;
            if (pointSize > (CGFloat)MAX_SIZE_PX) pointSize = (CGFloat)MAX_SIZE_PX;
            NSBitmapImageRep *next = rasterize(image, pointSize);
            if (next == nil) break;
            rep = next;
        }

        NSData *png = [rep representationUsingType:NSBitmapImageFileTypePNG properties:@{}];
        return toJByteArray(env, png);
    }
}
