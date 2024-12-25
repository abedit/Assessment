package com.abedit.aldiassessment.viewmodels

import androidx.lifecycle.SavedStateHandle
import com.abedit.aldiassessment.ARGUMENT_COIN_JSON
import com.abedit.aldiassessment.AUTOMATIC_REFRESH_TIME
import com.abedit.aldiassessment.models.Coin
import com.abedit.aldiassessment.repositories.CoinsRepository
import com.abedit.aldiassessment.states.DetailsUiState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.advanceTimeBy
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runCurrent
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Assertions.*

import org.junit.jupiter.api.Test
import org.mockito.Mock
import org.mockito.MockitoAnnotations
import org.mockito.kotlin.times
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever

@OptIn(ExperimentalCoroutinesApi::class)
class CoinDetailsViewModelTest {


    @Mock
    lateinit var coinsRepository: CoinsRepository
    private lateinit var coinViewModel: CoinDetailsViewModel
    private val testDispatcher = StandardTestDispatcher()
    private var savedStateHandle = SavedStateHandle(mapOf(ARGUMENT_COIN_JSON to
    """
                {
                    "changePercent24Hr":"1.71",
                    "id":"ethereum",
                    "marketCapUsd":"421.14",
                    "name":"Ethereum",
                    "priceUsd":"3499.65",
                    "supply":"120458692.02",
                    "symbol":"ETH",
                    "volumeUsd24Hr":"801.93"
                }
            """.trimIndent()))

    @BeforeEach
    fun setUp() {
        MockitoAnnotations.openMocks(this)
        Dispatchers.setMain(testDispatcher)
        savedStateHandle = SavedStateHandle(mapOf(ARGUMENT_COIN_JSON to
                """
                {
                    "changePercent24Hr":"1.71",
                    "id":"ethereum",
                    "marketCapUsd":"421.14",
                    "name":"Ethereum",
                    "priceUsd":"3499.65",
                    "supply":"120458692.02",
                    "symbol":"ETH",
                    "volumeUsd24Hr":"801.93"
                }
            """.trimIndent()))
        coinViewModel = CoinDetailsViewModel(savedStateHandle, coinsRepository)
    }

    @AfterEach
    fun tearDown() {
        Dispatchers.resetMain()
        coinViewModel.stopAutomaticRefresh()
        savedStateHandle = SavedStateHandle(mapOf(ARGUMENT_COIN_JSON to ""))
    }

    @Test
    fun `initial state is Loading`() {
        assertEquals(DetailsUiState.Loading, coinViewModel.uiState.value)
    }


    /*-----------------------------fetchCoinInfo-------------------------*/


    @Test
    fun `fetchCoinInfo - success, return Coin and set DetailsUiState to NotLoading`() = runTest {

        val expectedCoin = Coin(
            changePercent24Hr = "1.71",
            id = "ethereum",
            marketCapUsd = "421.14",
            name = "Ethereum",
            priceUsd = "3499.65",
            supply = "120458692.02",
            symbol = "ETH",
            volumeUsd24Hr = "801.93"
        )
        whenever(coinsRepository.getCoinById(expectedCoin.id)).thenReturn(expectedCoin)
        coinViewModel.fetchCoinInfo()
        runCurrent()

        assertTrue(coinViewModel.uiState.value is DetailsUiState.NotLoading)
        assertEquals(expectedCoin, coinViewModel.currentCoin.value)
    }


    @Test
    fun `fetchCoinInfo - failure, set DetailsUiState to NotLoading`() = runTest {
        whenever(coinsRepository.getCoinById("")).thenReturn(null)
        coinViewModel.fetchCoinInfo()
        runCurrent()

        assertTrue(coinViewModel.uiState.value is DetailsUiState.Error)
    }



    /*--------------------------Automatic fetching-----------------------------*/

    @Test
    fun `resumeAutomaticRefresh - automatically fetches n times`() = runTest {

        //have a viewmodel instance with the custom savedStateHandle

        val iterations = 2
        // Start the refresh process and then stop it
        coinViewModel.resumeAutomaticRefresh()
        advanceTimeBy(AUTOMATIC_REFRESH_TIME * iterations)
        coinViewModel.stopAutomaticRefresh()

        verify(coinsRepository, times(iterations)).getCoinById("ethereum")
    }


}