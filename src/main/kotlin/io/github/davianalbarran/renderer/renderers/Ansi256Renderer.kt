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

class Ansi256Renderer(val initWidth: Int?, val initHeight: Int?): IRenderer {
    override var width: Int = initWidth ?: 400
    override var height: Int = initHeight ?: 500
    override var backgroundColor: TuiColor? = null
    override var foregroundColor: TuiColor? = null
    /*
    8-bit Color Format:
        foreground: prefix + 38:5:[color] + suffix
        background: prefix + 48:5:[color] + suffix

             0 -   7:  standard colors (as in ESC [ 30–37 m)
             8 -  15:  high intensity colors (as in ESC [ 90–97 m)
            16 - 231:  6 × 6 × 6 cube (216 colors): 16 + 36 × r + 6 × g + b (0 ≤ r, g, b ≤ 5)
           232 - 255:  grayscale from dark to light in 24 steps
     */

    fun Ansi256Color.getAnsiCode(): String {
        return "${this.ordinal}"
    }

    override fun init() {
        print("${RendererConstants.SGR_PREFIX}2J")
        clearWithColor(backgroundColor)
    }

    override fun setBgFgColors(backgroundColor: TuiColor?, foregroundColor: TuiColor?) {
        val ansi256BackgroundColor = backgroundColor?.let { getAnsi256ColorFromTuiColor(it) }
            ?: if (this.backgroundColor != null) {
                getAnsi256ColorFromTuiColor(this.backgroundColor!!)
            } else Ansi256Color.STAND_BLACK

        val ansi256ForegroundColor = foregroundColor?.let { getAnsi256ColorFromTuiColor(it) }
            ?: if (this.backgroundColor != null) {
                getAnsi256ColorFromTuiColor(this.backgroundColor!!)
            } else Ansi256Color.HI_WHITE

        val backgroundSetupString = "${RendererConstants.SGR_SET_BACKGROUND_256}:${ansi256BackgroundColor.getAnsiCode()}"
        val foregroundSetupString = "${RendererConstants.SGR_SET_FOREGROUND_256}:${ansi256ForegroundColor.getAnsiCode()}"

        print("${RendererConstants.SGR_PREFIX}${backgroundSetupString}${RendererConstants.SGR_SUFFIX}")
        print("${RendererConstants.SGR_PREFIX}${foregroundSetupString}${RendererConstants.SGR_SUFFIX}")
    }

    override fun setBgColor(backgroundColor: TuiColor?) {
        val ansi256BackgroundColor = backgroundColor?.let { getAnsi256ColorFromTuiColor(it) }
            ?: if (this.backgroundColor != null) {
                getAnsi256ColorFromTuiColor(this.backgroundColor!!)
            } else Ansi256Color.STAND_BLACK

        val backgroundSetupString = "${RendererConstants.SGR_SET_BACKGROUND_256}:${ansi256BackgroundColor.getAnsiCode()}"
        print("${RendererConstants.SGR_PREFIX}${backgroundSetupString}${RendererConstants.SGR_SUFFIX}")
    }

