package com.abedit.aldiassessment

import com.abedit.aldiassessment.models.Coin
import com.abedit.aldiassessment.states.ListUiState
import java.util.Locale

//to get the drawable ID depending on the coin ID
fun String.coinId2IconId(): Int {
    return ICON_ID_2_IMAGE_MAP.getOrElse(this) { R.drawable.icon_coin_default }
}

//to know whether to show green or red color
val String?.hasPercentageIncreased: Boolean
    get() = (this?.toFloatOrNull() ?: 0f) > 0f


//using abbreviations (K for thousands, M for millions and B for billions)
//price is in USD therefore the US locale can be used
//Example: 28610 -> 28.61K
fun String?.formattedPrice(): String {
    if (this.isNullOrBlank())
        return NULL_VALUE_PLACEHOLDER

    return try {
        val value = this.toDouble()
        when {
            value >= BILLION -> String.format(Locale.US,"$%.2fB", value / BILLION)
            value >= MILLION -> String.format(Locale.US,"$%.2fM", value / MILLION)
            value >= THOUSAND -> String.format(Locale.US,"$%.2fK", value / THOUSAND)
            else -> String.format(Locale.US,"$%.2f", value)
        }
    } catch (e: NumberFormatException) {
        this
    }

}

//two digits after the decimal and add percentage sign
fun String?.formattedPercentage(): String {
    if (this.isNullOrBlank())
        return NULL_VALUE_PLACEHOLDER

    return try {
        val value = this.toDouble()
        String.format(Locale.US, "%.2f%%", value)
    } catch (e: NumberFormatException) {
        this
    }
}


//Get the items in a ListUiState without the need to cast the Ui state in compose
fun ListUiState.getItems(): List<Coin> {
    return when (this) {
        is ListUiState.Success -> items
        is ListUiState.ErrorListNotEmpty -> items
        else -> emptyList()
    }
}
