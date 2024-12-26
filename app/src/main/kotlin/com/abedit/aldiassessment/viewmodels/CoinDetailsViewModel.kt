package com.abedit.aldiassessment.viewmodels

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.abedit.aldiassessment.ARGUMENT_COIN_JSON
import com.abedit.aldiassessment.AUTOMATIC_REFRESH_TIME
import com.abedit.aldiassessment.models.Coin
import com.abedit.aldiassessment.repositories.CoinsRepository
import com.abedit.aldiassessment.states.DetailsUiState
import com.google.gson.Gson
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CoinDetailsViewModel @Inject constructor(
    savedState: SavedStateHandle,
    private val repository: CoinsRepository
) : ViewModel() {

    private val _currentCoin = MutableStateFlow<Coin>(
        Gson().fromJson(savedState.get<String>(ARGUMENT_COIN_JSON), Coin::class.java)
    )
    val currentCoin: StateFlow<Coin> = _currentCoin

    private var refreshJob: Job? = null
    private var fetchJob: Job? = null

    private val _detailUiState = MutableStateFlow<DetailsUiState>(DetailsUiState.Loading)
    val uiState: StateFlow<DetailsUiState> = _detailUiState

    /*
    * Automatically fetch the coin info every 1 minute
    * */
    private fun startAutomaticRefresh() {
        refreshJob = viewModelScope.launch {
            flow {
                while (true) {
                    emit(Unit)
                    delay(AUTOMATIC_REFRESH_TIME)
                }
            }.collect {
                fetchCoinInfo()
            }
        }
    }

    /*
    * Resume the fetching if it was cancelled
    * */
    fun resumeAutomaticRefresh() {
        if (refreshJob == null || refreshJob?.isCancelled == true) {
            startAutomaticRefresh()
        }
    }

    /*
    * Stop fetching every minute
    * */
    fun stopAutomaticRefresh() {
        refreshJob?.cancel()
    }

    /*
    * Call the API and update the DetailsUiState
    * */
    fun fetchCoinInfo() {

        //cancel previous job
        fetchJob?.cancel()

        // fetch the coin by ID
        fetchJob = viewModelScope.launch {
            _detailUiState.value = DetailsUiState.Loading

            try {
                val coinResponse = repository.getCoinById(_currentCoin.value.id)
                _detailUiState.value = DetailsUiState.NotLoading
                if (coinResponse != null) {
                    _currentCoin.value = coinResponse
                } else {
                    _detailUiState.value = DetailsUiState.Error
                }

            } catch (e: Exception) {
                _detailUiState.value = DetailsUiState.NotLoading
            }
        }

    }

    override fun onCleared() {
        super.onCleared()
        refreshJob?.cancel()
        fetchJob?.cancel()
        refreshJob = null
        fetchJob = null
    }


}
