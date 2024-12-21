package com.abedit.aldiassessment

import android.graphics.Color
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.SystemBarStyle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.core.content.ContextCompat
import com.abedit.aldiassessment.ui.CoinsOverview
import com.abedit.aldiassessment.viewmodels.CoinListViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private val coinListViewModel: CoinListViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge(
            statusBarStyle = SystemBarStyle.light(ContextCompat.getColor(this, R.color.status_bar_color), Color.TRANSPARENT)
        )
        setContent {
            CoinsOverview(
                coinListViewModel = coinListViewModel
            )
        }
    }
}

