package ir.miare.androidcodechallenge.data.model.response

import com.fasterxml.jackson.annotation.JsonProperty
import java.io.Serializable

data class Team(
    @JsonProperty("name") val name: String,
    @JsonProperty("rank") val rank: Int
) : Serializable {

    companion object {

        /**  generate fake Team for preview composable   */
        fun generateFakeTeam(): Team =
            Team(
                name = "fake team name",
                rank = (1..10).random()
            )
    }

}