package com.abedit.aldiassessment

import com.abedit.aldiassessment.models.Coin

const val REFRESH_TIME = 1000L * 60
const val BASE_URL = "https://api.coincap.io/"
const val GET_COINS_API = "v2/assets"
const val GET_COINS_WITH_ID_API = "v2/assets/{id}"

fun getPreviewCoin() = Coin( //fake Coin just for preview purposes in compose
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

val ICON_ID_2_IMAGE_MAP = mapOf(
    "bitcoin" to R.drawable.icon_bitcoin,
    "ethereum" to R.drawable.icon_ethereum,
    "tether" to R.drawable.icon_tether,
    "binance-coin" to R.drawable.icon_bnb,
    "cardano" to R.drawable.icon_cardano,
    "ripple" to R.drawable.icon_xrp,

    //not present in the json data
    "avalanche" to R.drawable.icon_avalanche,
    "polygon" to R.drawable.icon_polygon,
)