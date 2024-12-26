package com.abedit.aldiassessment

import com.abedit.aldiassessment.models.Coin
import com.abedit.aldiassessment.states.ListUiState
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.Assertions.*


class ExtensionsUnitTest {


//------------------------------coinId2IconId------------------------------------


    @Test
    fun `coinId2IconId when known coin ID return correct drawable`() {
        val coinId = "bitcoin"
        val expectedDrawable = R.drawable.icon_bitcoin

        assertEquals(expectedDrawable, coinId.coinId2IconId())
    }

    @Test
    fun `coinId2IconId when unknown coin ID return default drawable`() {
        val coinId = "some_random_value"
        val expectedDrawable = R.drawable.icon_coin_default

        assertEquals(expectedDrawable, coinId.coinId2IconId())
    }


//------------------------------hasPercentageIncreased------------------------------------


    @Test
    fun `hasPercentageIncreased when positive percentage return true`() {
        val percentageValue = "5.0"
        assertTrue(percentageValue.hasPercentageIncreased)
    }

    @Test
    fun `hasPercentageIncreased when negative percentage return true`() {
        val percentageValue = "-3"
        assertFalse(percentageValue.hasPercentageIncreased)
    }

    @Test
    fun `hasPercentageIncreased when null percentage return false`() {
        val nullString = null
        assertFalse(nullString.hasPercentageIncreased)
    }

    @Test
    fun `hasPercentageIncreased when empty return false`() {
        val emptyString = ""
        assertFalse(emptyString.hasPercentageIncreased)
    }

    @Test
    fun `hasPercentageIncreased when strings without numbers return false`() {
        val stringValue = "abc"
        assertFalse(stringValue.hasPercentageIncreased)
    }


//------------------------------formattedPrice------------------------------------


    @Test
    fun `formattedPrice format price for small value`() {
        val price = "345"
        assertEquals("$345.00", price.formattedPrice())
    }

    @Test
    fun `formattedPrice format price for super large value`() {
        val price = "12345678987654321"
        assertEquals("$12345678.99B", price.formattedPrice())
    }

    @Test
    fun `formattedPrice format price for thousand`() {
        val price = "12304"
        assertEquals("$12.30K", price.formattedPrice())
    }

    @Test
    fun `formattedPrice format price for million`() {
        val price = "3210000"
        assertEquals("$3.21M", price.formattedPrice())
    }

    @Test
    fun `formattedPrice format price for billion`() {
        val price = "7800000000"
        assertEquals("$7.80B", price.formattedPrice())
    }

    @Test
    fun `formattedPrice return placeholder when null price`() {
        val price = null
        assertEquals(NULL_VALUE_PLACEHOLDER, price.formattedPrice())
    }

    @Test
    fun `formattedPrice return placeholder when empty price`() {
        val price = ""
        assertEquals(NULL_VALUE_PLACEHOLDER, price.formattedPrice())
    }

    @Test
    fun `formattedPrice return string when non-numerical price string`() {
        val price = "at least 1$ i guess"
        assertEquals(price, price.formattedPrice())
    }


//------------------------------formattedPercentage------------------------------------


    @Test
    fun `formattedPercentage format normal positive percentages`() {
        val percentage1 = "5.2314"
        val percentage2 = "25.1234567"
        val percentage3 = "125.1234567"
        assertEquals("5.23%", percentage1.formattedPercentage())
        assertEquals("25.12%", percentage2.formattedPercentage())
        assertEquals("125.12%", percentage3.formattedPercentage())
    }

    @Test
    fun `formattedPercentage format normal negative percentages`() {
        val percentage1 = "-5.2314"
        val percentage2 = "-25.1234567"
        val percentage3 = "-125.1234567"
        assertEquals("-5.23%", percentage1.formattedPercentage())
        assertEquals("-25.12%", percentage2.formattedPercentage())
        assertEquals("-125.12%", percentage3.formattedPercentage())
    }

    @Test
    fun `formattedPercentage return placeholder for null strings`() {
        val nullString = null
        assertEquals(NULL_VALUE_PLACEHOLDER, nullString.formattedPercentage())
    }

    @Test
    fun `formattedPercentage should return placeholder for empty strings`() {
        val emptyString = ""
        assertEquals(NULL_VALUE_PLACEHOLDER, emptyString.formattedPercentage())
    }

    @Test
    fun `formattedPercentage return same string for non-numerical strings`() {
        val stringValue = "price increased a lot btw"
        assertEquals(stringValue, stringValue.formattedPercentage())
    }


    //------------------------------getItems------------------------------------


    @Test
    fun `getItems return items when state is Success`() {
        val coins = listOf(Coin("Bitcoin"), Coin("Ethereum"))
        val state = ListUiState.Success(coins)

        assertEquals(coins, state.getItems())
    }

    @Test
    fun `getItems return items when state is ErrorListNotEmpty`() {
        val coins = listOf(Coin("Bitcoin"), Coin("Ethereum"))
        val state = ListUiState.ErrorListNotEmpty(coins)

        assertEquals(coins, state.getItems())
    }

    @Test
    fun `getItems return empty list when state is Loading`() {
        val state = ListUiState.Loading
        assertEquals(emptyList<Coin>(), state.getItems())
    }

    @Test
    fun `getItems return empty list when state is Empty`() {
        val state = ListUiState.Empty
        assertEquals(emptyList<Coin>(), state.getItems())
    }

}