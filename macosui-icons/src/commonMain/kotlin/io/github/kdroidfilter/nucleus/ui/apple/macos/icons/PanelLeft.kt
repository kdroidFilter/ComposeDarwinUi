package io.github.kdroidfilter.nucleus.ui.apple.macos.icons

/*
MIT License

Copyright (c) 2022 WorkOS

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all
copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
SOFTWARE.

*/
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.group
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.unit.dp

val RadixPanelLeft: ImageVector
    get() {
        if (_RadixPanelLeft != null) return _RadixPanelLeft!!

        _RadixPanelLeft = ImageVector.Builder(
            name = "panel-left",
            defaultWidth = 24.dp,
            defaultHeight = 24.dp,
            viewportWidth = 19f,
            viewportHeight = 19f
        ).apply {
            group(translationX = 2f, translationY = 2f) {
                path(
                    fill = SolidColor(Color.Black)
                ) {
                moveTo(13.6533f, 2.00781f)
                curveTo(14.4097f, 2.08461f, 15f, 2.72334f, 15f, 3.5f)
                verticalLineTo(11.5f)
                lineTo(14.9922f, 11.6533f)
                curveTo(14.9205f, 12.3593f, 14.3593f, 12.9205f, 13.6533f, 12.9922f)
                lineTo(13.5f, 13f)
                horizontalLineTo(1.5f)
                lineTo(1.34668f, 12.9922f)
                curveTo(0.64069f, 12.9205f, 0.0794913f, 12.3593f, 0.0078125f, 11.6533f)
                lineTo(0f, 11.5f)
                verticalLineTo(3.5f)
                curveTo(0f, 2.72334f, 0.590277f, 2.08461f, 1.34668f, 2.00781f)
                lineTo(1.5f, 2f)
                horizontalLineTo(13.5f)
                lineTo(13.6533f, 2.00781f)
                close()
                moveTo(1.5f, 2.98438f)
                curveTo(1.21523f, 2.98438f, 0.984375f, 3.21523f, 0.984375f, 3.5f)
                verticalLineTo(11.5f)
                curveTo(0.984375f, 11.7848f, 1.21523f, 12.0156f, 1.5f, 12.0156f)
                horizontalLineTo(4f)
                verticalLineTo(2.98438f)
                horizontalLineTo(1.5f)
                close()
                moveTo(5f, 12.0156f)
                horizontalLineTo(13.5f)
                curveTo(13.7848f, 12.0156f, 14.0156f, 11.7848f, 14.0156f, 11.5f)
                verticalLineTo(3.5f)
                curveTo(14.0156f, 3.21523f, 13.7848f, 2.98438f, 13.5f, 2.98438f)
                horizontalLineTo(5f)
                verticalLineTo(12.0156f)
                close()
                }
            }
        }.build()

        return _RadixPanelLeft!!
    }

private var _RadixPanelLeft: ImageVector? = null

