package com.abedit.aldiassessment.services

import com.abedit.aldiassessment.COINS_FROM_API_LIMIT
import com.abedit.aldiassessment.GET_COINS_API
import com.abedit.aldiassessment.GET_COINS_WITH_ID_API
import com.abedit.aldiassessment.models.CoinsResponse
import com.abedit.aldiassessment.models.SingleCoinResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query


interface CoinService {

    @GET(GET_COINS_API)
    suspend fun getCoins(@Query("limit") limit: Int = COINS_FROM_API_LIMIT): Response<CoinsResponse>

    @GET(GET_COINS_WITH_ID_API)
    suspend fun getCoinById(@Path("id") id: String): Response<SingleCoinResponse>

}