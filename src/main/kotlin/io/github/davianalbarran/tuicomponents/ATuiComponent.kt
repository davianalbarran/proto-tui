package io.github.davianalbarran.tuicomponents

import io.github.davianalbarran.renderer.TuiColor
import io.github.davianalbarran.tuicomponents.interfaces.ITuiComponent

abstract class ATuiComponent(
    override val componentIndex: MutableMap<ITuiComponent, Int>,
    override val children: MutableList<ITuiComponent>,
    override var componentName: String?,
    override var backgroundColor: TuiColor?,
    override var foregroundColor: TuiColor?,
) : ITuiComponent {
    constructor() : this(
        HashMap(),
        ArrayList(),
        null,
        TuiColor(0),
        TuiColor(0xFFFFFF)
    )

    constructor(children: MutableList<ITuiComponent>) : this(
        HashMap(),
        ArrayList(),
        null,
        TuiColor(0),
        TuiColor(0xFFFFFF)
    ) {
        addAll(children)
    }

    constructor(backgroundColor: TuiColor?, foregroundColor: TuiColor?) : this(
        HashMap(),
        ArrayList(),
        null,
        backgroundColor,
        foregroundColor
    )

    override fun getChild(i: Int): ITuiComponent? {
        return children[i]
    }

    override fun add(child: ITuiComponent) {
        children.add(child)
        componentIndex[child] = children.size - 1
    }

    override fun addAll(children: List<ITuiComponent>) {
        children.forEach { add(it) }
    }

    override fun remove(child: ITuiComponent): ITuiComponent? {
        val index = componentIndex[child]
        return if (index != null) { children.removeAt(index) } else null
    }

    override fun clearChildren() {
        children.clear()
        componentIndex.clear()
    }
}