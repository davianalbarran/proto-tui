package io.github.davianalbarran.tuicomponents.interfaces

import io.github.davianalbarran.renderer.TuiColor

interface ITuiComponent {
    val componentIndex: MutableMap<ITuiComponent, Int>
    val children: MutableList<ITuiComponent>
    var componentName: String?
    var backgroundColor: TuiColor?
    var foregroundColor: TuiColor?

    fun getChild(i: Int): ITuiComponent?

    fun add(child: ITuiComponent)
    fun addAll(children: List<ITuiComponent>)

    fun remove(child: ITuiComponent): ITuiComponent?
    fun clearChildren()
}