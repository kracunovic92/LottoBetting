package com.example.lotto

import android.annotation.SuppressLint
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.animateColor
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.MutableTransitionState
import androidx.compose.animation.core.animateDp
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.tween
import androidx.compose.animation.core.updateTransition
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.Blue
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.lotto.data.CompleteOffer
import com.example.lotto.data.OfferNextNHours
import com.example.lotto.data.PriorityLottoOffer
import com.example.lotto.start_screen.BottomStartScreen
import com.example.lotto.start_screen.MiddleStartScreen
import com.example.lotto.start_screen.TopStartBar
import com.example.lotto.start_screen.TopTabStart


val EXPAND_ANIMATION_DURATION = 100
val EXPANSTION_TRANSITION_DURATION = 100
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun StartScreen(
    viewModel: StartModel,
) {
    Scaffold(
        topBar = { TopTabStart() },
        content = {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(top = 50.dp,
                        bottom = 50.dp/* Desired top padding */)
            ) {
                MiddleStartScreen(viewModel = viewModel)
            }
        },
        bottomBar = { BottomStartScreen(viewModel = viewModel)}
    )
}