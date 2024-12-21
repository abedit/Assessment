package com.abedit.aldiassessment.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.abedit.aldiassessment.REFRESH_TIME
import com.abedit.aldiassessment.Utilities
import com.abedit.aldiassessment.models.Coin
import com.abedit.aldiassessment.overviewData.ListUiState
import com.abedit.aldiassessment.repositories.CoinsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.Timer
import javax.inject.Inject

@HiltViewModel
class CoinListViewModel @Inject constructor(
    private val repository: CoinsRepository
) : ViewModel() {

    private val _coinsListStateFlow = MutableStateFlow<List<Coin>>(emptyList())
    val coinsList: StateFlow<List<Coin>> = _coinsListStateFlow

    internal val _listUiState = MutableStateFlow<ListUiState>(ListUiState.Loading)
    val uiState: StateFlow<ListUiState> = _listUiState
    private var refreshJob: Job? = null
    private var fetchJob: Job? = null

    init {
        startAutomaticRefresh()
    }

    /*
    * Automatically fetch the coins every 1 minute
    * */
    private fun startAutomaticRefresh() {
        refreshJob = viewModelScope.launch {
            flow {
                while (true) {
                    emit(Unit)
                    delay(REFRESH_TIME)
                }
            }.collect {
                fetchCoins()
            }
        }
    }

    /*
    * Resume the fetching if it was cancelled
    * */
    fun resumePeriodicRefresh() {
        if (refreshJob?.isCancelled == true) {
            startAutomaticRefresh()
        }
    }

    /*
    * Stop fetching every minute
    * */
    private fun stopAutomaticRefresh() {
        refreshJob?.cancel()
    }

    /*
    * Call the API and update the listUiState and the list
    * */
    fun fetchCoins() {
        fetchJob?.cancel()
        // fetch the coins
        fetchJob = viewModelScope.launch {
            _listUiState.value = ListUiState.Loading

            try {
                val coinsList = repository.getCoinsList()

                if (coinsList.isEmpty()) {
                    _listUiState.value = ListUiState.Empty
                } else {
                    _coinsListStateFlow.value = coinsList
                    _listUiState.value = ListUiState.Success(coinsList)
                }

            } catch (e: Exception) {
                _listUiState.value = ListUiState.Error

                if (_coinsListStateFlow.value.isNotEmpty()) {
                    _listUiState.value = ListUiState.ErrorListNotEmpty(_coinsListStateFlow.value)
                }
            }
        }

    }

    override fun onCleared() {
        super.onCleared()
        refreshJob?.cancel()
        refreshJob = null
    }
}
