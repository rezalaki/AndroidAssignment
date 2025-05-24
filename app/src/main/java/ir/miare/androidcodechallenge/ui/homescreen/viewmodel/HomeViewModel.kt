package ir.miare.androidcodechallenge.ui.homescreen.viewmodel


import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import ir.miare.androidcodechallenge.data.model.toFakeDataFlatted
import ir.miare.androidcodechallenge.data.model.toFakeDataSortedBy
import ir.miare.androidcodechallenge.data.repository.FakeDataRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class HomeViewModel @Inject constructor(
    private val fakeDataRepository: FakeDataRepository
) : ViewModel() {

    private val _uiState: MutableStateFlow<HomeUiState> = MutableStateFlow(HomeUiState.Loading)
    val uiState = _uiState.asStateFlow()

    fun handleSortTypeUpdates(sortType: SortTypes) {
        loadData(sortType)
    }

    private fun loadData(sortType: SortTypes) = viewModelScope.launch(Dispatchers.IO) {
        _uiState.value = HomeUiState.Loading
        fakeDataRepository
            .loadFakeData()
            .collect { apiResult ->

                apiResult.fold({ apiData ->

                    val sortedAndFlattedList = apiData
                        .toFakeDataSortedBy(sortType) // extension fun
                        .toFakeDataFlatted() // extension fun

                    _uiState.value = HomeUiState.LoadSuccess(sortedAndFlattedList)

                }, { throwable ->
                    _uiState.value = HomeUiState.LoadFailed(throwable.message.orEmpty(), sortType)
                })

            }
    }


    /**
     * Retrieves a list of all available [SortTypes] values.
     *
     * This function returns all entries defined in the [SortTypes] enum,
     * representing the different sorting criteria that can be applied to the data:
     * - [SortTypes.NONE]
     * - [SortTypes.LEAGUE_RANK]
     * - [SortTypes.MOST_GOAL]
     * - [SortTypes.AVERAGE_GOAL]
     *
     * @return A list of all [SortTypes] enum values.
     */
    fun getSortingTypesList(): List<SortTypes> = SortTypes.entries

}