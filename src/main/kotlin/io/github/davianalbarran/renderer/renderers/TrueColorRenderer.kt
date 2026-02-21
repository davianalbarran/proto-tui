package io.github.davianalbarran.renderer.renderers

import io.github.davianalbarran.renderer.RendererConstants
import io.github.davianalbarran.renderer.TuiColor
import io.github.davianalbarran.renderer.interfaces.IRenderer

class TrueColorRenderer: IRenderer {
    override var backgroundColor: TuiColor? = null
    override var foregroundColor: TuiColor? = null
    /*
        True Color Format:
            foreground: prefix + 38;2;[r];[g];[b] + suffix
            background: prefix + 48;2;[r];[g];[b] + suffix
     */
    override fun init() {
        TODO("Not yet implemented")
    }

    override fun reset() { println(RendererConstants.RESET_CODE) }
}