package io.github.davianalbarran.tuicomponents.components

import io.github.davianalbarran.renderer.TuiColor
import io.github.davianalbarran.tuicomponents.ATuiComponent
import io.github.davianalbarran.tuicomponents.interfaces.ITuiComponent

class TuiPanel(
    val initChildren: List<ITuiComponent>?,
    override var foregroundColor: TuiColor?,
    override var backgroundColor: TuiColor?
) : ATuiComponent(
    foregroundColor,
    backgroundColor
) {
    constructor(): this(listOf(), null, null)
    constructor(initChild: ITuiComponent): this(listOf(initChild), null, null)
    constructor(vararg children: ITuiComponent): this(children.toList(), null, null)

    init {
        if (initChildren != null) { addAll(initChildren) }
    }
}