package com.example.lotto.start_screen

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.MutableTransitionState
import androidx.compose.animation.core.tween
import androidx.compose.animation.core.updateTransition
import androidx.compose.animation.expandVertically
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.lotto.GameViewModel
import com.example.lotto.models.StartModel
import com.example.lotto.data.CompleteOffer
import com.example.lotto.data.LottoOffer
import com.example.lotto.data.OfferNextNHours
import com.example.lotto.data.PriorityLottoOffer
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import kotlin.math.min

@Composable
fun MiddleStartScreen(viewModel: StartModel, navController: NavController){

    val data = viewModel.currentOffer<Any>()
    val cardsModel = GameViewModel()
    val expanded by cardsModel.expandedCardIdsList.collectAsState()

    LazyColumn(
    modifier = Modifier.fillMaxWidth()
    ){
        items(data){
                offer->
            when(offer){
                is PriorityLottoOffer -> {
                    gameList(
                        navController,
                        viewModel,
                        offer,
                        onClick = {cardsModel.onClicked(offer.gameId)},
                        expanded = expanded.contains(offer.gameId))
                }
                is OfferNextNHours ->{
                    gameList(
                        navController,
                        viewModel,
                        offer,
                        onClick = {cardsModel.onClicked(offer.gameId)},
                        expanded = expanded.contains(offer.gameId)
                    )
                }
                is CompleteOffer -> {
                    gameList(
                        navController,
                        viewModel,
                        offer = offer,
                        onClick = { cardsModel.onClicked(offer.gameId) },
                        expanded = expanded.contains(offer.gameId))
                }
            }
        }
    }
}

@SuppressLint("UnusedTransitionTargetStateParameter")
@Composable
fun gameList(
    navController: NavController,
    viewModel: StartModel,
    offer: Any,
    onClick: () -> Unit,
    expanded: Boolean
) {

    val transitionState = remember {
        MutableTransitionState(expanded).apply {
            targetState = !expanded
        }
    }
    val transition = updateTransition(transitionState)


    Card(
        shape = RectangleShape,
        modifier = Modifier
            .fillMaxWidth()
            .padding(
                vertical = 8.dp
            )
    ) {
        Column {
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier
                    .clickable{
                    onClick.invoke()
                }
            ){
                when (offer) {
                    is PriorityLottoOffer -> {
                        Column {
                            Text(text = offer.gameName.toString())
                            ExpandableContent(viewModel = viewModel,visible = expanded, initialVisibility = expanded, offer = offer.lottoOffer, navController = navController)
                        }
                    }
                    is OfferNextNHours -> {
                        Column {
                            Text(text = offer.gameName.toString())
                            ExpandableContent(viewModel = viewModel, visible = expanded, initialVisibility = expanded, offer = offer.lottoOffer, navController = navController)
                        }
                    }
                    is CompleteOffer -> {
                        Column {
                            Text(text = offer.gameName.toString())
                            ExpandableContent(viewModel = viewModel,visible = expanded, initialVisibility = expanded, offer = offer.lottoOffer, navController = navController )
                        }
                    }
                }
            }
        }

    }


}

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun ExpandableContent(
    viewModel: StartModel,
    visible: Boolean = true,
    initialVisibility: Boolean = false,
    offer: ArrayList<LottoOffer>,
    navController: NavController

) {
    val currentTimestamp = System.currentTimeMillis()
    val coroutineScope = rememberCoroutineScope()
    val dateFormat = SimpleDateFormat("hh:mm", Locale.getDefault())
    val timeDifferences = remember { mutableStateMapOf<Long, String>() }
    var visibleLottoCount by remember { mutableStateOf(3) }
    val enterTransition = remember {
        expandVertically(
            expandFrom = Alignment.Top,
            animationSpec = tween(EXPANSTION_TRANSITION_DURATION)
        )
    }
    val exitTransition = remember {
        shrinkVertically(
            shrinkTowards = Alignment.Top,
            animationSpec = tween(EXPANSTION_TRANSITION_DURATION)
        )
    }

    LaunchedEffect(currentTimestamp) {
        coroutineScope.launch {
            while (true) {

                offer.take(visibleLottoCount).forEach { lottoOffer ->

                    val fixed_time = lottoOffer.time?: 0L
                    val time   = viewModel.calculateTime(lottoOffer).toString()
                    timeDifferences[fixed_time] = time
                }

                delay(1000)
            }
        }
    }

    AnimatedVisibility(
        visible = visible,
        initiallyVisible = initialVisibility,
        enter = enterTransition,
        exit = exitTransition
    ) {

        Column(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(color = Color.Blue),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(text = "Vreme izvlacenja")
                Text(text = "Preostalo vremena")
            }
            offer.take(visibleLottoCount).forEach {
                    lottoOffer ->
                                Row(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .clickable(
                                            onClick ={
                                                //mCon.startActivity(Intent(mCon, SecondActivity::class.java))
                                                Log.e("lottoOfferId", lottoOffer.eventId.toString())
                                                viewModel.setEvent(lottoOffer)
                                              navController.navigate("OfferScreen/${lottoOffer.eventId}")
                                            }
                                        )
                                    ,
                                    horizontalArrangement = Arrangement.SpaceBetween
                                ){
                                    Text(text = dateFormat.format(lottoOffer.time?.let { Date(it) }))
                                    Text(text =timeDifferences[lottoOffer.time ?: 0L] ?: "")
                                }

            }
            if (visibleLottoCount < offer.size) {
                Button(
                    onClick = {
                        visibleLottoCount = min(visibleLottoCount + 3, offer.size)
                    },
                    modifier = Modifier
                        .padding(top = 8.dp)
                ) {
                    Text(text = "Show More")
                }
            }
        }

       }


    }


