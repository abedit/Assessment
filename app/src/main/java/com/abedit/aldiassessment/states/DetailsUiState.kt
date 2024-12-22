package com.abedit.aldiassessment.states



sealed class DetailsUiState {
    data object Loading : DetailsUiState()
    data object NotLoading : DetailsUiState()
}