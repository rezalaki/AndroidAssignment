package ir.miare.androidcodechallenge.ui.homescreen.viewmodel


import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import ir.miare.androidcodechallenge.data.model.base.FakeData
import ir.miare.androidcodechallenge.data.model.base.League
import ir.miare.androidcodechallenge.data.model.base.Player
import ir.miare.androidcodechallenge.data.model.base.toFakeDataFlatted
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

                    when (sortType) {
                        SortTypes.NONE,
                        SortTypes.TEAM_LEAGUE_RANK -> {
                            val mappedToLeagueTeam = sortMapToTeamLeagueList(apiData, sortType)
                                ?.toFakeDataFlatted()
                            _uiState.value = if (mappedToLeagueTeam != null)
                                HomeUiState.LoadSuccessFlattedFakeData(mappedToLeagueTeam)
                            else
                                HomeUiState.LoadFailed(
                                    "error in sorting [$sortType] and mapping api data list",
                                    sortType
                                )
                        }

                        SortTypes.MOST_GOAL,
                        SortTypes.AVERAGE_GOAL -> {
                            val mappedToPlayer = sortMapToPlayerList(apiData, sortType)
                            _uiState.value = if (mappedToPlayer != null)
                                HomeUiState.LoadSuccessPlayer(mappedToPlayer)
                            else
                                HomeUiState.LoadFailed(
                                    "error in sorting [$sortType] and mapping api data list",
                                    sortType
                                )
                        }
                    }


                }, { throwable ->
                    _uiState.value = HomeUiState.LoadFailed(throwable.message.orEmpty(), sortType)
                })

            }
    }


    private fun sortMapToTeamLeagueList(
        dataList: List<FakeData>, sortType: SortTypes
    ): List<FakeData>? {
        return when (sortType) {
            SortTypes.TEAM_LEAGUE_RANK -> {
                dataList
                    .sortedBy { it.league.rank }
                    .map { fakeData ->
                        fakeData.copy(players = fakeData.players.sortedBy { player -> player.team.rank })
                    }
            }

            SortTypes.NONE -> {
                dataList
            }

            else -> {
                null
            }
        }
    }


    private fun sortMapToPlayerList(
        dataList: List<FakeData>, sortType: SortTypes
    ): List<Player>? {
        return when (sortType) {
            SortTypes.MOST_GOAL -> dataList
                .flatMap { it.players }
                .sortedByDescending { player -> player.totalGoal }

//            SortTypes.MOST_GOAL -> {
//                val sortedPlayers = dataList.flatMap { it.players }
//                    .sortedByDescending { player -> player.totalGoal }
//
//                listOf()
//
//            }

            SortTypes.AVERAGE_GOAL -> dataList
                .flatMap { fakeData ->
                    fakeData.players.map { player ->
                        val avg =
                            player.totalGoal.toFloat() / fakeData.league.totalMatches.toFloat()
                        player to avg
                    }
                }.sortedByDescending {
                    it.second
                }.map { it.first }

            else -> null
        }

    }


    /**
     * Retrieves a list of all available [SortTypes] values.
     *
     * This function returns all entries defined in the [SortTypes] enum,
     * representing the different sorting criteria that can be applied to the data:
     * - [SortTypes.NONE]
     * - [SortTypes.TEAM_LEAGUE_RANK]
     * - [SortTypes.MOST_GOAL]
     * - [SortTypes.AVERAGE_GOAL]
     *
     * @return A list of all [SortTypes] enum values.
     */
    fun getSortingTypesList(): List<SortTypes> = SortTypes.entries

}