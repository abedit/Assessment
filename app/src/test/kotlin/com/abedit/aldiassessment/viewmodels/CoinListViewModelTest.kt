package com.abedit.aldiassessment.viewmodels

import com.abedit.aldiassessment.AUTOMATIC_REFRESH_TIME
import com.abedit.aldiassessment.models.Coin
import com.abedit.aldiassessment.repositories.CoinsRepository
import com.abedit.aldiassessment.states.ListUiState
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
class CoinListViewModelTest {


    @Mock
    lateinit var coinsRepository: CoinsRepository
    private lateinit var coinViewModel: CoinListViewModel
    private val testDispatcher = StandardTestDispatcher()

    @BeforeEach
    fun setUp() {
        MockitoAnnotations.openMocks(this)
        Dispatchers.setMain(testDispatcher)
        coinViewModel = CoinListViewModel(coinsRepository)
    }

    @AfterEach
    fun tearDown() {
        Dispatchers.resetMain()
        coinViewModel.stopAutomaticRefresh()
    }

    @Test
    fun `initial state is Loading`() {
        assertEquals(ListUiState.Loading, coinViewModel.uiState.value)
    }


    /*-----------------------------fetchCoins-------------------------*/


    @Test
    fun `fetchCoins - success and set ListUiState to Success`() = runTest {
        val expectedCoins = listOf(Coin(id = "bitcoin"))
        whenever(coinsRepository.getCoinsList()).thenReturn(expectedCoins)
        coinViewModel.fetchCoins()
        runCurrent()

        assertTrue(coinViewModel.uiState.value is ListUiState.Success)
        assertEquals(expectedCoins, (coinViewModel.uiState.value as ListUiState.Success).items)
    }


    @Test
    fun `fetchCoins - failure and set ListUiState to Empty`() = runTest {
        whenever(coinsRepository.getCoinsList()).thenReturn(emptyList())
        coinViewModel.fetchCoins()
        runCurrent()
        assertEquals(ListUiState.Empty, coinViewModel.uiState.value)
    }

    @Test
    fun `fetchCoins - failure but list isn't empty, set ListUiState to ErrorListNotEmpty`() = runTest {
        //first simulate a scenario where we actually got the list
        val expectedCoins = listOf(Coin(id = "bitcoin"))
        whenever(coinsRepository.getCoinsList()).thenReturn(expectedCoins)
        coinViewModel.fetchCoins()
        runCurrent()

        //Then simulate an exception
        //Since we already have data, we expect the list ui state to be ErrorListNotEmpty
        whenever(coinsRepository.getCoinsList()).thenThrow(RuntimeException())
        coinViewModel.fetchCoins()
        runCurrent()

        assertTrue(coinViewModel.uiState.value is ListUiState.ErrorListNotEmpty)
        assertEquals(
            expectedCoins,
            (coinViewModel.uiState.value as ListUiState.ErrorListNotEmpty).items
        )
    }


    /*--------------------------Automatic fetching-----------------------------*/

    @Test
    fun `resumeAutomaticRefresh - automatically fetches n times`() = runTest {
        val iterations = 2
        // Start the refresh process and then stop it
        coinViewModel.resumeAutomaticRefresh()
        advanceTimeBy(AUTOMATIC_REFRESH_TIME * iterations)
        coinViewModel.stopAutomaticRefresh()

        verify(coinsRepository, times(iterations)).getCoinsList()
    }


}