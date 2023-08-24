package com.example.lotto.models

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.lotto.api_calls.RetrofitClient
import com.example.lotto.data.LottoOffer
import com.example.lotto.data.Offers
import com.example.lotto.start_screen.Options
import com.example.lotto.start_screen.SelectionOption
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Locale

class StartModel : ViewModel() {

    var data by mutableStateOf(Offers())
    var  _selected by mutableStateOf(Options.FAST)
    var  selectedEvent by mutableStateOf(LottoOffer())




    private val _options = listOf(
        SelectionOption("Fast",true, Options.FAST),
        SelectionOption("24h",false, Options.DAILY),
        SelectionOption("Complete", false, Options.FULL)
    ).toMutableList()

    val options: List<SelectionOption>
        get() = _options

    fun fetchAllData(){
        viewModelScope.launch {
            val response = RetrofitClient.getData()
            data = response
        }
    }

    fun selectionOptionSelected(selectionOption: SelectionOption){
        _options.forEach{it.selected = false}
        _options.find{it.option == selectionOption.option}?.selected = true
        val selected = _options.find{ it.selected }
        when (selected?.value) {
            Options.FAST -> _selected = Options.FAST
            Options.DAILY -> _selected = Options.DAILY
            Options.FULL -> _selected = Options.FULL
            else -> {}
        }

    }
    inline fun <reified T> currentOffer(): MutableList<T> {
        return when(_selected) {
            Options.FULL -> data.completeOffer as MutableList<T>
            Options.DAILY -> data.offerNextNHours as MutableList<T>
            Options.FAST -> data.priorityLottoOffer as MutableList<T>
        }
    }

      fun calculateTime(offer: LottoOffer): String? {
        val dataFormat = SimpleDateFormat("hh:mm:ss", Locale.getDefault())
        val currentTimestamp = System.currentTimeMillis()
        val time  = offer.time?: 0L
        val difference =  time-currentTimestamp
        return dataFormat.format(difference)

    }
    fun setEvent(eventid : LottoOffer){
        selectedEvent = eventid
    }



}