package ir.miare.androidcodechallenge.ui.homescreen.viewmodel

import ir.miare.androidcodechallenge.data.model.FakeDataFlatted


sealed class HomeUiState {
    data object Loading : HomeUiState()
    data class LoadSuccess(val data: List<FakeDataFlatted>, val sortTypes: SortTypes) : HomeUiState()
    data class LoadFailed(val errorMessage: String, val lastSortType: SortTypes) : HomeUiState()
}