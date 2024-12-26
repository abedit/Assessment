package com.abedit.aldiassessment.ui.coinDetailsComponents

import android.content.Context
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.test.platform.app.InstrumentationRegistry
import com.abedit.aldiassessment.R
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class CoinDetailsErrorKtTest {
    @get:Rule
    val composeTestRule = createComposeRule()

    private lateinit var context: Context

    @Before
    fun setUp() {
        context = InstrumentationRegistry.getInstrumentation().targetContext
    }


    @Test
    fun coinDetailsError_tryAgainClickable() {
        var tryAgainClicked = false
        val tryAgainText = context.getString(R.string.try_again)

        composeTestRule.setContent {
            CoinDetailsError(tryAgainClicked = { tryAgainClicked = true })
        }

        // Ensure the try again button is clickable
        composeTestRule.onNodeWithText(tryAgainText).performClick()
        assertTrue(tryAgainClicked)
    }
}