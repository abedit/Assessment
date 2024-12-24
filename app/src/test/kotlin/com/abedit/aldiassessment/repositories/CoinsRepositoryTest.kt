package com.abedit.aldiassessment.repositories

import com.abedit.aldiassessment.models.Coin
import com.abedit.aldiassessment.models.CoinsResponse
import com.abedit.aldiassessment.services.CoinService
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever
import retrofit2.Response

@RunWith(MockitoJUnitRunner::class)
class CoinsRepositoryTest {


    @Mock
    private val coinService: CoinService = mock()

    private val coinsRepository: CoinsRepository = CoinsRepository(coinService)

    @Test
    fun `getCoinsList returns expected data`() = runBlocking {

        val expectedCoins = listOf(Coin(
            id = "bitcoin_1",
            symbol = "BTC",
            name = "Bitcoin",
            supply = "21000000",
            marketCapUsd = "90000000000",
            volumeUsd24Hr = "10000000",
            priceUsd = "45000",
            changePercent24Hr = "1.2",
        ))
        //return fake response for the test
        whenever(coinService.getCoins()).thenReturn(Response.success(CoinsResponse(expectedCoins)))
        val coins = coinsRepository.getCoinsList()
        assertTrue(coins.isNotEmpty())
    }
}
