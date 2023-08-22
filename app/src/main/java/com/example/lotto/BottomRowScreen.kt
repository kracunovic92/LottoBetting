package com.example.lotto
//
//import android.transition.Scene
//import androidx.compose.animation.animateColorAsState
//import androidx.compose.animation.animateContentSize
//import androidx.compose.animation.core.LinearEasing
//import androidx.compose.animation.core.tween
//import androidx.compose.foundation.background
//import androidx.compose.foundation.interaction.MutableInteractionSource
//import androidx.compose.foundation.layout.Row
//import androidx.compose.foundation.layout.Spacer
//import androidx.compose.foundation.layout.fillMaxWidth
//import androidx.compose.foundation.layout.height
//import androidx.compose.foundation.layout.padding
//import androidx.compose.foundation.layout.width
//import androidx.compose.foundation.selection.selectable
//import androidx.compose.foundation.selection.selectableGroup
//import androidx.compose.material.Icon
//import androidx.compose.material.Surface
//import androidx.compose.material.Text
//import androidx.compose.material.ripple.rememberRipple
//import androidx.compose.material3.MaterialTheme
//import androidx.compose.runtime.Composable
//import androidx.compose.runtime.getValue
//import androidx.compose.runtime.remember
//import androidx.compose.ui.Modifier
//import androidx.compose.ui.graphics.Color
//import androidx.compose.ui.graphics.vector.ImageVector
//import androidx.compose.ui.semantics.Role
//import androidx.compose.ui.semantics.clearAndSetSemantics
//import androidx.compose.ui.semantics.contentDescription
//import androidx.compose.ui.tooling.preview.Preview
//import androidx.compose.ui.unit.Dp
//import androidx.compose.ui.unit.dp
//import com.example.lotto.ui.theme.Purple40
//import java.util.Locale
//
//
//private val TabHeight = 56.dp
//private const val InactiveTabOpacity = 0.60f
//
//private const val TabFadeInAnimationDuration = 150
//private const val TabFadeInAnimationDelay = 100
//private const val TabFadeOutAnimationDuration = 100
//
//@Composable
//fun BottomRowSceen(
//
//    allScreens: List<Destinations>,
//    onTabSelected: (Destinations) -> Unit,
//    currentScreen: Destinations
//){
//    Surface(
//        Modifier
//            .height(56.dp)
//            .fillMaxWidth()
//            .background(color = Purple40)
//    ){
//        Row(Modifier.selectableGroup().background(color =  Purple40)){
//            allScreens.forEach { screen ->
//                ForecastTab(
//                    text = screen.route,
//                    onSelected = { onTabSelected(screen) },
//                    selected = currentScreen == screen
//                )
//            }
//        }
//    }
//}
//
//
//@Composable
//private fun ForecastTab(
//    text: String,
//    icon: ImageVector,
//    onSelected: ()-> Unit,
//    selected: Boolean
//){
//    val color = MaterialTheme.colorScheme.background
//    val durationMillis = if (selected) TabFadeInAnimationDuration else TabFadeOutAnimationDuration
//    val animSpec = remember {
//        tween<Color>(
//            durationMillis = durationMillis,
//            easing = LinearEasing,
//            delayMillis = TabFadeInAnimationDelay
//        )
//    }
//    val tabTintColor by animateColorAsState(
//        targetValue = if (selected) color else color.copy(alpha = InactiveTabOpacity),
//        animationSpec = animSpec
//    )
//    Row(
//        modifier = Modifier
//            .padding(16.dp)
//            .animateContentSize()
//            .height(TabHeight)
//            .selectable(
//                selected = selected,
//                onClick = onSelected,
//                role = Role.Tab,
//                interactionSource = remember { MutableInteractionSource() },
//                indication = rememberRipple(
//                    bounded = false,
//                    radius = Dp.Unspecified,
//                    color = Color.Black
//                )
//            )
//            .clearAndSetSemantics { contentDescription = text }
//    ) {
//        Icon(imageVector = icon, contentDescription = text, tint = Color.Black)
//        if (selected) {
//            Spacer(Modifier.width(12.dp))
//            Text(text.uppercase(Locale.getDefault()), color = Color.Black)
//        }
//    }
//}
//
//
//@Preview
//@Composable
//fun prevTableRow(){
//
//    BottomRowSceen(allScreens = BottonTableRow , onTabSelected = {
//
//    }, currentScreen = FastLoto)
//}
