package com.example.lotto.api_calls

import com.example.lotto.data.GameData
import com.example.lotto.data.Offers
import com.example.lotto.data.PriorityGame
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitClient {

    private var my_api : LotoCalls

    init {
        val retrofit = Retrofit
            .Builder()
            .baseUrl("https://api.mozzartbet.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        my_api =retrofit.create(LotoCalls::class.java)
    }

    suspend fun  getData(): Offers{

        val gameData = GameData(
            countryId = 1,
            cutoffHours = 24,
            languageId = 1,
            priorityGames = listOf(
                PriorityGame(26, 20, 1),
                PriorityGame(58, 20, 2),
                PriorityGame(90, 20, 3),
                PriorityGame(91, 20, 4),
                PriorityGame(86, 20, 5),
                PriorityGame(89, 20, 6),
                PriorityGame(88, 20, 7)
            )
        )

        return my_api.getLotoOffers(gameData)
    }


}