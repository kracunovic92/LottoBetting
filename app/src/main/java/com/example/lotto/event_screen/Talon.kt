package com.example.lotto.event_screen

import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.lifecycle.ViewModel
import androidx.navigation.NavHostController
import com.example.lotto.models.StartModel

@Composable
fun TalonScreen(navController: NavHostController, viewModel: StartModel) {
    Button(onClick = { /*TODO*/ }) {

        Text("Ovo je novi talon")

        val event = viewModel.selectedEvent

        Text(event.eventId.toString())
        Text(event.name.toString())

    }
}