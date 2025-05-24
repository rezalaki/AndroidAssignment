package ir.miare.androidcodechallenge.data.model.base

import com.fasterxml.jackson.annotation.JsonProperty
import java.io.Serializable

data class Team(
    @JsonProperty("name") val name: String,
    @JsonProperty("rank") val rank: Int
) : Serializable {

    companion object {
        fun generateFakeTeam(): Team =
            Team(
                name = "fake team name",
                rank = (1..10).random()
            )
    }

}