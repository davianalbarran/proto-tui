package io.github.davianalbarran.renderer

import io.github.davianalbarran.renderer.interfaces.IRenderer
import io.github.davianalbarran.renderer.renderers.Ansi256Renderer
import io.github.davianalbarran.renderer.renderers.BasicRenderer
import io.github.davianalbarran.renderer.renderers.TrueColorRenderer

class RendererFactory(
    val isTrueColorSupported: Boolean,
    val isAnsi256Supported: Boolean,
) {
    var renderer: IRenderer = if (isTrueColorSupported) {
        TrueColorRenderer()
    } else if (isAnsi256Supported) {
        Ansi256Renderer()
    } else {
        BasicRenderer()
    }

    fun defaultRenderer(): RendererFactory {
        return this
    }

    fun withBackgroundColor(color: TuiColor): RendererFactory {
        renderer.backgroundColor = color
        return this
    }

    fun withForegroundColor(color: TuiColor): RendererFactory {
        renderer.foregroundColor = color
        return this
    }

    fun build(): IRenderer {
        return renderer // shouldn't have an issue since we have above line
    }
}