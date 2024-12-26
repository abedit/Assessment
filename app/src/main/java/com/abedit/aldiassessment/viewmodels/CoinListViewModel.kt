package com.abedit.aldiassessment.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.abedit.aldiassessment.AUTOMATIC_REFRESH_TIME
import com.abedit.aldiassessment.models.Coin
import com.abedit.aldiassessment.states.ListUiState
import com.abedit.aldiassessment.repositories.CoinsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.random.Random

@HiltViewModel
class CoinListViewModel @Inject constructor(
    private val repository: CoinsRepository
) : ViewModel() {

    private val _coinsListStateFlow = MutableStateFlow<List<Coin>>(emptyList())
    private val _listUiState = MutableStateFlow<ListUiState>(ListUiState.Loading)

    val uiState: StateFlow<ListUiState> = _listUiState

    private var refreshJob: Job? = null
    private var fetchJob: Job? = null


    /*
    * Automatically fetch the coins every 1 minute
    * */
    private fun startAutomaticRefresh() {
        refreshJob = viewModelScope.launch {
            flow {
                while (true) {
                    emit(Unit)
                    delay(AUTOMATIC_REFRESH_TIME)
                }
            }.collect {
                fetchCoins()
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
                    //show empty list view
                    _listUiState.value = ListUiState.Empty
                } else {
                    _coinsListStateFlow.value = coinsList
                    _listUiState.value = ListUiState.Success(coinsList)
                }

            } catch (e: Exception) {
                _listUiState.value = //if list is not empty, keep the data but change the state
                    if (_coinsListStateFlow.value.isNotEmpty())
                        ListUiState.ErrorListNotEmpty(_coinsListStateFlow.value)
                    else
                        ListUiState.Empty
            }
        }

    }

    override fun onCleared() {
        super.onCleared()
        refreshJob?.cancel()
        refreshJob = null
    }
}
