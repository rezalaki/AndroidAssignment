package ir.miare.androidcodechallenge.data.model.base

import com.fasterxml.jackson.annotation.JsonProperty

data class League(
    @JsonProperty("name") val name: String,
    @JsonProperty("country") val country: String,
    @JsonProperty("rank") val rank: Int,
    @JsonProperty("total_matches") val totalMatches: Int,
) {
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