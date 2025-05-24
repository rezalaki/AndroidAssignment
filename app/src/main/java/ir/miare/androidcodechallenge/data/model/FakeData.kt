package ir.miare.androidcodechallenge.data.model

import com.fasterxml.jackson.annotation.JsonProperty
import ir.miare.androidcodechallenge.ui.homescreen.viewmodel.SortTypes


data class FakeData(
    @JsonProperty("league") var league: League,
    @JsonProperty("players") var players: List<Player>
) {
    companion object {
        fun generateSampleFakeData() = FakeData(
            league = League.generateFakeLeague(),
            players = listOf(
                Player.generateFakePlayer()
            )
        )
    }
}

/**
 * Extension function to sort a list of [FakeData] based on the provided [SortTypes]
 *
 * The sorting behavior depends on the selected [sortType]:
 * - [SortTypes.NONE]: No sorting is applied.
 * - [SortTypes.LEAGUE_RANK]: Sorts by the league's rank in ascending order.
 * - [SortTypes.MOST_GOAL]: Sorts by the total number of goals scored by players in each league, in descending order.
 * - [SortTypes.AVERAGE_GOAL]: Sorts by the average number of goals per match across all players in a league, in descending order.
 *
 *
 * @param [FakeData] list of fake data to be sorted and mapped.
 * @param sortType type of sorting to apply.
 * @return A sorted list of [FakeData].
 */
fun List<FakeData>.toFakeDataSortedBy(sortType: SortTypes) =
    when (sortType) {
        SortTypes.NONE -> this

        SortTypes.LEAGUE_RANK ->
            this.sortedBy {
                it.league.rank
            }

        SortTypes.MOST_GOAL ->
            this.sortedByDescending {
                it.players.sumOf { player -> player.totalGoal }
            }

        SortTypes.AVERAGE_GOAL ->
            this.sortedByDescending {
                it.players.sumOf { player -> player.totalGoal } / it.league.totalMatches
            }
    }


/**
 * Extension function to convert list of [FakeData] into a flattened list of [FakeDataFlatted] objects, to
 * display in a single LazyColumn. Each [FakeData] is represented by a [FakeDataFlatted.LeagueFlatted]
 * entry for the league, followed by [FakeDataFlatted.PlayerFlatted] entries for its associated players.
 *
 * @return A list of [FakeDataFlatted] objects, where each league is followed by its players.
 */
fun List<FakeData>.toFakeDataFlatted(): List<FakeDataFlatted> {
    val tempList = mutableListOf<FakeDataFlatted>()
    this.forEach { fakeData ->
        var i = 0
        tempList.add(
            FakeDataFlatted.LeagueFlatted(fakeData.league)
        )
        tempList.addAll(
            fakeData.players.map { player ->
                player.index = ++i
                FakeDataFlatted.PlayerFlatted(player)
            }
        )
    }
    return tempList
}




