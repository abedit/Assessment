package com.abedit.aldiassessment.models

import com.abedit.aldiassessment.models.CoinsResponse
import com.abedit.aldiassessment.models.SingleCoinResponse
import com.google.gson.Gson
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test

class CoinsResponseTest {

    @Test
    fun `test coin API response`() {
        val gson = Gson()
        val jsonResponse = """
            {
                "data": [
                    {
                        "id": "bitcoin",
                        "rank": "1",
                        "symbol": "BTC",
                        "name": "Bitcoin",
                        "supply": "17193925.0000000000000000",
                        "maxSupply": "21000000.0000000000000000",
                        "marketCapUsd": "119150835874.4699281625807300",
                        "volumeUsd24Hr": "2927959461.1750323310959460",
                        "priceUsd": "6929.8217756835584756",
                        "changePercent24Hr": "-0.8101417214350335",
                        "vwap24Hr": "7175.0663247679233209"
                    }
                ]
            }
        """

        val response = gson.fromJson(jsonResponse, CoinsResponse::class.java).data?.get(0)
        assertEquals("bitcoin", response?.id)
        assertEquals("BTC", response?.symbol)
        assertEquals("Bitcoin", response?.name)
        assertEquals("17193925.0000000000000000", response?.supply)
        assertEquals("119150835874.4699281625807300", response?.marketCapUsd)
        assertEquals("2927959461.1750323310959460", response?.volumeUsd24Hr)
        assertEquals("6929.8217756835584756", response?.priceUsd)
        assertEquals("-0.8101417214350335", response?.changePercent24Hr)
    }

    @Test
    fun `test empty API response`() {
        val gson = Gson()
        val jsonResponse = """
            {
                "data": [
                    
                ]
            }
        """

        val response = gson.fromJson(jsonResponse, CoinsResponse::class.java).data
        assertTrue(response != null && response.isEmpty())
    }
}