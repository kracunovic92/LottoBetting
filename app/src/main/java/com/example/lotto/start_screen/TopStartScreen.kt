package com.example.lotto.start_screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.selection.selectableGroup
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.outlined.ContentPasteSearch
import androidx.compose.material.icons.outlined.Paid
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.lotto.ui.theme.DarkBlue


@Preview
@Composable
fun TopStartBar(){

    TopAppBar(
        title = { Text("My Number") },
        actions = {

            IconButton(onClick = { /*TODO*/ }) {
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(Icons.Outlined.ContentPasteSearch, contentDescription = "Reset")
                    Text("Prijava")
                }
            }

        }

    )
}

@Composable
fun TopTabStart(){

    Row(
        Modifier
            .fillMaxWidth()
            .selectableGroup()
            .background(color = DarkBlue)
        ,
        verticalAlignment = Alignment.CenterVertically,
    ){
        androidx.compose.material3.IconButton(onClick = { /*TODO*/ }) {
            Icon(imageVector = Icons.Filled.Star, contentDescription = null)
        }
        androidx.compose.material3.Text(
            modifier = Modifier.width(100.dp),
            textAlign = TextAlign.Center,
            text = "Moj broj"
        )
        androidx.compose.material3.IconButton(
            modifier = Modifier.padding(start = 50.dp),
            onClick = { /*TODO*/ }) {

            Icon(imageVector = Icons.Outlined.Paid, contentDescription = "Your money")

        }
        Button(
            modifier = Modifier.fillMaxWidth(),
            onClick = { /*TODO*/ }) {
            androidx.compose.material3.Text(text = "Prijava")
        }

    }
}
