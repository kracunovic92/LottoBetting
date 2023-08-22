package com.example.lotto

import androidx.compose.runtime.Composable


interface Destinations {

    val route: String
    val screen: @Composable () -> Unit


}


object  Start: Destinations{

    override val route: String
        get() = "Start"

    override val screen: @Composable () -> Unit
        get() = {TODO("Not yet implemented")}
}
