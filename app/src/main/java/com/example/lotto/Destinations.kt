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

object Talon: Destinations{
    override val route: String
        get() = "Talon"
}

object DodatneIgre:Destinations{
    override val route: String
        get() = "Dodatne Igre"
}


val screen_list = listOf(Talon, DodatneIgre)
