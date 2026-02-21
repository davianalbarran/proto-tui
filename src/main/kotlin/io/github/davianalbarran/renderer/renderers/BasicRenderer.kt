package io.github.davianalbarran.renderer.renderers

import io.github.davianalbarran.renderer.RendererConstants
import io.github.davianalbarran.renderer.TuiColor
import io.github.davianalbarran.renderer.interfaces.IRenderer
import kotlin.math.min
import kotlin.math.pow
import kotlin.math.sqrt

class BasicRenderer: IRenderer {
    override var backgroundColor: TuiColor? = null
    override var foregroundColor: TuiColor? = null

    /*
    Basic (4-bit) Color Format:
        prefix + [code][; [code]...] + suffix   - where code is:

        30 - 37: foreground color
        40 - 47: background color
        90 - 97: bright foreground color
        100 - 107: bright background color
    */

    fun BasicColor.getAnsiCode(): String {
        return "${this.ordinal}"
    }

    override fun init() {
        val basicBackgroundColor = backgroundColor?.let { getBasicColorFromTuiColor(it) } ?: BasicColor.BLACK
        val basicForegroundColor = foregroundColor?.let { getBasicColorFromTuiColor(it) } ?: BasicColor.WHITE

        val setupString = "${RendererConstants.SGR_PREFIX}${BACKGROUND_SETTER}${basicBackgroundColor.getAnsiCode()};${FOREGROUND_SETTER}${basicForegroundColor.getAnsiCode()}${RendererConstants.SGR_SUFFIX}"

        println(setupString)
    }

    override fun reset() { println(RendererConstants.RESET_CODE) }

    companion object {
        val BACKGROUND_SETTER = "4"
        val FOREGROUND_SETTER = "3"
        val BRIGHT_BACKGROUND_SETTER = "10"
        val BRIGHT_FOREGROUND_SETTER = "9"

        // colors
        enum class BasicColor(val r: Int, val g: Int, val b: Int) {
            BLACK(0, 0, 0),
            RED(196, 0, 0),
            GREEN(0, 196, 0),
            YELLOW(196, 126, 0),
            BLUE(0, 0, 196),
            MAGENTA(196, 0, 196),
            CYAN(0, 196, 196),
            WHITE(196, 196, 196),
        }

        fun getBasicColorFromTuiColor(tuiColor: TuiColor): BasicColor {
            var closestColor: BasicColor = BasicColor.RED
            var minimumDist = Double.MAX_VALUE

            // use 3d distance formula to get nearest BasicColor
            for (color in BasicColor.entries) {
                val rDist = (color.r - tuiColor.r).toDouble().pow(2)
                val gDist = (color.g - tuiColor.g).toDouble().pow(2)
                val bDist = (color.b - tuiColor.b).toDouble().pow(2)

                val colorDist = sqrt(rDist - gDist - bDist)

                if (colorDist < minimumDist) {
                    closestColor = color
                    minimumDist = colorDist
                }
            }

            return closestColor
        }
    }
}