package com.example.lotto.start_screen

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import com.example.lotto.start_screen.Options

class SelectionOption(val option: String, var initialSelectedValue: Boolean,val value: Options) {
    var selected by mutableStateOf(initialSelectedValue)
}