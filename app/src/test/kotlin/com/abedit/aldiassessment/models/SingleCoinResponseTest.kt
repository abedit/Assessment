package com.abedit.aldiassessment.models

import com.google.gson.Gson
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.Test

class SingleCoinResponseTest {

    @Test
    fun `test coin API response`() {
        val gson = Gson()
        val jsonResponse = """
            {
                "data": {
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
            }
        """

        val response = gson.fromJson(jsonResponse, SingleCoinResponse::class.java)
        assertNotNull(response)
        assertEquals("bitcoin", response.data?.id)
        assertEquals("BTC", response.data?.symbol)
        assertEquals("Bitcoin", response.data?.name)
        assertEquals("17193925.0000000000000000", response.data?.supply)
        assertEquals("119150835874.4699281625807300", response.data?.marketCapUsd)
        assertEquals("2927959461.1750323310959460", response.data?.volumeUsd24Hr)
        assertEquals("6929.8217756835584756", response.data?.priceUsd)
        assertEquals("-0.8101417214350335", response.data?.changePercent24Hr)
    }

    @Test
    fun `test coin empty API response`() {
        val gson = Gson()
        val jsonResponse = """
            {
                
            }
        """

        val response = gson.fromJson(jsonResponse, SingleCoinResponse::class.java)
        assertEquals(response, SingleCoinResponse(null))
    }
}