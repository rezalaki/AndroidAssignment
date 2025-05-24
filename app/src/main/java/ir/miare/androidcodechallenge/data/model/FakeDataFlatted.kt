package ir.miare.androidcodechallenge.data.model



sealed class FakeDataFlatted {
    data class LeagueFlatted(val league: League) : FakeDataFlatted()
    data class PlayerFlatted(val player: Player) : FakeDataFlatted()
}