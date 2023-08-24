package com.example.lotto.data


import com.google.gson.annotations.SerializedName


data class OddValues (

    @SerializedName("ballNumber" ) var ballNumber : Int?    = null,
    @SerializedName("value"      ) var value      : Double? = null

)