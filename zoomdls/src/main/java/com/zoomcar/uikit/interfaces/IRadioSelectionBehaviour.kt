package com.zoomcar.uikit.interfaces

interface IRadioSelectionBehaviour {
    fun onRadioButtonClicked(position: Int)
    fun onSelectPosition(position: Int)
}

interface IRadioButton {
    fun isSelected(): Boolean
}