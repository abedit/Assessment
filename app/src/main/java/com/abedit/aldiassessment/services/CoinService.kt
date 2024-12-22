package com.abedit.aldiassessment.services

import com.abedit.aldiassessment.BASE_URL
import com.abedit.aldiassessment.GET_COINS_API
import com.abedit.aldiassessment.GET_COINS_WITH_ID_API
import com.abedit.aldiassessment.models.CoinsResponse
import com.abedit.aldiassessment.models.SingleCoinResponse
import okhttp3.OkHttpClient
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import java.util.concurrent.TimeUnit

interface CoinService {

    @GET(GET_COINS_API)
    suspend fun getCoins(@Query("limit") limit: Int = 10): Response<CoinsResponse>

    @GET(GET_COINS_WITH_ID_API)
    suspend fun getCoinById(@Path("id") id: String): Response<SingleCoinResponse>


    companion object {
        fun create(): CoinService {
            val okHttpClient = OkHttpClient.Builder()
                .connectTimeout(5, TimeUnit.SECONDS)
                .readTimeout(5, TimeUnit.SECONDS)
                .build()

            return Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(CoinService::class.java)
        }
    }
}