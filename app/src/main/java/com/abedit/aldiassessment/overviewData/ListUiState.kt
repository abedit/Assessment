package com.abedit.aldiassessment.overviewData

sealed class ListUiState {
    data object Loading : ListUiState()
    data object Empty : ListUiState()
    data class Success(val items: List<String>) : ListUiState()
}