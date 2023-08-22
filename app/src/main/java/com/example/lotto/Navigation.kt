package com.example.lotto

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.lotto.start_screen.StartScreen

@Composable
fun Navigation(

    navController: NavHostController,
    modifier: Modifier = Modifier,
    ){

    NavHost(
        navController = navController,
        startDestination = "StartScreen",
        modifier = modifier
    ){
        composable(route = "StartScreen" ){
            StartScreen(viewModel = StartModel() , navController )
        }
        composable(route = "OfferScreen/{eventId}") { backStackEntry ->
            val eventid = backStackEntry.arguments?.getString("eventId")
            Log.e("Event", eventid.toString())
            OfferScreen(eventid,navController)
        }
    }

}


fun NavHostController.navigateSingleTopTo(route: String){

    this.navigate(route){

        popUpTo(
            this@navigateSingleTopTo.graph.findStartDestination().id
        ){
            saveState = true
        }

        launchSingleTop = true

        restoreState = true
    }
}