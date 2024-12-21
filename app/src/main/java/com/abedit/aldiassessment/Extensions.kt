package com.abedit.aldiassessment

fun String.coinId2IconId(): Int {
    return ICON_ID_2_IMAGE_MAP.getOrElse(this) { R.drawable.arrow_back }
}