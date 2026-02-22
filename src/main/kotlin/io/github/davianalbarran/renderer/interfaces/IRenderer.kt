package io.github.davianalbarran.renderer.interfaces

import io.github.davianalbarran.renderer.TuiColor
import io.github.davianalbarran.tuicomponents.components.TuiLabel
import io.github.davianalbarran.tuicomponents.interfaces.ITuiComponent

interface IRenderer {
    var width: Int
    var height: Int
    var backgroundColor: TuiColor?
    var foregroundColor: TuiColor?

    fun resetStyle()

    /**
     * Set up general background and foreground colors.
     */
    fun init()

    /**
     * Use to set background and foreground colors
     */
    fun setBgFgColors(backgroundColor: TuiColor?, foregroundColor: TuiColor?)

    fun setBgColor(backgroundColor: TuiColor?)

    fun setFgColor(foregroundColor: TuiColor?)

    fun renderComponent(component: ITuiComponent)

    /**
     * Clear terminal with color
     */
    fun clearWithColor(backgroundColor: TuiColor?)
}