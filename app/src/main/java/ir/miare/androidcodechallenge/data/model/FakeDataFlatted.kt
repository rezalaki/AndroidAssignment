package ir.miare.androidcodechallenge.data.model

import ir.miare.androidcodechallenge.data.model.response.League
import ir.miare.androidcodechallenge.data.model.response.Player

/**
 * A sealed class used to flatten hierarchical data from [FakeData] into a single list structure.
 * This is particularly useful for displaying both leagues and their players in a single [LazyColumn],
 * avoiding nested LazyColumns which can lead to performance issues or scroll conflicts.
 *
 * @see LeagueFlatted Represents a flattened league item.
 * @see PlayerFlatted Represents a flattened player item under a league.
 */
sealed class FakeDataFlatted {
    data class LeagueFlatted(val league: League) : FakeDataFlatted()
    data class PlayerFlatted(val player: Player) : FakeDataFlatted()
}