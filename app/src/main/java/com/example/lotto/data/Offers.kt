package com.example.lotto.data


import com.google.gson.annotations.SerializedName
data class Offers (

    @SerializedName("priorityLottoOffer" ) var priorityLottoOffer : ArrayList<PriorityLottoOffer> = arrayListOf(),
    @SerializedName("offerNextNHours"    ) var offerNextNHours    : ArrayList<OfferNextNHours>    = arrayListOf(),
    @SerializedName("completeOffer"      ) var completeOffer      : ArrayList<CompleteOffer>      = arrayListOf()

)