package com.abedit.aldiassessment

import com.abedit.aldiassessment.models.Coin

const val REFRESH_TIME = 1000L * 10
const val BASE_URL = "https://api.coincap.io/"
const val GET_COINS_API = "v2/assets"
const val GET_COINS_WITH_ID_API = "v2/assets/{id}"
fun getPreviewCoin() = Coin(
    id = "bitcoin",
    rank = "1",
    symbol = "BTC",
    name = "Bitcoin",
    supply = "21000000",
    maxSupply = "21000000",
    marketCapUsd = "900000000",
    volumeUsd24Hr = "10000000",
    priceUsd = "45000",
    changePercent24Hr = "1.2",
    vwap24Hr = "44800"
)