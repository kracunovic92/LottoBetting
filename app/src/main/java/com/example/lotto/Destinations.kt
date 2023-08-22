package com.example.lotto


interface Destinations {
    val route: String

}


object  Start: Destinations{

    override val route: String
        get() = "Start"

}

object Event: Destinations{
    override val route: String
        get() = "Event"
}
