package io.github.davianalbarran.renderer

import io.github.davianalbarran.renderer.renderers.BasicRenderer.Companion.BasicColor
import kotlin.math.pow
import kotlin.math.sqrt

object RendererUtils {
    enum class RendererColor() {
        BASIC_COLOR,
        ANSI256_COLOR,
    }

    fun <T> returnClosestColor(
        tuiColor: TuiColor,
        colorList: List<T>)
    : T? where T : Enum<T>, T : IRgbColor {
        var minimumDist = Double.MAX_VALUE
        var closestColor: T? = null

        // use 3d distance formula to get nearest BasicColor
        for (color in colorList) {
            val rDist = (color.r - tuiColor.r).toDouble().pow(2)
            val gDist = (color.g - tuiColor.g).toDouble().pow(2)
            val bDist = (color.b - tuiColor.b).toDouble().pow(2)

            val colorDist = sqrt(rDist - gDist - bDist)

            if (colorDist < minimumDist) {
                closestColor = color
                minimumDist = colorDist
            }
        }

        return closestColor
    }
}