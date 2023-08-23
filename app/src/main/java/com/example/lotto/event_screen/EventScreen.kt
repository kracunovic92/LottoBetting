package com.example.lotto.event_screen

import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.lotto.Navigation
import com.example.lotto.Talon
import com.example.lotto.models.StartModel
import com.example.lotto.navigateSingleTopTo
import com.example.lotto.screen_list


@Composable
fun EventScreen(eventId: String?,
                navController: NavController,
                viewModel: StartModel) {

    val currentBackStack by navController.currentBackStackEntryAsState()
    val currentDestination = currentBackStack?.destination
    val currentScreen =  screen_list.find{it.route == currentDestination?.route} ?: Talon

    val navController2 = rememberNavController()
    Scaffold(
        topBar = {
            EventTopBar(
                allScreens = screen_list,
                onTabSelected = {
                    newScreen ->
                    navController2.navigateSingleTopTo(newScreen.route)
                },
                currentScreen = currentScreen,
                navController = navController,
                viewModel = viewModel
            )
        }
    ){
        innerPadding ->
        NavHost(
            navController2,
            startDestination = Talon.route,
            Modifier.padding(innerPadding)
        ){
            composable(route = "Talon"){
                TalonScreen(navController2,viewModel)
            }
        }
    }

}






