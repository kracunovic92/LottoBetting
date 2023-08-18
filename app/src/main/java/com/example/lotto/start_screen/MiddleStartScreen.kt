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
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.min
import com.example.lotto.EXPAND_ANIMATION_DURATION
import com.example.lotto.EXPANSTION_TRANSITION_DURATION
import com.example.lotto.GameViewModel
import com.example.lotto.StartModel
import com.example.lotto.data.CompleteOffer
import com.example.lotto.data.LottoOffer
import com.example.lotto.data.OfferNextNHours
import com.example.lotto.data.PriorityLottoOffer
import kotlin.math.min

@Composable
fun MiddleStartScreen(viewModel: StartModel){

    val data = viewModel.currentOffer<Any>()
    val cardsModel = GameViewModel()
    val cards by cardsModel.cards.collectAsState()
    val expanded by cardsModel.expandedCardIdsList.collectAsState()

    LazyColumn(
    ){
        items(data){
                offer->
            when(offer){
                is PriorityLottoOffer -> {
                    gameList(offer,
                        onArrowClick = {cardsModel.onArrowClicked(offer.gameId)},
                        expanded = expanded.contains(offer.gameId))
                }
                is OfferNextNHours ->{
                    //TODO
                }
                is CompleteOffer -> {
                    //TODO
                }
            }
        }
    }
}

@SuppressLint("UnusedTransitionTargetStateParameter")
@Composable
fun gameList(offer: Any,
             onArrowClick: () -> Unit,
             expanded: Boolean,
) {

    val transitionState = remember {
        MutableTransitionState(expanded).apply {
            targetState = !expanded
        }
    }
    val transition = updateTransition(transitionState)

    val cardPaddingHorizontal by transition.animateDp({
        tween(durationMillis = EXPAND_ANIMATION_DURATION)
    }, label = "") {
        if (expanded) 30.dp else 20.dp
    }

    val cardRoundedCorners by transition.animateDp({
        tween(
            durationMillis = EXPAND_ANIMATION_DURATION,
            easing = FastOutSlowInEasing
        )
    }, label = "") {
        if (expanded) 0.dp else 16.dp
    }

    Card(
        shape = RoundedCornerShape(cardRoundedCorners),
        modifier = Modifier
            .fillMaxWidth()
            .padding(
                horizontal = cardPaddingHorizontal,
                vertical = 8.dp
            )
    ) {
        Column {
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.clickable{
                    onArrowClick.invoke()
                }
            ){
                when (offer) {
                    is PriorityLottoOffer -> {
                        Column {
                            Text(text = offer.gameName.toString())
                            ExpandableContent(visible = expanded, initialVisibility = expanded, offer = offer.lottoOffer)
                        }



                    }

                    is OfferNextNHours -> {
                        Text(text = offer.gameName.toString())
                        ExpandableContent(visible = expanded, initialVisibility = expanded, offer = offer.lottoOffer)

                    }

                    is CompleteOffer -> {
                        Text(text = offer.gameName.toString())
                        ExpandableContent(visible = expanded, initialVisibility = expanded, offer = offer.lottoOffer)

                    }
                }
            }
        }

    }


}

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun ExpandableContent(
    visible: Boolean = true,
    initialVisibility: Boolean = false,
    offer: ArrayList<LottoOffer>
) {
    var visibleLottoCount by remember { mutableStateOf(3) }
    val enterTransition = remember {
        expandVertically(
            expandFrom = Alignment.Top,
            animationSpec = tween(EXPANSTION_TRANSITION_DURATION)
        ) + fadeIn(
            initialAlpha = 0.3f,
            animationSpec = tween(EXPANSTION_TRANSITION_DURATION)
        )
    }
    val exitTransition = remember {
        shrinkVertically(
            // Expand from the top.
            shrinkTowards = Alignment.Top,
            animationSpec = tween(EXPANSTION_TRANSITION_DURATION)
        ) + fadeOut(
            // Fade in with the initial alpha of 0.3f.
            animationSpec = tween(EXPANSTION_TRANSITION_DURATION)
        )
    }
    AnimatedVisibility(
        visible = visible,
        initiallyVisible = initialVisibility,
        enter = enterTransition,
        exit = exitTransition
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 16.dp)
        ) {
            offer.take(visibleLottoCount).forEach { lottoOffer ->
                Text(text = lottoOffer.eventId.toString())
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

