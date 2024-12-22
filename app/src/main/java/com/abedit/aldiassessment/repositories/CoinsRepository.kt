package com.abedit.aldiassessment.repositories

import com.abedit.aldiassessment.models.Coin
import com.abedit.aldiassessment.services.CoinService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class CoinsRepository @Inject constructor() {

    private val service = CoinService.create()

    suspend fun getCoinsList(): List<Coin> = withContext(Dispatchers.IO) {
        val response = service.getCoins()
        if (response.isSuccessful) {
            response.body()?.data ?: emptyList()
        } else {
            throw Exception("Error: fetching coins failed")
        }
    }

}