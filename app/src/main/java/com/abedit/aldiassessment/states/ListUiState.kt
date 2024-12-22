package com.abedit.aldiassessment.states

import com.abedit.aldiassessment.models.Coin

sealed class ListUiState {
    data object Loading : ListUiState()
    data object Empty : ListUiState()
    data class Success(val items: List<Coin>) : ListUiState()
    data class ErrorListNotEmpty(val items: List<Coin>) : ListUiState()
}