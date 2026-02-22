package io.github.davianalbarran.main

import io.github.davianalbarran.renderer.RendererFactory
import io.github.davianalbarran.renderer.TuiColor
import io.github.davianalbarran.tuicomponents.components.TuiLabel
import io.github.davianalbarran.tuicomponents.components.TuiPanel

fun getTerminalSize() {
    val width = System.getProperty("os.width")?.toInt() ?: 80 // Default width
    val height = System.getProperty("os.height")?.toInt() ?: 24 // Default height
    println("Width: $width, Height: $height")
}

fun setTerminalSize(width: Int, height: Int) {
    System.setProperty("os.width", width.toString())
    System.setProperty("os.height", height.toString())
}

fun main() {
//    val factory = RendererFactory(isTrueColorSupported = true, isAnsi256Supported = true) // true color factory
//    val factory = RendererFactory(isTrueColorSupported = false, isAnsi256Supported = true) // ansi256 factory
    val factory = RendererFactory(isTrueColorSupported = false, isAnsi256Supported = false) // basic factory

    val bgColor = TuiColor(0x00C400)
    val fgColor = TuiColor(0xFFFFFF)

    val renderer = factory.apply {
        withBackgroundColor(bgColor)
        withForegroundColor(fgColor)
        withDimensions(200, 100)
    }.build()

    val helloWorldComponent = TuiLabel("Hello World").apply {
        backgroundColor = bgColor
        foregroundColor = fgColor
    }

    val welcomeComponent = TuiLabel("Welcome to my TUI library").apply {
        backgroundColor = TuiColor(0xC47E00)
    }

    val helloWorldPanel = TuiPanel(helloWorldComponent, welcomeComponent).apply {
        backgroundColor = bgColor
        foregroundColor = fgColor
    }

    renderer.init()
    helloWorldPanel.children.forEach {
        renderer.renderComponent(it)
    }
    renderer.resetStyle()
}