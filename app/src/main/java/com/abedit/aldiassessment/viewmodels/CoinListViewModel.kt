package com.abedit.aldiassessment.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.abedit.aldiassessment.overviewData.ListUiState
import com.abedit.aldiassessment.repositories.CoinsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CoinListViewModel @Inject constructor(
    private val repository: CoinsRepository
) : ViewModel() {

    private val _coinsListStateFlow = MutableStateFlow<List<String>>(emptyList())
    val coinsList: StateFlow<List<String>> = _coinsListStateFlow

    internal val _listUiState = MutableStateFlow<ListUiState>(ListUiState.Loading)
    val uiState: StateFlow<ListUiState> = _listUiState

    fun fetchCoins() {
        // fetch the coins
        viewModelScope.launch {
//            delay(3000)
            val listResponse = repository.getCoinsList()

            _coinsListStateFlow.value = listResponse
            _listUiState.value = if (listResponse.isEmpty()) {
                ListUiState.Empty
            } else {
                ListUiState.Success(listResponse)
            }
        }
    }
}
