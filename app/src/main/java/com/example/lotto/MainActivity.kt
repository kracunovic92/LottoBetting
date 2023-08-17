package com.example.lotto

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.selection.selectableGroup
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.outlined.Paid
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.lotto.ui.theme.LottoTheme
import com.example.lotto.ui.theme.PurpleGrey80

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            LottoTheme {
                //LottoApp()
                StartScreen()
            }
        }
    }

    @Composable
    fun LottoAppStart(){



    }
    @Preview
    @Composable
    fun TopTabStart(){

        Row(
            Modifier
                .fillMaxWidth()
                .selectableGroup()
                .background(color = Color.Green)
            ,
            verticalAlignment = Alignment.CenterVertically,
        ){
            IconButton(onClick = { /*TODO*/ }) {
                Icon(imageVector = Icons.Filled.Star, contentDescription = null)
            }
            Text(modifier = Modifier.width(100.dp),
                textAlign = TextAlign.Center,
                text = "Moj broj")
            IconButton(
                modifier = Modifier.padding(start = 50.dp),
                onClick = { /*TODO*/ }) {
                
                Icon(imageVector = Icons.Outlined.Paid, contentDescription = "Your money" )
                
            }
            Button(
                modifier = Modifier.fillMaxWidth(),
                onClick = { /*TODO*/ }) {
                Text(text = "Prijava")
            }

        }
    }


    @Preview
    @Composable
    fun BottomStartSceen(){

        var list = listOf("Brze igre","Sledecih 24 sata","Svi dani")
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .selectableGroup()
                .height(30.dp)
                .background(color = PurpleGrey80),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween

        ) {
            list.forEach { item ->
                Text(
                    text = item,
                    color = Color.Blue
                )
            }

        }
    }


    @Composable
    fun LottoApp(){

        val navController = rememberNavController()
        val currentBackStack by navController.currentBackStackEntryAsState()
        val currentDestination = currentBackStack?.destination
        val currentScreen =  BottonTableRow.find{it.route == currentDestination?.route} ?: FastLoto


        Scaffold(
            bottomBar = {
                BottomRowSceen(
                    allScreens = BottonTableRow,
                    onTabSelected = {
                        newScreen ->
                        navController.navigateSingleTopTo(newScreen.route)
                    },
                    currentScreen = currentScreen
                )
            }

        ){
                innerPadding ->
                Navigation(
                navController = navController,
                modifier = Modifier
                    .fillMaxWidth()
                    .background(color = PurpleGrey80)
                    .padding(innerPadding),
            )
        }

    }
}


