package com.abedit.aldiassessment.ui.coinsListComponents

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

class EmptyListViewKtTest {

    @get:Rule val composeTestRule = createComposeRule()
    private lateinit var context: Context

    @Before
    fun setUp() {
        context = InstrumentationRegistry.getInstrumentation().targetContext
    }

    @Test
    fun emptyListView_showsMessageAndTryAgainButton() {
        composeTestRule.setContent {
            EmptyListView()
        }

        //ensure message and button are shown
        composeTestRule.onNodeWithText(context.getString(R.string.empty_coins_list_message))
        composeTestRule.onNodeWithText(context.getString(R.string.try_again))
    }

    @Test
    fun emptyListView_isClickable() {
        composeTestRule.setContent {
            EmptyListView()
        }

        //check button is clickable
        composeTestRule.onNodeWithText(context.getString(R.string.try_again)).assertHasClickAction()
    }

    @Test
    fun emptyListView_tryAgainButtonHasAction() {
        var tryAgainClicked = false
        composeTestRule.setContent {
            EmptyListView(tryAgainAction = { tryAgainClicked = true })
        }

        //check button click does something
        composeTestRule.onNodeWithText(context.getString(R.string.try_again)).performClick()
        assertTrue(tryAgainClicked)

    }

}
