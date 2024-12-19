package com.abedit.aldiassessment.ui

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.abedit.aldiassessment.R
import com.abedit.aldiassessment.overviewData.ListUiState
import com.abedit.aldiassessment.ui.coinsOverview.EmptyListView
import com.abedit.aldiassessment.ui.sharedComponents.Toolbar
import com.abedit.aldiassessment.ui.theme.AldiAssessmentTheme
import com.abedit.aldiassessment.ui.theme.Blue
import com.abedit.aldiassessment.ui.theme.CoinsListBackground
import com.abedit.aldiassessment.ui.theme.ToolbarBackground
import com.abedit.aldiassessment.viewmodels.CoinListViewModel

@Composable
fun CoinsOverview(coinListViewModel: CoinListViewModel) {
    val listUiState by coinListViewModel.uiState.collectAsState()
    CoinsOverview(listUiState = listUiState)
}

@Composable
fun CoinsOverview(listUiState: ListUiState) {
//    val coinsList by coinListViewModel.coinsList.collectAsState()

    AldiAssessmentTheme {
        Scaffold { innerPadding ->
            Column(
                modifier = Modifier
                    .padding(innerPadding)
                    .fillMaxSize()
            ) {

                /*
                Structure:
                * Toolbar
                * CoinsList
                */
                Toolbar(
                    modifier = Modifier.background(ToolbarBackground),
                    title = stringResource(R.string.coins_overview_toolbar_title),
                    showBackButton = false
                )

                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(CoinsListBackground)
                ) {
                    CoinsView(listUiState = listUiState)
                }

            }
        }

    }
}

@Composable
private fun CoinsView(listUiState: ListUiState) {

    // show progress indicator if list is empty
    AnimatedVisibility(
        visible = listUiState is ListUiState.Loading,
        enter = fadeIn(),
        exit = fadeOut(),
    ) {
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier.fillMaxSize()
        ) {
            CircularProgressIndicator(
                modifier = Modifier.size(30.dp),
                strokeWidth = 3.dp,
                color = Blue
            )
        }
    }

    //other scenarios - if list has data or is empty
    AnimatedVisibility(
        visible = listUiState !is ListUiState.Loading,
        enter = fadeIn(),
        exit = fadeOut(),
    ) {
        when (listUiState) {
            is ListUiState.Success -> CoinsList(coinsList = listUiState.items)
            is ListUiState.Empty -> EmptyListView()
            else -> {} //Loading - already handled
        }
    }

}



@Composable
private fun CoinsList(coinsList: List<String>) {
    //show the list of coins
    LazyColumn(
        modifier = Modifier.fillMaxSize()
    ) {
        items(items = coinsList) { mItem ->
            Text(
                text = mItem,
                modifier = Modifier.padding(15.dp)
            )
        }
    }
}




@Preview(showBackground = true, showSystemUi = true)
@Composable
fun CoinsOverviewSuccessPreview() {
    CoinsOverview(listUiState = ListUiState.Success(listOf("Bitcoin", "Ethereum", "XRP")))
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun CoinsOverviewLoadingPreview() {
    CoinsOverview(listUiState = ListUiState.Loading)
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun CoinsOverviewEmptyPreview() {
    CoinsOverview(listUiState = ListUiState.Empty)
}