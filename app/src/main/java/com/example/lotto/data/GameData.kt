package com.example.lotto.data

import kotlinx.serialization.Serializable

@Serializable
data class GameData(
    val countryId: Int,
    val cutoffHours: Int,
    val languageId: Int,
    val priorityGames: List<PriorityGame>
)