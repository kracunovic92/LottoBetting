package com.example.lotto

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ExitToApp
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableDoubleStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.lotto.models.EventModel
import com.example.lotto.ui.theme.DarkBlack
import com.example.lotto.ui.theme.DarkBlue
import com.example.lotto.ui.theme.LightGrey


@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun TicketScreen(
    eventModel: EventModel,
    navController: NavController
){



    val event = eventModel.event_info
    var tiket = eventModel.selected_numbers
    var novac by remember { mutableStateOf("")}
    var calculated by remember { mutableDoubleStateOf(0.0) }
    var selectedTab by remember { mutableStateOf("normal") }
    var option by remember { mutableStateOf(emptyList<Int>()) }

    Scaffold(
        topBar = {
            TopAppBar(
                modifier = Modifier.background(color = DarkBlue),
                title = { Text(text = "Uplata tiketa") },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(
                            imageVector = Icons.Filled.ArrowBack,
                            contentDescription = null
                        )
                    }
                }
            )

        },

    ){
        innerPadding->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
                    .background(color = DarkBlack),
            ) {

                event.name?.let { it1 ->

                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(color = LightGrey),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment =  Alignment.CenterVertically
                    ){
                        Text(text = it1)
                        IconButton(onClick = {
                            eventModel.clearNumbers()
                            navController.popBackStack() }) {
                            Icon(
                                imageVector = Icons.Filled.ExitToApp,
                                contentDescription = null
                            )

                        }

                    }
                     }
                Row(
                    modifier = Modifier.fillMaxWidth()
                ){
                    Text("Vreme izvlacenja: " + eventModel.makeDate())

                }
                SelectedNumbersView(eventModel.selected_numbers)
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Button(
                        onClick = { selectedTab = "normal" },
                        colors = ButtonDefaults.run { buttonColors(containerColor = if (selectedTab == "normal") Color.Blue else Color.LightGray) }
                    ) {
                        Text("Normal")
                    }
                    Button(
                        onClick = { selectedTab = "system" },
                        colors = ButtonDefaults.buttonColors(containerColor = if (selectedTab == "system") Color.Blue else Color.LightGray)
                    ) {
                        Text("System")
                    }
                }
                if (selectedTab == "system") {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 16.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        LazyRow(
                            modifier = Modifier
                                .padding(vertical = 8.dp)
                                .fillMaxWidth(),
                            horizontalArrangement = Arrangement.spacedBy(8.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            items(eventModel.selected_numbers.size) { i ->
                                val isSelected = i+1 in option
                                Checkbox(
                                    checked = isSelected,
                                    onCheckedChange = { checked ->
                                        if (checked) {
                                            option += i+1
                                        } else {
                                            option -= i+1
                                        }
                                    }
                                )
                                Text((i+1).toString() + '/' + eventModel.selected_numbers.size)
                            }
                        }
                    }
                }


                Row(
                    modifier = Modifier.fillMaxWidth()
                ){
                    Column() {
                        Text(text = "Koliko uplacujem")
                        TextField(value = novac, onValueChange = {
                            novac = it
                            calculated = it.toDouble()
                        },
                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal), )

                    }
                    Column(
                        modifier = Modifier
                            .background(color = Color.LightGray)
                            .clickable { eventModel.uplatiTiket() }
                    ) {
                        Text("Preracunat povrat  ${eventModel.calculateWin(calculated,option)}", color = Color.Black)

                    }
                }

            }
    }



}

@Composable
fun BallView(item: Int) {
    val ballColor = when {
        item <= 10 -> Color.Blue
        item <= 20 -> Color.Green
        item <=  30 -> Color.Cyan
        item <= 40 -> Color.Yellow
        item <= 50 -> Color.Magenta
        item <= 60 -> Color.DarkGray
        else -> Color.Red
    }

    Box(
        modifier = Modifier
            .size(24.dp)
            .background(Color.Transparent)
            .padding(4.dp)
            .border(
                2.dp,
                ballColor,
                CircleShape
            )
            .aspectRatio(1f),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = item.toString(),
            fontSize = 14.sp, // Increase font size
            fontWeight = FontWeight.Bold, // Make the text bold
            textAlign = TextAlign.Center
        )
    }
}


@Composable
fun SelectedNumbersView(lista: List<Int>) {

    LazyVerticalGrid(
        columns = GridCells.Fixed(7),
        modifier = Modifier.height(140.dp),
        contentPadding = PaddingValues(4.dp),
        verticalArrangement = Arrangement.SpaceEvenly,
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        itemsIndexed(lista) { index, item ->
            BallView(item = item)
        }
    }
}



