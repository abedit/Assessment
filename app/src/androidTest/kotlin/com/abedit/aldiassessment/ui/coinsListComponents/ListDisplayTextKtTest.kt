package com.abedit.aldiassessment.ui.coinsListComponents

import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.test.assertTextEquals
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.unit.sp
import org.junit.Rule
import org.junit.Test

class ListDisplayTextKtTest {

    @get:Rule val composeTestRule = createComposeRule()


    @Test
    fun listDisplayText_displayCorrectData() {
        val testText = "test text"

        composeTestRule.setContent {
            ListDisplayText(
                modifier = Modifier,
                text = testText,
                fontSize = 16.sp,
                textColor = Color.Red
            )
        }

        //ensure the text is displayed
        composeTestRule.onNodeWithText(testText).assertExists().assertTextEquals(testText)
    }


}