package com.example.lotto.data
import com.google.gson.annotations.SerializedName


data class OfferNextNHours (

    @SerializedName("gameId"     ) var gameId     : Int?                  = null,
    @SerializedName("gameName"   ) var gameName   : String?               = null,
    @SerializedName("lottoOffer" ) var lottoOffer : ArrayList<LottoOffer> = arrayListOf()

)