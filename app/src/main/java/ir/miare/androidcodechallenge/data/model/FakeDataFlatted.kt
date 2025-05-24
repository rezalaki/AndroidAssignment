package ir.miare.androidcodechallenge.data.model

import ir.miare.androidcodechallenge.data.model.base.League
import ir.miare.androidcodechallenge.data.model.base.Player
import ir.miare.androidcodechallenge.data.model.base.Team


sealed class FakeDataFlatted {
    data class LeagueFlatted(val league: League) : FakeDataFlatted()
    data class PlayerFlatted(val player: Player) : FakeDataFlatted()
}