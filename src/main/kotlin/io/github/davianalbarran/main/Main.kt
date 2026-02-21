package io.github.davianalbarran.main

import io.github.davianalbarran.renderer.RendererFactory
import io.github.davianalbarran.renderer.TuiColor

fun main() {
    val factory = RendererFactory(isTrueColorSupported = false, isAnsi256Supported = false)
    val renderer = factory.withBackgroundColor(TuiColor(0x00C400)).withForegroundColor(TuiColor(0xFFFFFF)).build()

    renderer.init()
    println("Hello World!")
    renderer.reset()
}