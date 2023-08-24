package com.example.lotto.api_calls

import com.example.lotto.data.Event
import com.example.lotto.data.GameData
import com.example.lotto.data.Offers
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.http.Query


interface LotoCalls {

    @Headers("Content-type:application/json;charset=UTF-8")
    @POST("MozzartWS/external.json/lotto-offer-complete")
    suspend fun getLotoOffers(@Body gameData: GameData) : Offers

    @GET("https://api.mozzartbet.com/MozzartWS/external.json/lotto-event")
    suspend fun getLottoEvent(
        @Query("languageId") languageId: Int,
        @Query("gameId") gameId: Int?,
        @Query("eventId") eventId: Int?,
    ) : Event
}