package com.example.lotto

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.lotto.api_calls.RetrofitClient
import com.example.lotto.data.CompleteOffer
import com.example.lotto.data.LottoOffer
import com.example.lotto.data.OfferNextNHours
import com.example.lotto.data.Offers
import com.example.lotto.data.PriorityLottoOffer
import kotlinx.coroutines.launch

class StartModel : ViewModel() {

    var data by mutableStateOf(Offers())
    var  _selected by mutableStateOf(Options.FAST)




    private val _options = listOf(
        SelectionOption("Brza",true, Options.FAST),
        SelectionOption("24h",false,Options.DAILY),
        SelectionOption("Completna", false,Options.FULL)
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
        if(selected?.value == Options.FAST)
            _selected = Options.FAST
        else if(selected?.value == Options.DAILY)
            _selected = Options.DAILY
        else if(selected?.value == Options.FULL)
            _selected = Options.FULL

    }
    inline fun <reified T> currentOffer(): MutableList<T> {
        return when(_selected) {
            Options.FULL -> data.completeOffer as MutableList<T>
            Options.DAILY -> data.offerNextNHours as MutableList<T>
            Options.FAST -> data.priorityLottoOffer as MutableList<T>
        }
    }

}