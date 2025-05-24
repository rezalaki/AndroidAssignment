package ir.miare.androidcodechallenge.data.model

import com.fasterxml.jackson.annotation.JsonProperty

data class League(
    @JsonProperty("name") val name: String,
    @JsonProperty("country") val country: String,
    @JsonProperty("rank") val rank: Int,
    @JsonProperty("total_matches") val totalMatches: Int,
) {
    val fakeId: Int
        get() = (1_000..9_999).random()

    fun getTitleAndCountry() = "$name - $country"

    companion object {
        fun generateFakeLeague() = League(
            name = "Fake League",
            country = "Fake Country",
            rank = 1,
            totalMatches = 100
        )
    }

}