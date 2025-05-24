package ir.miare.androidcodechallenge.ui.homescreen.viewmodel

import ir.miare.androidcodechallenge.data.model.FakeDataFlatted
import ir.miare.androidcodechallenge.data.model.base.Player


sealed class HomeUiState {
    data object Loading : HomeUiState()
    data class LoadSuccessFlattedFakeData(val data: List<FakeDataFlatted>) : HomeUiState()
    data class LoadSuccessPlayer(val data: List<Player>) : HomeUiState()
    data class LoadFailed(val errorMessage: String, val lastSortType: SortTypes) : HomeUiState()
}