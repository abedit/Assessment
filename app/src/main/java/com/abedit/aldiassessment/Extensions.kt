package com.abedit.aldiassessment

import java.util.Locale

//to get the drawable ID depending on the coin ID
fun String.coinId2IconId(): Int {
    return ICON_ID_2_IMAGE_MAP.getOrElse(this) { R.drawable.icon_coin_default }
}

//to know whether to show green or red color
val String?.hasPriceIncreased: Boolean
    get() = (this?.toFloatOrNull() ?: 0f) > 0f


fun String?.formattedPrice(): String {
    if (this.isNullOrBlank())
        return NULL_VALUE_PLACEHOLDER

    //using abbreviations (K for thousands, M for millions and B for billions)
    //price is in USD therefore the US locale can be used
    return try {
        val value = this.toDouble()
        when {
            value >= BILLION -> String.format(Locale.US,"\$%.2fB", value / BILLION)
            value >= MILLION -> String.format(Locale.US,"\$%.2fM", value / MILLION)
            value >= THOUSAND -> String.format(Locale.US,"\$%.2fK", value / THOUSAND)
            else -> String.format(Locale.US,"\$%.2f", value)
        }
    } catch (e: NumberFormatException) {
        this
    }

}

fun String?.formattedPercentage(): String {
    if (this.isNullOrBlank())
        return NULL_VALUE_PLACEHOLDER
    //two digits after the decimal and add percentage sign
    return try {
        val value = this.toDouble()
        String.format(Locale.US, "%.2f%%", value)
    } catch (e: NumberFormatException) {
        this
    }
}