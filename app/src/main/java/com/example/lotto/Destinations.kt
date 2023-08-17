package com.example.lotto

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Pages
import androidx.compose.ui.graphics.vector.ImageVector


interface Destinations {

    val icon: ImageVector
    val route: String

}


object  FastLoto: Destinations{

    override val icon: ImageVector
        get() = Icons.Filled.Pages
    override val route: String
        get() = "fastloto"
}


val BottonTableRow = listOf(FastLoto)