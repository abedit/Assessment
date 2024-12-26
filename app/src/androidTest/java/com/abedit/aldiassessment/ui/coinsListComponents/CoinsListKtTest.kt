package com.abedit.aldiassessment.ui.coinsListComponents

import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import com.abedit.aldiassessment.models.Coin
import org.junit.Assert.assertEquals
import org.junit.Rule
import org.junit.Test

class CoinsListKtTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun coinsList_itemClickNavigatesToDetails() {
        val testCoins = listOf(
            Coin(id = "bitcoin", name = "Bitcoin", symbol = "BTC", priceUsd = "45000", changePercent24Hr = "1.5")
        )
        var clickedCoin: Coin? = null

        composeTestRule.setContent {
            CoinsList(
                coinsList = testCoins,
                lazyColumnState = LazyListState(),
                navigateToDetails = { clickedCoin = it }
            )
        }

        //Ensure the data is on the screen
        composeTestRule.onNodeWithText(testCoins[0].symbol?:"BTC").performClick()
        assertEquals(testCoins[0], clickedCoin)
    }

}