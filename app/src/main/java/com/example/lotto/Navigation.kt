package com.example.lotto

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.lotto.event_screen.EventScreen
import com.example.lotto.models.StartModel
import com.example.lotto.start_screen.StartScreen

@Composable
fun Navigation(

    navController: NavHostController,
    modifier: Modifier = Modifier,
    viewModel: StartModel
    ){

    NavHost(
        navController = navController,
        startDestination = "StartScreen",
        modifier = modifier
    ){
        composable(route = "StartScreen" ){
            StartScreen(viewModel = viewModel , navController =navController )

        }
        composable(route = "OfferScreen/{eventId}",
            arguments = listOf(navArgument("eventId"){type = NavType.StringType})) {
                backStackEntry ->
                val eventid = backStackEntry.arguments?.getString("eventId")
                EventScreen(eventid, navController = navController, viewModel = viewModel)
        }
    }

}

fun NavHostController.navigateSingleTopTo(route: String) =
    this.navigate(route){

        popUpTo(
            this@navigateSingleTopTo.graph.findStartDestination().id
        ){
            saveState = true
        }

        launchSingleTop = true

        restoreState = true
    }
