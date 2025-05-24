package ir.miare.androidcodechallenge.data.model.base

import com.fasterxml.jackson.annotation.JsonProperty
import java.io.Serializable

data class Player(
    @JsonProperty("name") val name: String,
    @JsonProperty("team") val team: Team,
    @JsonProperty("total_goal") val totalGoal: Int
) : Serializable {

    /** unique id, to use in LazyColumn as Key */
    val fakeId: Int = (totalGoal * 10) + team.rank

    companion object {
        /**
         * Generate a [Player] data class with fake data.
         * @return a fake [Player] data class.
         */
        fun generateFakePlayer() = Player(
            name = "Mahdi Mahdavi Kia",
            team = Team("Bayern Munich", 2),
            totalGoal = 200
        )
    }

}