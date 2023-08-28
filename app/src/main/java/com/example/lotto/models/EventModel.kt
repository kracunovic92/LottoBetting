package com.example.lotto.models

import CalculationResult
import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.lotto.api_calls.RetrofitClient
import com.example.lotto.data.Event
import com.example.lotto.data.TicketSystemCombinationGroup
import getMaxPotentialPayment
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import kotlin.random.Random

class EventModel: ViewModel() {

    var event_info by mutableStateOf(Event())

    var max_numbers = 0
    var res by mutableStateOf(CalculationResult())

    var  selected_numbers by mutableStateOf(emptyList<Int>())
    val dateFormat = SimpleDateFormat("hh:mm", Locale.getDefault())

    val tiket by mutableStateOf(TicketSystemCombinationGroup())

    fun fetchEventData(gameId: String?, eventId: String?) {
        viewModelScope.launch {
            selected_numbers.drop(0)
            val game:Int  = gameId?.toInt() ?: 0
            val event: Int = eventId?.toInt()?: 0
            val response = RetrofitClient.getEventData(game,event)
            event_info = response
            max_numbers  = event_info.ballsToBet ?: 0
        }
    }

    fun makeDate(): String?{
       return event_info.time?.let { Date(it) }?.let { dateFormat.format(it) }
    }



    fun onClickNumber(number: Int){

        Log.e("selected", number.toString())
        Log.e("all", selected_numbers.toString())
        Log.e("Max numbers", max_numbers.toString())
        if (selected_numbers.contains(number)){
            selected_numbers = selected_numbers - listOf(number)
        }else{
            val balls = event_info.ballsToBet?:0
            if(selected_numbers.size < balls) {
                selected_numbers = selected_numbers + listOf(number)
            }else{
                Log.e("Previse brojeva","Greska")
            }
        }
    }

    fun clearNumbers(){
        selected_numbers = selected_numbers.drop(0)
    }

    fun getRandomNumbers( n: Int) {
        val randomSet =  mutableSetOf<Int>()
        while (randomSet.size < n) {
            val randomNumber = Random.nextInt(1, event_info.ballsTotalNumber?.plus(1) ?: 0)
            randomSet.add(randomNumber)
        }

        selected_numbers = selected_numbers.drop(0)
        selected_numbers = randomSet.toList()

    }
    fun calculateWin(uplata: Double, option: List<Int>): Double {

        val n = selected_numbers.size
        tiket.payin = uplata
        tiket.fromHowMany = event_info.ballsToBet?:0
        tiket.howMany = n


        var r = getMaxPotentialPayment(event_info,0,selected_numbers,option,uplata,res)

        return r
    }

    fun uplatiTiket(): Unit {

    }
}
