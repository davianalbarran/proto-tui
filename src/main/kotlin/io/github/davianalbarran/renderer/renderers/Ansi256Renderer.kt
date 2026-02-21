package io.github.davianalbarran.renderer.renderers

import io.github.davianalbarran.renderer.RendererConstants
import io.github.davianalbarran.renderer.TuiColor
import io.github.davianalbarran.renderer.interfaces.IRenderer

class Ansi256Renderer: IRenderer {
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
    override fun init() {
        TODO("Not yet implemented")
    }
    override fun reset() { println(RendererConstants.RESET_CODE) }
}