package com.abedit.aldiassessment.repositories

import com.abedit.aldiassessment.models.Coin
import com.abedit.aldiassessment.models.CoinsResponse
import com.abedit.aldiassessment.models.SingleCoinResponse
import com.abedit.aldiassessment.services.CoinService
import kotlinx.coroutines.runBlocking
import okhttp3.MediaType
import okhttp3.ResponseBody
import org.junit.Before
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever
import retrofit2.Response

@RunWith(MockitoJUnitRunner::class)
class CoinsRepositoryTest {


    private val coinService: CoinService = mock()

    private val coinsRepository: CoinsRepository = CoinsRepository(coinService)

    /*--------------------------getCoinsList-----------------------------*/

    @Test
    fun `getCoinsList returns coin list data`() = runBlocking {

        val expectedCoins = listOf(
            Coin(
                id = "bitcoin",
                symbol = "BTC",
                name = "Bitcoin",
                supply = "21000000",
                marketCapUsd = "90000000000",
                volumeUsd24Hr = "10000000",
                priceUsd = "45000",
                changePercent24Hr = "1.2",
            )
        )
        //return fake response for the test
        whenever(coinService.getCoins()).thenReturn(Response.success(CoinsResponse(expectedCoins)))
        val coins = coinsRepository.getCoinsList()
        assertTrue(coins.isNotEmpty())
    }

    @Test
    fun `getCoinsList returns exception if coin list API unsuccessful response`() = runBlocking {

        val errorResponse =
            Response.error<CoinsResponse>(400, ResponseBody.create(MediaType.parse(""), ""))

        //return fake error response for the test
        whenever(coinService.getCoins()).thenReturn(errorResponse)
        val exceptionMessage = assertThrows<Exception> {
            coinsRepository.getCoinsList()
        }.message

        assertTrue(!exceptionMessage.isNullOrEmpty() && exceptionMessage.contains("fetching coins failed"));
    }



    /*-------------------------getCoinById-----------------------------*/


    @Test
    fun `getCoinById returns single coin data`() = runBlocking {

        val expectedCoin =
            Coin(
                id = "bitcoin",
                symbol = "BTC",
                name = "Bitcoin",
                supply = "21000000",
                marketCapUsd = "90000000000",
                volumeUsd24Hr = "10000000",
                priceUsd = "45000",
                changePercent24Hr = "1.2",
            )

        //return fake response for the test
        whenever(coinService.getCoinById(expectedCoin.id)).thenReturn(
            Response.success(
                SingleCoinResponse(expectedCoin)
            )
        )
        val coins = coinsRepository.getCoinById(expectedCoin.id)
        assertTrue(coins != null)
    }

    @Test
    fun `getCoinById returns exception if coin API unsuccessful response`() = runBlocking {

        val errorResponse =
            Response.error<SingleCoinResponse>(400, ResponseBody.create(MediaType.parse(""), ""))

        //return fake error response for the test
        whenever(coinService.getCoinById("")).thenReturn(errorResponse)
        val exceptionMessage = assertThrows<Exception> {
            coinsRepository.getCoinById("")
        }.message

        assertTrue(!exceptionMessage.isNullOrEmpty() && exceptionMessage.contains("fetching coin info failed"));
    }
}
