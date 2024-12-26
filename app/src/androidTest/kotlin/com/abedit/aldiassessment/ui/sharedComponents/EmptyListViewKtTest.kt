package com.abedit.aldiassessment.ui.sharedComponents

import android.content.Context
import androidx.compose.ui.test.assertHasClickAction
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.test.platform.app.InstrumentationRegistry
import com.abedit.aldiassessment.R
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class CommonErrorViewKtTest {

    @get:Rule val composeTestRule = createComposeRule()
    private lateinit var context: Context

    @Before
    fun setUp() {
        context = InstrumentationRegistry.getInstrumentation().targetContext
    }

    @Test
    fun emptyListView_showsMessageAndTryAgainButton() {
        val errorMessage = context.getString(R.string.empty_coins_list_message)
        composeTestRule.setContent {
            CommonErrorView(errorMessage) {}
        }

        //ensure message and button are shown
        composeTestRule.onNodeWithText(errorMessage)
        composeTestRule.onNodeWithText(context.getString(R.string.try_again))
    }

    @Test
    fun emptyListView_isClickable() {
        val errorMessage = context.getString(R.string.empty_coins_list_message)
        composeTestRule.setContent {
            CommonErrorView(errorMessage) {}
        }

        //check button is clickable
        composeTestRule.onNodeWithText(context.getString(R.string.try_again)).assertHasClickAction()
    }

    @Test
    fun emptyListView_tryAgainButtonHasAction() {
        var tryAgainClicked = false
        val errorMessage = context.getString(R.string.empty_coins_list_message)
        composeTestRule.setContent {
            CommonErrorView(errorMessage) {
                tryAgainClicked = true
            }
        }

        //check button click does something
        composeTestRule.onNodeWithText(context.getString(R.string.try_again)).performClick()
        assertTrue(tryAgainClicked)

    }

}
