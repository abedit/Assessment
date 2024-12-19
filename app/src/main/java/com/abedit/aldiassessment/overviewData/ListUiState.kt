package com.abedit.aldiassessment.overviewData

import com.abedit.aldiassessment.models.Coin

sealed class ListUiState {
    data object Loading : ListUiState()
    data object Empty : ListUiState()
    data class Success(val items: List<Coin>) : ListUiState()
    data object Error : ListUiState()
    data class ErrorListNotEmpty(val items: List<Coin>) : ListUiState()
}