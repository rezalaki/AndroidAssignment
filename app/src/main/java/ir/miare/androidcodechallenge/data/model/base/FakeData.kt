package ir.miare.androidcodechallenge.data.model.base

import android.util.Log
import com.fasterxml.jackson.annotation.JsonProperty
import ir.miare.androidcodechallenge.data.model.FakeDataFlatted
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

///**
// * Extension function to sort a list of [FakeData] based on the provided [SortTypes]
// *
// * The sorting behavior depends on the selected [sortType]:
// * - [SortTypes.NONE]: No sorting is applied.
// * - [SortTypes.TEAM_LEAGUE_RANK]: Sorts by the league's rank in ascending order.
// * - [SortTypes.MOST_GOAL]: Sorts by the total number of goals scored by players in each league, in descending order.
// * - [SortTypes.AVERAGE_GOAL]: Sorts by the average number of goals per match across all players in a league, in descending order.
// *
// *
// * @param [FakeData] list of fake data to be sorted and mapped.
// * @param sortType type of sorting to apply.
// * @return A sorted list of [FakeData].
// */

fun List<FakeData>.toFakeDataSortedBy(sortType: SortTypes) {
    val d = when (sortType) {
        // List<FakeData>
        SortTypes.NONE -> this

        // List<FakeData>
        SortTypes.TEAM_LEAGUE_RANK ->
            sortedBy { it.league.rank }
                .map { fakeData ->
                    fakeData.copy(
                        players = fakeData.players.sortedBy { player -> player.team.rank }
                    )
                }

        // List<Player>
        SortTypes.MOST_GOAL ->
            flatMap { it.players }
                .sortedByDescending { player -> player.totalGoal }

        // List<Player>
        SortTypes.AVERAGE_GOAL ->
            flatMap { fakeData ->
                    fakeData.players.map { player ->
                        val avg = player.totalGoal.toFloat() / fakeData.league.totalMatches.toFloat()
                        Log.d("TAGGGG", ">>> ${player.name} | $avg")
                        player to avg
                    }
                }
                .sortedByDescending {
                    it.second
                }
                .map { it.first }
    }
    Log.d("TAGGGG", "after sort (${sortType.title}): ")
    Log.d("TAGGGG", d.toString())
}


///**
// * Extension function to convert list of [FakeData] into a flattened list of [FakeDataFlatted] objects, to
// * display in a single LazyColumn. Each [FakeData] is represented by a [FakeDataFlatted.LeagueFlatted]
// * entry for the league, followed by [FakeDataFlatted.PlayerFlatted] entries for its associated players.
// *
// * @return A list of [FakeDataFlatted] objects, where each league is followed by its players.
// */
fun List<FakeData>.toFakeDataFlatted(): List<FakeDataFlatted> {
    val tempList = mutableListOf<FakeDataFlatted>()
    this.forEach { fakeData ->
        tempList.add(
            FakeDataFlatted.LeagueFlatted(fakeData.league)
        )
        tempList.addAll(
            fakeData.players.map { player ->
                FakeDataFlatted.PlayerFlatted(player)
            }
        )
    }
    return tempList
}




