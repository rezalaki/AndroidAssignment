package ir.miare.androidcodechallenge.data.model.response

import com.fasterxml.jackson.annotation.JsonProperty

data class League(
    @JsonProperty("name") val name: String,
    @JsonProperty("country") val country: String,
    @JsonProperty("rank") val rank: Int,
    @JsonProperty("total_matches") val totalMatches: Int,
) {
    fun getNameAndCountry() = "$name - $country"

    companion object {

        /**  generate fake league for preview composable   */
        fun generateFakeLeague() = League(
            name = "Fake League",
            country = "Fake Country",
            rank = 1,
            totalMatches = 100
        )

        /**  generate empty league, which is needed in .toSortByType() extension function   */
        fun generateEmptyLeague() = League(
            name = "",
            country = "",
            rank = (10_000..99_000).random(),
            totalMatches = (10_000..99_000).random()
        )

    }

}