package ir.miare.androidcodechallenge.data.model

import ir.miare.androidcodechallenge.data.model.response.FakeData
import ir.miare.androidcodechallenge.data.model.response.League
import ir.miare.androidcodechallenge.ui.homescreen.viewmodel.SortTypes


/**
 * Extension function to sort a list of [FakeData] based on the provided [SortTypes]
 *
 * The sorting behavior depends on the selected [sortType]:
 * - [SortTypes.NONE]: No sorting is applied.
 * - [SortTypes.TEAM_LEAGUE_RANK]: Sorts by the league's rank in ascending order.
 * - [SortTypes.MOST_GOAL]: Sorts by the total number of goals scored by players in each league, in descending order.
 * - [SortTypes.AVERAGE_GOAL]: Sorts by the average number of goals per match across all players in a league, in descending order.
 *
 *
 * @param [FakeData] list of fake data to be sorted and mapped.
 * @param sortType type of sorting to apply.
 * @return A sorted list of [FakeData].
 */
fun List<FakeData>.toSortByType(sortType: SortTypes): List<FakeData> =
    when (sortType) {
        // returns List<FakeData>
        SortTypes.NONE -> {
            this
        }

        // returns List<FakeData>
        SortTypes.TEAM_LEAGUE_RANK -> {
            this
                .sortedBy { it.league.rank }
                .map { fakeData ->
                    fakeData.copy(players = fakeData.players.sortedBy { player -> player.team.rank })
                }
        }

        // returns List<Player>, but we convert it to List<FakeData>
        SortTypes.MOST_GOAL -> {
            val sortedPlayers = this.flatMap { it.players }
                .sortedByDescending { player -> player.totalGoal }

            // to convert FakeData into FakeDataFlatten, we need a League having empty data
            listOf(
                FakeData(
                    league = League.generateEmptyLeague(),
                    players = sortedPlayers.mapIndexed { mapIndex, player ->
                        player.apply {
                            index = (mapIndex + 1)
                        }
                    }
                )
            )
        }

        // returns List<Player>, but we convert it to List<FakeData>
        SortTypes.AVERAGE_GOAL -> {
            val sortedPlayers = this
                .flatMap { fakeData ->
                    fakeData.players.map { player ->
                        val avg =
                            player.totalGoal.toFloat() / fakeData.league.totalMatches.toFloat()
                        player to avg
                    }
                }.sortedByDescending {
                    it.second
                }.map { it.first }

            // to convert List<FakeData> into List<FakeDataFlatten>, we need a League having empty data
            listOf(
                FakeData(
                    league = League.generateEmptyLeague(),
                    players = sortedPlayers.mapIndexed { mapIndex, player ->
                        player.apply {
                            index = (mapIndex + 1)
                        }
                    }
                )
            )
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

