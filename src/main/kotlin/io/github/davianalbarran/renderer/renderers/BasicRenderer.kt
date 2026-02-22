package io.github.davianalbarran.renderer.renderers

import io.github.davianalbarran.renderer.IRgbColor
import io.github.davianalbarran.renderer.RendererConstants
import io.github.davianalbarran.renderer.RendererUtils
import io.github.davianalbarran.renderer.TuiColor
import io.github.davianalbarran.renderer.interfaces.IRenderer
import io.github.davianalbarran.tuicomponents.components.TuiLabel
import io.github.davianalbarran.tuicomponents.interfaces.ITuiComponent
import kotlin.math.pow
import kotlin.math.sqrt

class BasicRenderer(val initWidth: Int?, val initHeight: Int?): IRenderer {
    override var width: Int = initWidth ?: 400
    override var height: Int = initHeight ?: 500
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
        print("${RendererConstants.SGR_PREFIX}2J")
        clearWithColor(backgroundColor)
    }

    override fun setBgFgColors(backgroundColor: TuiColor?, foregroundColor: TuiColor?) {
        val basicBackgroundColor = backgroundColor?.let { getBasicColorFromTuiColor(it) }
            ?: if (this.backgroundColor != null) {
                getBasicColorFromTuiColor(this.backgroundColor!!)
            } else BasicColor.BLACK

        val basicForegroundColor = foregroundColor?.let { getBasicColorFromTuiColor(it) }
            ?: if (this.foregroundColor != null) {
                getBasicColorFromTuiColor(this.foregroundColor!!)
            } else BasicColor.WHITE

        val setupString = "${RendererConstants.SGR_PREFIX}${if (!basicBackgroundColor.isBright) BACKGROUND_SETTER else BRIGHT_BACKGROUND_SETTER}${basicBackgroundColor.getAnsiCode()};${if (!basicForegroundColor.isBright) FOREGROUND_SETTER else BRIGHT_FOREGROUND_SETTER}${basicForegroundColor.getAnsiCode()}${RendererConstants.SGR_SUFFIX}"

        print(setupString)
    }

    override fun setBgColor(backgroundColor: TuiColor?) {
        val basicBackgroundColor = backgroundColor?.let { getBasicColorFromTuiColor(it) }
            ?: if (this.backgroundColor != null) {
                getBasicColorFromTuiColor(this.backgroundColor!!)
            } else BasicColor.BLACK

        print("${RendererConstants.SGR_PREFIX}${if (!basicBackgroundColor.isBright) BACKGROUND_SETTER else BRIGHT_BACKGROUND_SETTER}${basicBackgroundColor.getAnsiCode()}${RendererConstants.SGR_SUFFIX}")
    }

    override fun setFgColor(foregroundColor: TuiColor?) {
        val basicForegroundColor = foregroundColor?.let { getBasicColorFromTuiColor(it) }
            ?: if (this.foregroundColor != null) {
                getBasicColorFromTuiColor(this.foregroundColor!!)
            } else BasicColor.WHITE

        print("${RendererConstants.SGR_PREFIX}${if (!basicForegroundColor.isBright) FOREGROUND_SETTER else BRIGHT_FOREGROUND_SETTER}${basicForegroundColor.getAnsiCode()}${RendererConstants.SGR_SUFFIX}")
    }

    override fun renderComponent(component: ITuiComponent) {
        setBgColor(component.backgroundColor)
        setFgColor(component.foregroundColor)

        if (component is TuiLabel) {
            val padded = component.labelVal?.padEnd(width, ' ')
            print("$padded")
            resetStyle()
        }
    }

    override fun clearWithColor(backgroundColor: TuiColor?) {
        print("${RendererConstants.SGR_PREFIX}H")
        repeat(height) {
            setBgColor(backgroundColor)
            print(" ".repeat(width))
            resetStyle()
        }
        print("${RendererConstants.SGR_PREFIX}H")
    }

    override fun resetStyle() { println(RendererConstants.RESET_CODE) }

    companion object {
        val BACKGROUND_SETTER = "4"
        val FOREGROUND_SETTER = "3"
        val BRIGHT_BACKGROUND_SETTER = "10"
        val BRIGHT_FOREGROUND_SETTER = "9"

        // colors
        enum class BasicColor(override val r: Int, override val g: Int, override val b: Int, val isBright: Boolean): IRgbColor {
            BLACK(0, 0, 0, false),
            RED(196, 0, 0, false),
            GREEN(0, 196, 0, false),
            YELLOW(196, 126, 0, false),
            BLUE(0, 0, 196, false),
            MAGENTA(196, 0, 196, false),
            CYAN(0, 196, 196, false),
            WHITE(196, 196, 196, false),
            BRIGHT_BLACK(78, 78, 78, true),
            BRIGHT_RED(220, 78, 78, true),
            BRIGHT_GREEN(78, 220, 78, true),
            BRIGHT_YELLOW(243, 243, 78, true),
            BRIGHT_BLUE(78, 78, 220, true),
            BRIGHT_MAGENTA(243, 78, 243, true),
            BRIGHT_CYAN(78, 243, 243, true),
            BRIGHT_WHITE(255, 255, 255, true),
        }

        fun getBasicColorFromTuiColor(tuiColor: TuiColor): BasicColor {
            return RendererUtils.returnClosestColor(tuiColor, BasicColor.entries) ?: BasicColor.RED
        }
    }
}