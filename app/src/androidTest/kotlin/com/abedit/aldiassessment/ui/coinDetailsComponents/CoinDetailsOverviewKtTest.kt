package com.abedit.aldiassessment.ui.coinDetailsComponents

import android.content.Context
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.test.platform.app.InstrumentationRegistry
import com.abedit.aldiassessment.R
import com.abedit.aldiassessment.formattedPercentage
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class CoinDetailsOverviewKtTest {
    @get:Rule
    val composeTestRule = createComposeRule()

    private lateinit var context: Context

    @Before
    fun setUp() {
        context = InstrumentationRegistry.getInstrumentation().targetContext
    }

    @Test
    fun coinDetailsOverview_displayCorrectData() {
        val testPrice = "$45,000"
        val testPercentChange = "1.5"
        val testMarketCap = "$900B"
        val testVolume24h = "$10B"
        val testSupply = "19M BTC"

        composeTestRule.setContent {
            CoinDetailsOverview(
                price = testPrice,
                percentChange = testPercentChange,
                marketCap = testMarketCap,
                volume24h = testVolume24h,
                supply = testSupply
            )
        }
        val priceCaption = context.getString(R.string.coin_details_caption_price)
        val percentChangeCaption = context.getString(R.string.coin_details_caption_change)
        val marketCapCaption = context.getString(R.string.coin_details_caption_market_cap)
        val volume24hCaption = context.getString(R.string.coin_details_caption_volume_24h)
        val supplyCaption = context.getString(R.string.coin_details_caption_supply)

        //ensure the proper captions and values are displayed
        composeTestRule.onNodeWithText(priceCaption).assertExists()
        composeTestRule.onNodeWithText(testPrice).assertExists()

        composeTestRule.onNodeWithText(percentChangeCaption).assertExists()
        composeTestRule.onNodeWithText(testPercentChange.formattedPercentage()).assertExists()

        composeTestRule.onNodeWithText(marketCapCaption).assertExists()
        composeTestRule.onNodeWithText(testMarketCap).assertExists()

        composeTestRule.onNodeWithText(volume24hCaption).assertExists()
        composeTestRule.onNodeWithText(testVolume24h).assertExists()

        composeTestRule.onNodeWithText(supplyCaption).assertExists()
        composeTestRule.onNodeWithText(testSupply).assertExists()


    }

}