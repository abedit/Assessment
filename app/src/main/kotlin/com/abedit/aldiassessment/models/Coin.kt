package com.abedit.aldiassessment.models

import com.google.gson.annotations.SerializedName

data class Coin(
    @SerializedName("id")
    val id: String,

    @SerializedName("symbol")
    val symbol: String? = null,

    @SerializedName("name")
    val name: String? = null,

    @SerializedName("supply")
    val supply: String? = null,

    @SerializedName("marketCapUsd")
    val marketCapUsd: String? = null,

    @SerializedName("volumeUsd24Hr")
    val volumeUsd24Hr: String? = null,

    @SerializedName("priceUsd")
    val priceUsd: String? = null,

    @SerializedName("changePercent24Hr")
    val changePercent24Hr: String? = null
)