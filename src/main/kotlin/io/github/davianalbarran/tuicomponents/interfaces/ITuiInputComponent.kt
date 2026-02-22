package io.github.davianalbarran.tuicomponents.interfaces

interface ITuiInputComponent {
    val actions: List<Runnable>
    val label: String
    val inputType: TuiInputType

    /**
     * This method should be called when desired event occurs
     */
    fun trigger()
}

enum class TuiInputType {
    BUTTON,
    CHECKBOX,
    TEXT,
    DROPDOWN,
}