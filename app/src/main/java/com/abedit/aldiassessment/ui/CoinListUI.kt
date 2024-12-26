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
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.abedit.aldiassessment.R
import com.abedit.aldiassessment.getItems
import com.abedit.aldiassessment.getPreviewCoin
import com.abedit.aldiassessment.models.Coin
import com.abedit.aldiassessment.states.ListUiState
import com.abedit.aldiassessment.ui.coinsListComponents.CoinsList
import com.abedit.aldiassessment.ui.coinsListComponents.EmptyListView
import com.abedit.aldiassessment.ui.sharedComponents.Toolbar
import com.abedit.aldiassessment.ui.theme.AldiAssessmentTheme
import com.abedit.aldiassessment.ui.theme.Blue
import com.abedit.aldiassessment.ui.theme.CoinsListBackground
import com.abedit.aldiassessment.ui.theme.ToolbarBackground
import com.abedit.aldiassessment.viewmodels.CoinListViewModel


@Composable
fun CoinsListView(coinListViewModel: CoinListViewModel, navigateToDetails: (Coin) -> Unit) {
    val listUiState by coinListViewModel.uiState.collectAsState()
    val refreshing by coinListViewModel.refreshing.collectAsState()
    CoinsListView(
        isRefreshing = refreshing,
        listUiState = listUiState,
        tryAgainAction = { coinListViewModel.fetchCoins() },
        navigateToDetails = navigateToDetails,
        onRefresh = { coinListViewModel.manualRefreshTriggered() }
    )
}

@Composable
private fun CoinsListView(
    isRefreshing: Boolean,
    listUiState: ListUiState,
    tryAgainAction: () -> Unit = {},
    navigateToDetails: (Coin) -> Unit = {},
    onRefresh: () -> Unit = {}
) {

    val snackbarHostState = remember { SnackbarHostState() }

    //would like to save the scroll position after automatic refresh
    val lazyColumnState = rememberLazyListState()

    AldiAssessmentTheme {
        Scaffold(
            snackbarHost = { SnackbarHost(snackbarHostState) }
        ) { innerPadding ->
            Column(
                modifier = Modifier
                    .padding(innerPadding)
                    .fillMaxSize()
            ) {

                /*
                UI structure:
                * Toolbar
                * CoinsList
                */
                Toolbar(
                    modifier = Modifier.background(ToolbarBackground),
                    title = stringResource(R.string.coins_overview_toolbar_title)
                )

                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(CoinsListBackground)
                ) {
                    CoinsView(
                        isRefreshing = isRefreshing,
                        listUiState = listUiState,
                        tryAgainAction = tryAgainAction,
                        lazyColumnState = lazyColumnState,
                        navigateToDetails = navigateToDetails,
                        onRefresh = onRefresh
                    )
                }

            }
        }

    }

    //Show snackbar based on the UIState
    val context = LocalContext.current
    LaunchedEffect(listUiState) {
        if (listUiState is ListUiState.ErrorListNotEmpty) {
            snackbarHostState.showSnackbar(
                message = context.getString(R.string.api_fetch_error_message_with_data),
                duration = SnackbarDuration.Short
            )
        }
    }
}

//The main view for the list and the progress indicator
@Composable
private fun CoinsView(
    isRefreshing: Boolean,
    listUiState: ListUiState,
    tryAgainAction: () -> Unit,
    lazyColumnState: LazyListState,
    navigateToDetails: (Coin) -> Unit,
    onRefresh: () -> Unit
) {

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
            is ListUiState.Success, is ListUiState.ErrorListNotEmpty -> {
                CoinsList(
                    isRefreshing = isRefreshing,
                    coinsList = listUiState.getItems(),
                    lazyColumnState = lazyColumnState,
                    navigateToDetails = navigateToDetails,
                    onRefresh = onRefresh
                )
            }
            is ListUiState.Empty -> EmptyListView(tryAgainAction = tryAgainAction)
            else -> {} // Loading - already handled
        }
    }

}


@Preview(showBackground = true, showSystemUi = true)
@Composable
private fun CoinsOverviewSuccessPreview() {
    CoinsListView(
        false,
        listUiState = ListUiState.Success(
            listOf(
                getPreviewCoin(),
                getPreviewCoin(),
                getPreviewCoin(),
            )
        )
    )
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
private fun CoinsOverviewLoadingPreview() {
    CoinsListView(false, listUiState = ListUiState.Loading)
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
private fun CoinsOverviewEmptyPreview() {
    CoinsListView(false, listUiState = ListUiState.Empty)
}