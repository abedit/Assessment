package com.abedit.aldiassessment

import android.graphics.Color
import android.net.Uri
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.SystemBarStyle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.core.content.ContextCompat
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.abedit.aldiassessment.ui.CoinsListView
import com.abedit.aldiassessment.viewmodels.CoinListViewModel
import dagger.hilt.android.AndroidEntryPoint
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.abedit.aldiassessment.ui.CoinDetailsView
import com.abedit.aldiassessment.viewmodels.CoinDetailsViewModel
import com.google.gson.Gson

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //For the status bar color
        enableEdgeToEdge(
            statusBarStyle = SystemBarStyle.light(
                ContextCompat.getColor(
                    this,
                    R.color.status_bar_color
                ), Color.TRANSPARENT
            )
        )

        setContent {
            CoinAppNavigation()
        }
    }
}



@Composable
fun CoinAppNavigation() {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = Screen.List.route) {

        /*************  List screen  ********/
        composable(route = Screen.List.route) {
            val coinListViewModel: CoinListViewModel = hiltViewModel()
            val gson = Gson()

            //Resume the automatic refresh for the list
            LaunchedEffect(navController) {
                coinListViewModel.resumeAutomaticRefresh()
            }

            CoinsListView(
                coinListViewModel = coinListViewModel,
                navigateToDetails = { mCoin ->
                    //Stop the automatic refresh
                    coinListViewModel.stopAutomaticRefresh()

                    //Transfer the coin object into the new screen
                    val coinJson = Uri.encode(gson.toJson(mCoin))
                    navController.navigate(Screen.Details.route + "?$ARGUMENT_COIN_JSON=$coinJson")
                }
            )

        }

        /*************  Details screen  ********/
        composable(
            route = Screen.Details.route + "?$ARGUMENT_COIN_JSON={$ARGUMENT_COIN_JSON}",
            arguments = listOf(navArgument(ARGUMENT_COIN_JSON) { type = NavType.StringType })
        ) {
            val coinDetailsViewModel: CoinDetailsViewModel = hiltViewModel()

            //Resume the automatic refresh
            LaunchedEffect(navController) {
                coinDetailsViewModel.resumeAutomaticRefresh()
            }

            CoinDetailsView(
                    viewModel = coinDetailsViewModel,
                    backButtonClicked = {
                        //stop the automatic refresh
                        coinDetailsViewModel.stopAutomaticRefresh()

                        //go back to the list view
                        navController.popBackStack()
                    }
                )
            }
    }
}
