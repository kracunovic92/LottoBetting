package com.example.lotto.start_screen

import android.annotation.SuppressLint
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.MutableTransitionState
import androidx.compose.animation.core.animateDp
import androidx.compose.animation.core.tween
import androidx.compose.animation.core.updateTransition
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.modifier.modifierLocalConsumer
import androidx.compose.ui.unit.dp
import com.example.lotto.EXPAND_ANIMATION_DURATION
import com.example.lotto.EXPANSTION_TRANSITION_DURATION
import com.example.lotto.GameViewModel
import com.example.lotto.StartModel
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
import kotlin.time.DurationUnit
import kotlin.time.toDuration

@Composable
fun MiddleStartScreen(viewModel: StartModel){

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
                        viewModel,
                        offer,
                        onClick = {cardsModel.onClicked(offer.gameId)},
                        expanded = expanded.contains(offer.gameId))
                }
                is OfferNextNHours ->{
                    gameList(
                        viewModel,
                        offer,
                        onClick = {cardsModel.onClicked(offer.gameId)},
                        expanded = expanded.contains(offer.gameId)
                        )
                }
                is CompleteOffer -> {
                    gameList(
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
            viewModel: StartModel,
            offer: Any,
             onClick: () -> Unit,
             expanded: Boolean,
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
                            ExpandableContent(viewModel = viewModel,visible = expanded, initialVisibility = expanded, offer = offer.lottoOffer)
                        }
                    }
                    is OfferNextNHours -> {
                        Column {
                            Text(text = offer.gameName.toString())
                            ExpandableContent(viewModel = viewModel, visible = expanded, initialVisibility = expanded, offer = offer.lottoOffer)
                        }
                    }
                    is CompleteOffer -> {
                        Column {
                            Text(text = offer.gameName.toString())
                            ExpandableContent(viewModel = viewModel,visible = expanded, initialVisibility = expanded, offer = offer.lottoOffer)
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
    offer: ArrayList<LottoOffer>
) {

    val currentTimestamp = System.currentTimeMillis()
    val coroutineScope = rememberCoroutineScope()
    val dateFormat = SimpleDateFormat("hh:mm", Locale.getDefault())
    var time by remember { mutableStateOf("0") }
    var visibleLottoCount by remember { mutableStateOf(3) }
    val enterTransition = remember {
        expandVertically(
            expandFrom = Alignment.Top,
            animationSpec = tween(EXPANSTION_TRANSITION_DURATION)
        )
    }
    val exitTransition = remember {
        shrinkVertically(
            // Expand from the top.
            shrinkTowards = Alignment.Top,
            animationSpec = tween(EXPANSTION_TRANSITION_DURATION)
        )
    }

    LaunchedEffect(currentTimestamp) {
        coroutineScope.launch {
            while (true) {

                offer.take(visibleLottoCount).forEach { lottoOffer ->

                    time  = viewModel.calculateTime(lottoOffer).toString()
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
                                            onClick = openOffer(lottoOffer)
                                        ),
                                    horizontalArrangement = Arrangement.SpaceBetween
                                ){
                                    Text(text = dateFormat.format(lottoOffer.time?.let { Date(it) }))
                                    Text(text = time)
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

@Composable
fun openOffer(lottoOffer: LottoOffer): () -> Unit {


    return {}
}

