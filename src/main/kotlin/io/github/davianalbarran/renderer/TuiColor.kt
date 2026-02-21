package io.github.davianalbarran.renderer

class TuiColor(val rgb: Int) {
    val r: Int get() = (rgb shr 16) and 0xFF
    val g: Int get() = (rgb shr 8) and 0xFF
    val b: Int get() = rgb and 0xFF
}