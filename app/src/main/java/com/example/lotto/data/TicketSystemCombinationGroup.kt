package com.example.lotto.data

data class TicketSystemCombinationGroup(

     var isSelectedForSystem: Boolean = false,
     var fixes: Int = 0,
     var howMany: Int = 0,
     var fromHowMany: Int = 0,
     var payin: Double = 0.0,
     var minPotentialPayout: Double = 0.0,
     var maxPotentialPayout: Double = 0.0
)
