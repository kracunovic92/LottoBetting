package com.example.lotto.api_calls

import com.example.lotto.data.GameData
import com.example.lotto.data.Offers
import retrofit2.http.Body
import retrofit2.http.POST


interface LotoCalls {


    @POST("MozzartWS/external.json/lotto-offer-complete")
    suspend fun getLotoOffers(@Body gameData: GameData) : Offers
}