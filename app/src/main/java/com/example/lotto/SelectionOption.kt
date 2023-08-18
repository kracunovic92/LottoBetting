package com.example.lotto

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue

class SelectionOption(val option: String, var initialSelectedValue: Boolean,val value: Options) {
    var selected by mutableStateOf(initialSelectedValue)
}