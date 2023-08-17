package com.example.lotto

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable

@Composable
fun Navigation(

    navController: NavHostController,
    modifier: Modifier = Modifier,
    ){

    NavHost(
        navController = navController,
        startDestination = FastLoto.route,
        modifier = modifier
    ){
        composable(route = FastLoto.route ){
            FastLotoScreen(

            )
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