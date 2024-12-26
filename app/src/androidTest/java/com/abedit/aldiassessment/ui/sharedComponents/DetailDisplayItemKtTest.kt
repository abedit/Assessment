package com.abedit.aldiassessment.ui.sharedComponents

import android.content.Context
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.test.platform.app.InstrumentationRegistry
import com.abedit.aldiassessment.R
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class DetailDisplayItemKtTest {
    @get:Rule
    val composeTestRule = createComposeRule()

    private lateinit var context: Context

    @Before
    fun setUp() {
        context = InstrumentationRegistry.getInstrumentation().targetContext
    }

    @Test
    fun detailDisplayItem_displayCorrectData() {
        val testPrice = "$45,000"

        composeTestRule.setContent {
            DetailDisplayItem(
                caption  = context.getString(R.string.coin_details_caption_price),
                value = testPrice,
            )
        }

        //ensure the  correct captions and values are displayed
        composeTestRule.onNodeWithText(context.getString(R.string.coin_details_caption_price)).assertExists()
        composeTestRule.onNodeWithText(testPrice).assertExists()


    }
}