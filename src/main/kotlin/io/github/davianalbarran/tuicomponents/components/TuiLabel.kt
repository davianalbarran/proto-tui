package io.github.davianalbarran.tuicomponents.components

import io.github.davianalbarran.renderer.TuiColor
import io.github.davianalbarran.tuicomponents.ATuiComponent

class TuiLabel(
    var labelVal: String?,
    override var backgroundColor: TuiColor?,
    override var foregroundColor: TuiColor?
): ATuiComponent(backgroundColor, foregroundColor) {
    constructor(labelVal: String): this(labelVal, null, null)
}