    override fun setFgColor(foregroundColor: TuiColor?) {
        val ansi256ForegroundColor = foregroundColor?.let { getAnsi256ColorFromTuiColor(it) }
            ?: if (this.backgroundColor != null) {
                getAnsi256ColorFromTuiColor(this.backgroundColor!!)
            } else Ansi256Color.HI_WHITE

        val foregroundSetupString = "${RendererConstants.SGR_SET_FOREGROUND_256}:${ansi256ForegroundColor.getAnsiCode()}"
        print("${RendererConstants.SGR_PREFIX}${foregroundSetupString}${RendererConstants.SGR_SUFFIX}")
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
            print("${RendererConstants.SGR_PREFIX}0${RendererConstants.SGR_SUFFIX}")
        }
        print("${RendererConstants.SGR_PREFIX}H")
    }

    override fun resetStyle() { println(RendererConstants.RESET_CODE) }

    companion object {
        // colors
        enum class Ansi256Color(override val r: Int, override val g: Int, override val b: Int): IRgbColor {
            // standard colors
            STAND_BLACK(0, 0, 0),
            STAND_RED(0xCD, 0, 0),
            STAND_GREEN(0, 0xCD, 0),
            STAND_YELLOW(0xCD, 0xCD, 0),
            STAND_BLUE(0, 0, 0xEE),
            STAND_MAGENTA(0xCD, 0, 0xCD),
            STAND_CYAN(0, 0xCD, 0xCD),
            STAND_WHITE(0xE5, 0xE5, 0xE5),

            // high intensity colors
            HI_GRAY(0x7F, 0x7F, 0x7F),
            HI_RED(0xFF, 0, 0),
            HI_GREEN(0, 0xFF, 0),
            HI_YELLOW(0xFF, 0xFF, 0),
            HI_BLUE(0x5C, 0x5C, 0xFF),
            HI_MAGENTA(0xFF, 0, 0xFF),
            HI_CYAN(0x00, 0xFF, 0xFF),
            HI_WHITE(0xFF, 0xFF, 0xFF),

            // 216 colors
            RGB_00_00_00(0x00, 0x00, 0x00),
            RGB_00_00_5F(0x00, 0x00, 0x5F),
            RGB_00_00_87(0x00, 0x00, 0x87),
            RGB_00_00_AF(0x00, 0x00, 0xAF),
            RGB_00_00_D7(0x00, 0x00, 0xD7),
            RGB_00_00_FF(0x00, 0x00, 0xFF),
            RGB_00_5F_00(0x00, 0x5F, 0x00),
            RGB_00_5F_5F(0x00, 0x5F, 0x5F),
            RGB_00_5F_87(0x00, 0x5F, 0x87),
            RGB_00_5F_AF(0x00, 0x5F, 0xAF),
            RGB_00_5F_D7(0x00, 0x5F, 0xD7),
            RGB_00_5F_FF(0x00, 0x5F, 0xFF),
            RGB_00_87_00(0x00, 0x87, 0x00),
            RGB_00_87_5F(0x00, 0x87, 0x5F),
            RGB_00_87_87(0x00, 0x87, 0x87),
            RGB_00_87_AF(0x00, 0x87, 0xAF),
            RGB_00_87_D7(0x00, 0x87, 0xD7),
            RGB_00_87_FF(0x00, 0x87, 0xFF),
            RGB_00_AF_00(0x00, 0xAF, 0x00),
            RGB_00_AF_5F(0x00, 0xAF, 0x5F),
            RGB_00_AF_87(0x00, 0xAF, 0x87),
            RGB_00_AF_AF(0x00, 0xAF, 0xAF),
            RGB_00_AF_D7(0x00, 0xAF, 0xD7),
            RGB_00_AF_FF(0x00, 0xAF, 0xFF),
            RGB_00_D7_00(0x00, 0xD7, 0x00),
            RGB_00_D7_5F(0x00, 0xD7, 0x5F),
            RGB_00_D7_87(0x00, 0xD7, 0x87),
            RGB_00_D7_AF(0x00, 0xD7, 0xAF),
            RGB_00_D7_D7(0x00, 0xD7, 0xD7),
            RGB_00_D7_FF(0x00, 0xD7, 0xFF),
            RGB_00_FF_00(0x00, 0xFF, 0x00),
            RGB_00_FF_5F(0x00, 0xFF, 0x5F),
            RGB_00_FF_87(0x00, 0xFF, 0x87),
            RGB_00_FF_AF(0x00, 0xFF, 0xAF),
            RGB_00_FF_D7(0x00, 0xFF, 0xD7),
            RGB_00_FF_FF(0x00, 0xFF, 0xFF),
            RGB_5F_00_00(0x5F, 0x00, 0x00),
            RGB_5F_00_5F(0x5F, 0x00, 0x5F),
            RGB_5F_00_87(0x5F, 0x00, 0x87),
            RGB_5F_00_AF(0x5F, 0x00, 0xAF),
            RGB_5F_00_D7(0x5F, 0x00, 0xD7),
            RGB_5F_00_FF(0x5F, 0x00, 0xFF),
            RGB_5F_5F_00(0x5F, 0x5F, 0x00),
            RGB_5F_5F_5F(0x5F, 0x5F, 0x5F),
            RGB_5F_5F_87(0x5F, 0x5F, 0x87),
            RGB_5F_5F_AF(0x5F, 0x5F, 0xAF),
            RGB_5F_5F_D7(0x5F, 0x5F, 0xD7),
            RGB_5F_5F_FF(0x5F, 0x5F, 0xFF),
            RGB_5F_87_00(0x5F, 0x87, 0x00),
            RGB_5F_87_5F(0x5F, 0x87, 0x5F),
            RGB_5F_87_87(0x5F, 0x87, 0x87),
            RGB_5F_87_AF(0x5F, 0x87, 0xAF),
            RGB_5F_87_D7(0x5F, 0x87, 0xD7),
            RGB_5F_87_FF(0x5F, 0x87, 0xFF),
            RGB_5F_AF_00(0x5F, 0xAF, 0x00),
            RGB_5F_AF_5F(0x5F, 0xAF, 0x5F),
            RGB_5F_AF_87(0x5F, 0xAF, 0x87),
            RGB_5F_AF_AF(0x5F, 0xAF, 0xAF),
            RGB_5F_AF_D7(0x5F, 0xAF, 0xD7),
            RGB_5F_AF_FF(0x5F, 0xAF, 0xFF),
            RGB_5F_D7_00(0x5F, 0xD7, 0x00),
            RGB_5F_D7_5F(0x5F, 0xD7, 0x5F),
            RGB_5F_D7_87(0x5F, 0xD7, 0x87),
            RGB_5F_D7_AF(0x5F, 0xD7, 0xAF),
            RGB_5F_D7_D7(0x5F, 0xD7, 0xD7),
            RGB_5F_D7_FF(0x5F, 0xD7, 0xFF),
            RGB_5F_FF_00(0x5F, 0xFF, 0x00),
            RGB_5F_FF_5F(0x5F, 0xFF, 0x5F),
            RGB_5F_FF_87(0x5F, 0xFF, 0x87),
            RGB_5F_FF_AF(0x5F, 0xFF, 0xAF),
            RGB_5F_FF_D7(0x5F, 0xFF, 0xD7),
            RGB_5F_FF_FF(0x5F, 0xFF, 0xFF),
            RGB_87_00_00(0x87, 0x00, 0x00),
            RGB_87_00_5F(0x87, 0x00, 0x5F),
            RGB_87_00_87(0x87, 0x00, 0x87),
            RGB_87_00_AF(0x87, 0x00, 0xAF),
            RGB_87_00_D7(0x87, 0x00, 0xD7),
            RGB_87_00_FF(0x87, 0x00, 0xFF),
            RGB_87_5F_00(0x87, 0x5F, 0x00),
            RGB_87_5F_5F(0x87, 0x5F, 0x5F),
            RGB_87_5F_87(0x87, 0x5F, 0x87),
            RGB_87_5F_AF(0x87, 0x5F, 0xAF),
            RGB_87_5F_D7(0x87, 0x5F, 0xD7),
            RGB_87_5F_FF(0x87, 0x5F, 0xFF),
            RGB_87_87_00(0x87, 0x87, 0x00),
            RGB_87_87_5F(0x87, 0x87, 0x5F),
            RGB_87_87_87(0x87, 0x87, 0x87),
            RGB_87_87_AF(0x87, 0x87, 0xAF),
            RGB_87_87_D7(0x87, 0x87, 0xD7),
            RGB_87_87_FF(0x87, 0x87, 0xFF),
            RGB_87_AF_00(0x87, 0xAF, 0x00),
            RGB_87_AF_5F(0x87, 0xAF, 0x5F),
            RGB_87_AF_87(0x87, 0xAF, 0x87),
            RGB_87_AF_AF(0x87, 0xAF, 0xAF),
            RGB_87_AF_D7(0x87, 0xAF, 0xD7),
            RGB_87_AF_FF(0x87, 0xAF, 0xFF),
            RGB_87_D7_00(0x87, 0xD7, 0x00),
            RGB_87_D7_5F(0x87, 0xD7, 0x5F),
            RGB_87_D7_87(0x87, 0xD7, 0x87),
            RGB_87_D7_AF(0x87, 0xD7, 0xAF),
            RGB_87_D7_D7(0x87, 0xD7, 0xD7),
            RGB_87_D7_FF(0x87, 0xD7, 0xFF),
            RGB_87_FF_00(0x87, 0xFF, 0x00),
            RGB_87_FF_5F(0x87, 0xFF, 0x5F),
            RGB_87_FF_87(0x87, 0xFF, 0x87),
            RGB_87_FF_AF(0x87, 0xFF, 0xAF),
            RGB_87_FF_D7(0x87, 0xFF, 0xD7),
            RGB_87_FF_FF(0x87, 0xFF, 0xFF),
            RGB_AF_00_00(0xAF, 0x00, 0x00),
            RGB_AF_00_5F(0xAF, 0x00, 0x5F),
            RGB_AF_00_87(0xAF, 0x00, 0x87),
            RGB_AF_00_AF(0xAF, 0x00, 0xAF),
            RGB_AF_00_D7(0xAF, 0x00, 0xD7),
            RGB_AF_00_FF(0xAF, 0x00, 0xFF),
            RGB_AF_5F_00(0xAF, 0x5F, 0x00),
            RGB_AF_5F_5F(0xAF, 0x5F, 0x5F),
            RGB_AF_5F_87(0xAF, 0x5F, 0x87),
            RGB_AF_5F_AF(0xAF, 0x5F, 0xAF),
            RGB_AF_5F_D7(0xAF, 0x5F, 0xD7),
            RGB_AF_5F_FF(0xAF, 0x5F, 0xFF),
            RGB_AF_87_00(0xAF, 0x87, 0x00),
            RGB_AF_87_5F(0xAF, 0x87, 0x5F),
            RGB_AF_87_87(0xAF, 0x87, 0x87),
            RGB_AF_87_AF(0xAF, 0x87, 0xAF),
            RGB_AF_87_D7(0xAF, 0x87, 0xD7),
            RGB_AF_87_FF(0xAF, 0x87, 0xFF),
            RGB_AF_AF_00(0xAF, 0xAF, 0x00),
            RGB_AF_AF_5F(0xAF, 0xAF, 0x5F),
            RGB_AF_AF_87(0xAF, 0xAF, 0x87),
            RGB_AF_AF_AF(0xAF, 0xAF, 0xAF),
            RGB_AF_AF_D7(0xAF, 0xAF, 0xD7),
            RGB_AF_AF_FF(0xAF, 0xAF, 0xFF),
            RGB_AF_D7_00(0xAF, 0xD7, 0x00),
            RGB_AF_D7_5F(0xAF, 0xD7, 0x5F),
            RGB_AF_D7_87(0xAF, 0xD7, 0x87),
            RGB_AF_D7_AF(0xAF, 0xD7, 0xAF),
            RGB_AF_D7_D7(0xAF, 0xD7, 0xD7),
            RGB_AF_D7_FF(0xAF, 0xD7, 0xFF),
            RGB_AF_FF_00(0xAF, 0xFF, 0x00),
            RGB_AF_FF_5F(0xAF, 0xFF, 0x5F),
            RGB_AF_FF_87(0xAF, 0xFF, 0x87),
            RGB_AF_FF_AF(0xAF, 0xFF, 0xAF),
            RGB_AF_FF_D7(0xAF, 0xFF, 0xD7),
            RGB_AF_FF_FF(0xAF, 0xFF, 0xFF),
            RGB_D7_00_00(0xD7, 0x00, 0x00),
            RGB_D7_00_5F(0xD7, 0x00, 0x5F),
            RGB_D7_00_87(0xD7, 0x00, 0x87),
            RGB_D7_00_AF(0xD7, 0x00, 0xAF),
            RGB_D7_00_D7(0xD7, 0x00, 0xD7),
            RGB_D7_00_FF(0xD7, 0x00, 0xFF),
            RGB_D7_5F_00(0xD7, 0x5F, 0x00),
            RGB_D7_5F_5F(0xD7, 0x5F, 0x5F),
            RGB_D7_5F_87(0xD7, 0x5F, 0x87),
            RGB_D7_5F_AF(0xD7, 0x5F, 0xAF),
            RGB_D7_5F_D7(0xD7, 0x5F, 0xD7),
            RGB_D7_5F_FF(0xD7, 0x5F, 0xFF),
            RGB_D7_87_00(0xD7, 0x87, 0x00),
            RGB_D7_87_5F(0xD7, 0x87, 0x5F),
            RGB_D7_87_87(0xD7, 0x87, 0x87),
            RGB_D7_87_AF(0xD7, 0x87, 0xAF),
            RGB_D7_87_D7(0xD7, 0x87, 0xD7),
            RGB_D7_87_FF(0xD7, 0x87, 0xFF),
            RGB_D7_AF_00(0xD7, 0xAF, 0x00),
            RGB_D7_AF_5F(0xD7, 0xAF, 0x5F),
            RGB_D7_AF_87(0xD7, 0xAF, 0x87),
            RGB_D7_AF_AF(0xD7, 0xAF, 0xAF),
            RGB_D7_AF_D7(0xD7, 0xAF, 0xD7),
            RGB_D7_AF_FF(0xD7, 0xAF, 0xFF),
            RGB_D7_D7_00(0xD7, 0xD7, 0x00),
            RGB_D7_D7_5F(0xD7, 0xD7, 0x5F),
            RGB_D7_D7_87(0xD7, 0xD7, 0x87),
            RGB_D7_D7_AF(0xD7, 0xD7, 0xAF),
            RGB_D7_D7_D7(0xD7, 0xD7, 0xD7),
            RGB_D7_D7_FF(0xD7, 0xD7, 0xFF),
            RGB_D7_FF_00(0xD7, 0xFF, 0x00),
            RGB_D7_FF_5F(0xD7, 0xFF, 0x5F),
            RGB_D7_FF_87(0xD7, 0xFF, 0x87),
            RGB_D7_FF_AF(0xD7, 0xFF, 0xAF),
            RGB_D7_FF_D7(0xD7, 0xFF, 0xD7),
            RGB_D7_FF_FF(0xD7, 0xFF, 0xFF),
            RGB_FF_00_00(0xFF, 0x00, 0x00),
            RGB_FF_00_5F(0xFF, 0x00, 0x5F),
            RGB_FF_00_87(0xFF, 0x00, 0x87),
            RGB_FF_00_AF(0xFF, 0x00, 0xAF),
            RGB_FF_00_D7(0xFF, 0x00, 0xD7),
            RGB_FF_00_FF(0xFF, 0x00, 0xFF),
            RGB_FF_5F_00(0xFF, 0x5F, 0x00),
            RGB_FF_5F_5F(0xFF, 0x5F, 0x5F),
            RGB_FF_5F_87(0xFF, 0x5F, 0x87),
            RGB_FF_5F_AF(0xFF, 0x5F, 0xAF),
            RGB_FF_5F_D7(0xFF, 0x5F, 0xD7),
            RGB_FF_5F_FF(0xFF, 0x5F, 0xFF),
            RGB_FF_87_00(0xFF, 0x87, 0x00),
            RGB_FF_87_5F(0xFF, 0x87, 0x5F),
            RGB_FF_87_87(0xFF, 0x87, 0x87),
            RGB_FF_87_AF(0xFF, 0x87, 0xAF),
            RGB_FF_87_D7(0xFF, 0x87, 0xD7),
            RGB_FF_87_FF(0xFF, 0x87, 0xFF),
            RGB_FF_AF_00(0xFF, 0xAF, 0x00),
            RGB_FF_AF_5F(0xFF, 0xAF, 0x5F),
            RGB_FF_AF_87(0xFF, 0xAF, 0x87),
            RGB_FF_AF_AF(0xFF, 0xAF, 0xAF),
            RGB_FF_AF_D7(0xFF, 0xAF, 0xD7),
            RGB_FF_AF_FF(0xFF, 0xAF, 0xFF),
            RGB_FF_D7_00(0xFF, 0xD7, 0x00),
            RGB_FF_D7_5F(0xFF, 0xD7, 0x5F),
            RGB_FF_D7_87(0xFF, 0xD7, 0x87),
            RGB_FF_D7_AF(0xFF, 0xD7, 0xAF),
            RGB_FF_D7_D7(0xFF, 0xD7, 0xD7),
            RGB_FF_D7_FF(0xFF, 0xD7, 0xFF),
            RGB_FF_FF_00(0xFF, 0xFF, 0x00),
            RGB_FF_FF_5F(0xFF, 0xFF, 0x5F),
            RGB_FF_FF_87(0xFF, 0xFF, 0x87),
            RGB_FF_FF_AF(0xFF, 0xFF, 0xAF),
            RGB_FF_FF_D7(0xFF, 0xFF, 0xD7),
            RGB_FF_FF_FF(0xFF, 0xFF, 0xFF),

            // grayscale colors
            GRAY_08(0x08, 0x08, 0x08),
            GRAY_12(0x12, 0x12, 0x12),
            GRAY_1C(0x1C, 0x1C, 0x1C),
            GRAY_26(0x26, 0x26, 0x26),
            GRAY_30(0x30, 0x30, 0x30),
            GRAY_3A(0x3A, 0x3A, 0x3A),
            GRAY_44(0x44, 0x44, 0x44),
            GRAY_4E(0x4E, 0x4E, 0x4E),
            GRAY_58(0x58, 0x58, 0x58),
            GRAY_62(0x62, 0x62, 0x62),
            GRAY_6C(0x6C, 0x6C, 0x6C),
            GRAY_76(0x76, 0x76, 0x76),
            GRAY_80(0x80, 0x80, 0x80),
            GRAY_8A(0x8A, 0x8A, 0x8A),
            GRAY_94(0x94, 0x94, 0x94),
            GRAY_9E(0x9E, 0x9E, 0x9E),
            GRAY_A8(0xA8, 0xA8, 0xA8),
            GRAY_B2(0xB2, 0xB2, 0xB2),
            GRAY_BC(0xBC, 0xBC, 0xBC),
            GRAY_C6(0xC6, 0xC6, 0xC6),
            GRAY_D0(0xD0, 0xD0, 0xD0),
            GRAY_DA(0xDA, 0xDA, 0xDA),
            GRAY_E4(0xE4, 0xE4, 0xE4),
            GRAY_EE(0xEE, 0xEE, 0xEE),
        }

        fun getAnsi256ColorFromTuiColor(tuiColor: TuiColor): Ansi256Color {
            return RendererUtils.returnClosestColor(tuiColor, Ansi256Color.entries) ?: Ansi256Color.STAND_RED
        }
    }
}