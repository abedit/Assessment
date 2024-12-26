package com.abedit.aldiassessment.models

import org.junit.jupiter.api.Assertions.*

import org.junit.jupiter.api.Test

class CoinTest {

    private val coin = Coin(
        id = "bitcoin",
        symbol = "BTC",
        name = "Bitcoin",
        supply = "21000000",
        marketCapUsd = "90000000000",
        volumeUsd24Hr = "10000000",
        priceUsd = "45000",
        changePercent24Hr = "1.2",
    )
    @Test
    fun getId() {
        assertEquals(coin.id, "bitcoin")
    }

    @Test
    fun getSymbol() {
        assertEquals(coin.symbol, "BTC")
    }

    @Test
    fun getName() {
        assertEquals(coin.name, "Bitcoin")
    }

    @Test
    fun getSupply() {
        assertEquals(coin.supply, "21000000")
    }

    @Test
    fun getMarketCapUsd() {
        assertEquals(coin.marketCapUsd, "90000000000")
    }

    @Test
    fun getVolumeUsd24Hr() {
        assertEquals(coin.volumeUsd24Hr, "10000000")
    }

    @Test
    fun getPriceUsd() {
        assertEquals(coin.priceUsd, "45000")
    }

    @Test
    fun getChangePercent24Hr() {
        assertEquals(coin.changePercent24Hr, "1.2")
    }

    @Test
    fun copy() {
        val coinCopy = coin.copy()
        assertEquals(coin, coinCopy)
    }

    @Test
    fun testToString() {
        val stringCoin = coin.toString()
        assertEquals(stringCoin, "Coin(id=bitcoin, symbol=BTC, name=Bitcoin, supply=21000000, marketCapUsd=90000000000, volumeUsd24Hr=10000000, priceUsd=45000, changePercent24Hr=1.2)")
    }

    @Test
    fun testEquals() {
        val coin1 = Coin(
            id = "bitcoin",
            symbol = "BTC",
            name = "Bitcoin",
            supply = "21000000",
            marketCapUsd = "90000000000",
            volumeUsd24Hr = "10000000",
            priceUsd = "45000",
            changePercent24Hr = "1.2",
        )
        assertEquals(coin, coin1)
    }

    @Test
    fun testNotEquals() {
        val coin1 = Coin(
            id = "bitcoin_1",
            symbol = "BTC",
            name = "Bitcoin",
            supply = "21000000",
            marketCapUsd = "90000000000",
            volumeUsd24Hr = "10000000",
            priceUsd = "45000",
            changePercent24Hr = "1.2",
        )
        assertNotEquals(coin, coin1)
    }
}