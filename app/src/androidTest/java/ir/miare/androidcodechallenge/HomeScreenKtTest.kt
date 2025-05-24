package ir.miare.androidcodechallenge

import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import ir.miare.androidcodechallenge.data.model.FakeDataFlatted
import ir.miare.androidcodechallenge.data.model.response.FakeData
import ir.miare.androidcodechallenge.data.model.response.League
import ir.miare.androidcodechallenge.data.model.response.Player
import ir.miare.androidcodechallenge.data.model.response.Team
import ir.miare.androidcodechallenge.data.model.toFakeDataFlatted
import ir.miare.androidcodechallenge.data.model.toSortByType
import ir.miare.androidcodechallenge.ui.homescreen.ResultListBox
import ir.miare.androidcodechallenge.ui.homescreen.SortingBox
import ir.miare.androidcodechallenge.ui.homescreen.viewmodel.SortTypes
import org.junit.Rule
import org.junit.Test


class HomeScreenKtTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    private val fakeDataList = listOf(
        FakeData(
            league = League(
                name = "Serie A",
                country = "Italy",
                rank = 3,
                totalMatches = 32
            ),
            players = listOf(
                Player(
                    name = "Edin Dzeko",
                    totalGoal = 17,
                    team = Team(name = "Inter", rank = 2)
                ),
                Player(
                    name = "Angel Di Maria",
                    totalGoal = 9,
                    team = Team(name = "Juventus", rank = 3)
                ),
                Player(
                    name = "Zlatan Ibrahimovic",
                    totalGoal = 17,
                    team = Team(name = "Ac Milan", rank = 1)
                )
            )
        ),
        FakeData(
            league = League(
                name = "Premier League",
                country = "England",
                rank = 1,
                totalMatches = 38
            ),
            players = listOf(
                Player(
                    name = "Mohammad Salah",
                    totalGoal = 25,
                    team = Team(name = "Liverpool", rank = 2)
                ),
                Player(
                    name = "Erling Haaland",
                    totalGoal = 33,
                    team = Team(name = "Man City", rank = 1)
                ),
                Player(
                    name = "Marcus Rashford",
                    totalGoal = 17,
                    team = Team(name = "Man United", rank = 3)
                )
            )
        ),
        FakeData(
            league = League(
                name = "LaLiga",
                country = "Spain",
                rank = 2,
                totalMatches = 36
            ),
            players = listOf(
                Player(
                    name = "Antoine Griezmann",
                    totalGoal = 21,
                    team = Team(name = "Atletico", rank = 3)
                ),
                Player(
                    name = "Karim Benzema",
                    totalGoal = 27,
                    team = Team(name = "Real Madrid", rank = 2)
                ),
                Player(
                    name = "Robert Lewandowski",
                    totalGoal = 23,
                    team = Team(name = "Barcelona", rank = 1)
                )
            )
        )
    )

    private val flattenList = listOf(
        FakeDataFlatted.LeagueFlatted(
            league = League(
                name = "Serie A",
                country = "Italy",
                rank = 3,
                totalMatches = 32
            )
        ),
        FakeDataFlatted.PlayerFlatted(
            player = Player(
                name = "Edin Dzeko",
                team = Team(name = "Inter", rank = 2),
                totalGoal = 17
            )
        ),
        FakeDataFlatted.PlayerFlatted(
            player = Player(
                name = "Angel Di Maria",
                team = Team(name = "Juventus", rank = 3),
                totalGoal = 9
            )
        ),
        FakeDataFlatted.PlayerFlatted(
            player = Player(
                name = "Zlatan Ibrahimovic",
                team = Team(name = "Ac Milan", rank = 1),
                totalGoal = 17
            )
        ),
        FakeDataFlatted.LeagueFlatted(
            league = League(
                name = "Premier League",
                country = "England",
                rank = 1,
                totalMatches = 38
            )
        ),
        FakeDataFlatted.PlayerFlatted(
            player = Player(
                name = "Mohammad Salah",
                team = Team(name = "Liverpool", rank = 2),
                totalGoal = 25
            )
        ),
        FakeDataFlatted.PlayerFlatted(
            player = Player(
                name = "Erling Haaland",
                team = Team(name = "Man City", rank = 1),
                totalGoal = 33
            )
        ),
        FakeDataFlatted.PlayerFlatted(
            player = Player(
                name = "Marcus Rashford",
                team = Team(name = "Man United", rank = 3),
                totalGoal = 17
            )
        ),
        FakeDataFlatted.LeagueFlatted(
            league = League(
                name = "LaLiga",
                country = "Spain",
                rank = 2,
                totalMatches = 36
            )
        ),
        FakeDataFlatted.PlayerFlatted(
            player = Player(
                name = "Antoine Griezmann",
                team = Team(name = "Atletico", rank = 3),
                totalGoal = 21
            )
        ),
        FakeDataFlatted.PlayerFlatted(
            player = Player(
                name = "Karim Benzema",
                team = Team(name = "Real Madrid", rank = 2),
                totalGoal = 27
            )
        ),
        FakeDataFlatted.PlayerFlatted(
            player = Player(
                name = "Robert Lewandowski",
                team = Team(name = "Barcelona", rank = 1),
                totalGoal = 23
            )
        )
    )

    @Test
    fun is_seri_A_Italy_first_when_most_goals_radio_btn_click() {
        val resultFinalList = mutableListOf<FakeDataFlatted>()
        composeTestRule.setContent {
            val type = remember {
                mutableStateOf(SortTypes.NONE)
            }
            Column {
                SortingBox(sortTypesList = SortTypes.entries) {
                    type.value = it
                }
                val sortedFlattedList = remember(type.value) {
                    fakeDataList
                        .toSortByType(type.value)
                        .toFakeDataFlatted()
                }
                resultFinalList.add(sortedFlattedList.first())
                ResultListBox(dataList = sortedFlattedList, sortType = type.value) {
                }
            }
        }
        Thread.sleep(2000)
        composeTestRule
            .onNodeWithText(SortTypes.MOST_GOAL.title)
            .performClick()
        Thread.sleep(2000)

        val isSerieAItalyLeagueFirst = when (resultFinalList.first()) {
            is FakeDataFlatted.LeagueFlatted -> (resultFinalList.first() as FakeDataFlatted.LeagueFlatted).league.name == "Serie A"
            is FakeDataFlatted.PlayerFlatted -> false
        }
        assert(isSerieAItalyLeagueFirst)

        Thread.sleep(2000)
    }

    @Test
    fun result_list_box_is_serie_A_Italy_displayed() {
        composeTestRule.setContent {
            ResultListBox(dataList = flattenList, sortType = SortTypes.NONE) {

            }
        }

        Thread.sleep(2000)

        composeTestRule
            .onNodeWithText("Serie A - Italy")
            .assertIsDisplayed()
    }

    @Test
    fun sorting_box_radio_btn_text_display() {
        composeTestRule.setContent {
            SortingBox(sortTypesList = SortTypes.entries) {
            }
        }

        composeTestRule
            .onNodeWithText("Team and league ranking")
            .assertIsDisplayed()

        Thread.sleep(2000)
    }

}