package com.abedit.aldiassessment.ui.sharedComponents

import android.content.Context
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithText
import androidx.test.platform.app.InstrumentationRegistry
import com.abedit.aldiassessment.R
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class ToolbarKtTest {
    @get:Rule
    val composeTestRule = createComposeRule()
    private lateinit var context: Context

    @Before
    fun setUp() {
        context = InstrumentationRegistry.getInstrumentation().targetContext
    }


    @Test
    fun toolbar_displayCorrectTitle() {

        composeTestRule.setContent {
            Toolbar(
                title  = "Bitcoin"
            )
        }

        //ensure the title is displayed
        composeTestRule.onNodeWithText("Bitcoin").assertExists()

    }

    @Test
    fun toolbar_showBackButtonWhenActionExists() {
        val contentDesc = context.getString(R.string.back_button_content_description)

        composeTestRule.setContent {
            Toolbar(
                title = "something",
                backButtonClicked = {} //back button action not null
            )
        }

        //ensure the back button is displayed
        composeTestRule.onNodeWithContentDescription(contentDesc).assertExists()
    }

    @Test
    fun toolbar_showBackButtonWhenActionNotExists() {
        val contentDesc = context.getString(R.string.back_button_content_description)

        composeTestRule.setContent {
            Toolbar(
                title = "something",
                backButtonClicked = null
            )
        }

        //ensure the back button is not displayed
        composeTestRule.onNodeWithContentDescription(contentDesc).assertDoesNotExist()
    }

}