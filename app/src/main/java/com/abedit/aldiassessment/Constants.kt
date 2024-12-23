package com.abedit.aldiassessment

import com.abedit.aldiassessment.models.Coin

const val AUTOMATIC_REFRESH_TIME = 1000L * 60
const val BASE_URL = "https://api.coincap.io/"
const val GET_COINS_API = "v2/assets"
const val GET_COINS_WITH_ID_API = "v2/assets/{id}"
const val NULL_VALUE_PLACEHOLDER = "N/A"
const val COINS_FROM_API_LIMIT = 10

fun getPreviewCoin() = Coin( //fake Coin just for preview purposes in compose
    id = "bitcoin",
    symbol = "BTC",
    name = "Bitcoin",
    supply = "21000000",
    marketCapUsd = "90000000000",
    volumeUsd24Hr = "10000000",
    priceUsd = "45000",
    changePercent24Hr = "1.2",
)

val ICON_ID_2_IMAGE_MAP = mapOf(
    "bitcoin" to R.drawable.icon_bitcoin,
    "ethereum" to R.drawable.icon_ethereum,
    "tether" to R.drawable.icon_tether,
    "binance-coin" to R.drawable.icon_bnb,
    "cardano" to R.drawable.icon_cardano,
    "xrp" to R.drawable.icon_xrp,
    "avalanche" to R.drawable.icon_avalanche,
    "polygon" to R.drawable.icon_polygon,
)

const val THOUSAND = 1000.0
const val MILLION = 1000000.0
const val BILLION = 1000000000.0

const val ARGUMENT_COIN_JSON = "coinJson"
