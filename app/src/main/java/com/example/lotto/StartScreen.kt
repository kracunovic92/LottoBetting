package com.example.lotto

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.selection.selectableGroup
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModel
import com.example.lotto.api_calls.RetrofitClient
import com.example.lotto.ui.theme.PurpleGrey80
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch


@Composable
fun StartScreen(
    viewModel: StartModel
) {

    Surface(
        modifier = Modifier.fillMaxSize()
    ) {
        Column (modifier = Modifier.fillMaxSize()){
            MiddleStartScreen(viewModel)
            BottomStartScreen(viewModel)

        }
    }





}
@Composable
fun MiddleStartScreen(viewModel: StartModel){

    if (viewModel._selected == 1){
        var data = viewModel.data.priorityLottoOffer
        LazyColumn(){
            items(data){
                item ->
                Text(text = item.toString())
            }
        }

    }else if(viewModel._selected == 2){
        var data = viewModel.data.offerNextNHours
        LazyColumn(){
            items(data){
                    item ->
                Text(text = item.toString())
            }
        }
    }else if(viewModel._selected == 3){
        var data = viewModel.data.completeOffer
        LazyColumn(){
            items(data){
                    item ->
                Text(text = item.toString())
            }
        }
    }
}

@Composable
fun BottomStartScreen(viewModel:StartModel){
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(70.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Row(
            modifier = Modifier.fillMaxWidth()
        ) {
            SingleSelectionList(
                viewModel.options,
                onOptionClicked = viewModel::selectionOptionSelected
            )
        }
    }
}

@Composable
fun SingleSelectionList(options: List<SelectionOption>, onOptionClicked: (SelectionOption) -> Unit) {
    LazyRow (modifier = Modifier.fillMaxWidth()){
        items(options) { option -> SingleSelectionCard(option, onOptionClicked) }
    }
}

@Composable
fun SingleSelectionCard(selectionOption: SelectionOption, onOptionClicked: (SelectionOption) -> Unit) {
    Surface(modifier = Modifier
        .fillMaxSize()
        .padding(horizontal = 8.dp, vertical = 4.dp)) {
        Surface(
            modifier = Modifier
                .border(1.dp, MaterialTheme.colorScheme.primary, RectangleShape)
                .clickable(true, onClick = { onOptionClicked(selectionOption) }),
            color = if (selectionOption.selected) { MaterialTheme.colorScheme.primary } else { MaterialTheme.colorScheme.background },
        ) {
            Row(modifier = Modifier.padding(10.dp)) {
                Text(
                    text = selectionOption.option,
                    style = MaterialTheme.typography.bodySmall
                )
            }
        }
    }
}