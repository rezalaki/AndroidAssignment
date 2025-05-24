package ir.miare.androidcodechallenge.data.model.response

import com.fasterxml.jackson.annotation.JsonProperty
import java.io.Serializable

data class Player(
    @JsonProperty("name") val name: String,
    @JsonProperty("team") val team: Team,
    @JsonProperty("total_goal") val totalGoal: Int
) : Serializable {

    /** unique id, to use in LazyColumn as Key */
    val fakeId: Int = (totalGoal * 10) + team.rank

    /** index of row, when showing in a list */
    var index = 0

    companion object {

        /**  generate fake Player for preview composable   */
        fun generateFakePlayer() = Player(
            name = "Mahdi Mahdavi Kia",
            team = Team("Bayern Munich", 2),
            totalGoal = 200
        )
    }

}