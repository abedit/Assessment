package com.abedit.aldiassessment.repositories

import javax.inject.Inject

class CoinsRepository @Inject constructor() {

    fun getCoinsList(): List<String> {
        return listOf("Bitcoin", "Ethereum", "Litecoin", "Ripple", "Cardano")
    }
}