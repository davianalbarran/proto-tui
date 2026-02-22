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
        TrueColorRenderer(null, null)
    } else if (isAnsi256Supported) {
        Ansi256Renderer(null, null)
    } else {
        BasicRenderer(null, null)
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

    fun withDimensions(width: Int, height: Int): RendererFactory {
        renderer.width = width
        renderer.height = height
        return this
    }

    fun withWidth(width: Int): RendererFactory {
        renderer.width = width
        return this
    }

    fun withHeight(height: Int): RendererFactory {
        renderer.height = height
        return this
    }

    fun build(): IRenderer {
        return renderer // shouldn't have an issue since we have above line
    }
}