package io.github.davianalbarran.renderer.interfaces

import io.github.davianalbarran.renderer.TuiColor

interface IRenderer {
    var backgroundColor: TuiColor?
    var foregroundColor: TuiColor?

    fun reset()

    /**
     * Below method is meant to set up general background and foreground colors.
     */
    fun init()
}