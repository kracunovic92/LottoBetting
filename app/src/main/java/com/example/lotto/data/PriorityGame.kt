package com.example.lotto.data

import kotlinx.serialization.Serializable


@Serializable
data class PriorityGame(
    val gameId: Int,
    val numberOfRounds: Int,
    val priority: Int
)