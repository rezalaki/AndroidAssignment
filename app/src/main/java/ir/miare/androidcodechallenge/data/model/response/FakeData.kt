package ir.miare.androidcodechallenge.data.model.response

import com.fasterxml.jackson.annotation.JsonProperty
import ir.miare.androidcodechallenge.data.model.FakeDataFlatted
import ir.miare.androidcodechallenge.ui.homescreen.viewmodel.SortTypes


data class FakeData(
    @JsonProperty("league") var league: League,
    @JsonProperty("players") var players: List<Player>
) {
    companion object {

        /**  generate fake sample for preview composable   */
        fun generateSampleFakeData() = FakeData(
            league = League.generateFakeLeague(),
            players = listOf(
                Player.generateFakePlayer()
            )
        )
    }
}
