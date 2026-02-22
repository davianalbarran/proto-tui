package io.github.davianalbarran.renderer.renderers

import io.github.davianalbarran.renderer.RendererConstants
import io.github.davianalbarran.renderer.TuiColor
import io.github.davianalbarran.renderer.interfaces.IRenderer
import io.github.davianalbarran.tuicomponents.components.TuiLabel
import io.github.davianalbarran.tuicomponents.interfaces.ITuiComponent

class TrueColorRenderer(val initWidth: Int?, val initHeight: Int?): IRenderer {
    override var width: Int = initWidth ?: 400
    override var height: Int = initHeight ?: 500
    override var backgroundColor: TuiColor? = null
    override var foregroundColor: TuiColor? = null
    /*
        True Color Format:
            foreground: prefix + 38;2;[r];[g];[b] + suffix
            background: prefix + 48;2;[r];[g];[b] + suffix
     */
    override fun init() {
        print("${RendererConstants.SGR_PREFIX}2J")
        clearWithColor(backgroundColor)
    }

    override fun setBgFgColors(backgroundColor: TuiColor?, foregroundColor: TuiColor?) {
        val backgroundColorString = "${backgroundColor?.r ?: this.backgroundColor?.r ?: 0};${backgroundColor?.g ?: this.backgroundColor?.g ?: 0};${backgroundColor?.b ?: this.backgroundColor?.b ?: 0}"
        val foregroundColorString = "${foregroundColor?.r ?: this.foregroundColor?.r ?: 0xFFFFFF};${foregroundColor?.g ?: this.foregroundColor?.g ?: 0xFFFFFF};${foregroundColor?.b ?: this.foregroundColor?.b ?: 0xFFFFFF}"

        print("${RendererConstants.SGR_PREFIX}${RendererConstants.SGR_SET_BACKGROUND_TRUE_COLOR};${backgroundColorString}${RendererConstants.SGR_SUFFIX}")
        print("${RendererConstants.SGR_PREFIX}${RendererConstants.SGR_SET_FOREGROUND_TRUE_COLOR};${foregroundColorString}${RendererConstants.SGR_SUFFIX}")
    }

    override fun setBgColor(backgroundColor: TuiColor?) {
        val backgroundColorString = "${backgroundColor?.r ?: this.backgroundColor?.r ?: 0};${backgroundColor?.g ?: this.backgroundColor?.g ?: 0};${backgroundColor?.b ?: this.backgroundColor?.b ?: 0}"
        print("${RendererConstants.SGR_PREFIX}${RendererConstants.SGR_SET_BACKGROUND_TRUE_COLOR};${backgroundColorString}${RendererConstants.SGR_SUFFIX}")
    }

    override fun setFgColor(foregroundColor: TuiColor?) {
        val foregroundColorString = "${foregroundColor?.r ?: this.foregroundColor?.r ?: 0xFFFFFF};${foregroundColor?.g ?: this.foregroundColor?.g ?: 0xFFFFFF};${foregroundColor?.b ?: this.foregroundColor?.b ?: 0xFFFFFF}"
        print("${RendererConstants.SGR_PREFIX}${RendererConstants.SGR_SET_FOREGROUND_TRUE_COLOR};${foregroundColorString}${RendererConstants.SGR_SUFFIX}")
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
}