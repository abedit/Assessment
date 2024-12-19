package com.abedit.aldiassessment.repositories

import com.abedit.aldiassessment.models.Coin
import com.abedit.aldiassessment.services.CoinService
import javax.inject.Inject

class CoinsRepository @Inject constructor() {

    private val service = CoinService.create()
    suspend fun getCoinsList(): List<Coin> {
        val response = service.getCoins()
        return if (response.isSuccessful) {
            val coinsList = response.body()?.data?: emptyList()
            coinsList.sortedBy { it.rank?.toIntOrNull() }.take(10)
        } else {
            throw Exception("Error: fetching coins failed")
        }
    }
}