package com.example.lotto.start_screen

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.lotto.models.StartModel


const val EXPANSTION_TRANSITION_DURATION = 100
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun StartScreen(
    viewModel: StartModel,
    navController: NavController
) {
    Scaffold(
        topBar = { TopTabStart() },
        content = {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(top = 50.dp,
                        bottom = 50.dp/* Desired top padding */)
            ) {
                MiddleStartScreen(viewModel = viewModel, navController = navController)
            }
        },
        bottomBar = { BottomStartScreen(viewModel = viewModel)}
    )
}