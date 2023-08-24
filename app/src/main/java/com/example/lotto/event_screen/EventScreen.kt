package com.example.lotto.event_screen

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.lotto.Talon
import com.example.lotto.models.EventModel
import com.example.lotto.navigateSingleTopTo
import com.example.lotto.screen_list


@Composable
fun EventScreen(gameid:String?,eventId: String?,
                navController: NavController) {

    val currentBackStack by navController.currentBackStackEntryAsState()
    val currentDestination = currentBackStack?.destination
    val currentScreen =  screen_list.find{it.route == currentDestination?.route} ?: Talon

    val viewModel = EventModel();
    viewModel.fetchEventData(gameid,eventId)


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
        },
        bottomBar = {
            Row(
                modifier =  Modifier.fillMaxWidth()
                    .height(60.dp),
                verticalAlignment = Alignment.CenterVertically

            ) {
                Box(
                    modifier = Modifier.width(50.dp),
                    contentAlignment = Alignment.Center
                ){
                    Text(text = viewModel.selected_numbers.size.toString(), color = Color.Black, textAlign = TextAlign.Center)
                }
                Text(text = "Preostalo vreme: "+ viewModel.event_info.allowBetTimeBefore)

            }
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






