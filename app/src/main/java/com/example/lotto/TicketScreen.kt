package com.example.lotto

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.lotto.models.EventModel
import com.example.lotto.ui.theme.DarkBlack
import com.example.lotto.ui.theme.DarkBlue


@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun TicketScreen(
    eventModel: EventModel,
    navController: NavController
){



    var event = eventModel.event_info
    var tiket = eventModel.selected_numbers

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
        content = {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .background(color = DarkBlack),
            ) {

                event.name?.let { it1 -> Text(text = it1, modifier = Modifier.fillMaxWidth()) }
                Row(
                    modifier = Modifier.fillMaxWidth()
                ){
                    Text("Vreme izvlacenja: " + eventModel.makeDate())

                }
                SelectedNumbersView(eventModel.selected_numbers)

                Text(text = tiket.toString(), color = Color.Black)
            }

        }
    )



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