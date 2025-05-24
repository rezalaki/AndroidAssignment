package ir.miare.androidcodechallenge.ui.homescreen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Button
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Divider
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.ModalBottomSheetLayout
import androidx.compose.material.ModalBottomSheetValue
import androidx.compose.material.Text
import androidx.compose.material.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import ir.miare.androidcodechallenge.data.model.FakeDataFlatted
import ir.miare.androidcodechallenge.data.model.response.FakeData
import ir.miare.androidcodechallenge.data.model.response.Player
import ir.miare.androidcodechallenge.data.model.toFakeDataFlatted
import ir.miare.androidcodechallenge.ui.homescreen.items.LeagueItem
import ir.miare.androidcodechallenge.ui.homescreen.items.PlayerItem
import ir.miare.androidcodechallenge.ui.homescreen.items.RadioButtonItem
import ir.miare.androidcodechallenge.ui.homescreen.items.TeamItem
import ir.miare.androidcodechallenge.ui.homescreen.viewmodel.HomeUiState
import ir.miare.androidcodechallenge.ui.homescreen.viewmodel.HomeViewModel
import ir.miare.androidcodechallenge.ui.homescreen.viewmodel.SortTypes
import ir.miare.androidcodechallenge.ui.theme.Shapes
import ir.miare.androidcodechallenge.ui.theme.Typography
import ir.miare.androidcodechallenge.ui.theme.backgroundBlue
import kotlinx.coroutines.launch


@OptIn(ExperimentalMaterialApi::class)
@Composable
fun HomeScreen() {
    var player by remember {
        mutableStateOf<Player?>(null)
    }
    val sheetState = rememberModalBottomSheetState(
        initialValue = ModalBottomSheetValue.Hidden
    )
    val coroutineScope = rememberCoroutineScope()
    ModalBottomSheetLayout(
        sheetState = sheetState,
        sheetContent = {
            player?.let {
                PlayerDetailBottomSheetContent(
                    player = it,
                    onBackButtonClicked = {
                        coroutineScope.launch { sheetState.hide() }
                    }
                )
            }
        },
        sheetShape = Shapes.medium,
        content = {
            HomeContent { selectedPlayer ->
                player = selectedPlayer
                coroutineScope.launch { sheetState.show() }
            }
        }
    )
}


@Composable
fun HomeContent(
    viewModel: HomeViewModel = hiltViewModel(),
    onPlayerClicked: (Player) -> Unit
) {
    val sortingTypesList = viewModel.getSortingTypesList()
    val state = viewModel.uiState.collectAsStateWithLifecycle()
    LaunchedEffect(key1 = Unit) {
        viewModel.handleSortTypeUpdates(SortTypes.NONE)
    }

    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        // ============= Top Box
        SortingBox(
            sortTypesList = sortingTypesList,
            onSortItemClicked = {
                viewModel.handleSortTypeUpdates(it)
            }
        )
        // ============= Bottom Box
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1F)
                .align(Alignment.CenterHorizontally),
            contentAlignment = Alignment.Center
        ) {
            when (state.value) {
                HomeUiState.Loading -> CircularProgressIndicator(
                    modifier = Modifier
                        .width(48.dp)
                        .height(48.dp)
                )

                is HomeUiState.LoadFailed -> {
                    val (errorMsg, lastSortingType) = (state.value as HomeUiState.LoadFailed)
                    ErrorBox(errorMessage = errorMsg) {
                        viewModel.handleSortTypeUpdates(lastSortingType)
                    }
                }

                is HomeUiState.LoadSuccess -> {
                    val (flattedFakeData, sortType) = (state.value as HomeUiState.LoadSuccess)
                    ResultListBox(
                        sortType,
                        dataList = flattedFakeData,
                        onPlayerClicked = onPlayerClicked
                    )
                }
            }
        }
    }
}

// =================== Sorting Box

@Composable
fun SortingBox(
    sortTypesList: List<SortTypes>,
    onSortItemClicked: (SortTypes) -> Unit
) {
    val (selectedSort, onOptionSelected) = remember {
        mutableStateOf(sortTypesList.first())
    }
    val options = sortTypesList.toList().chunked(2)
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(color = backgroundBlue)
    ) {
        Text(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 12.dp)
                .testTag("SortingByTag"),
            text = "Sorting by:",
            style = Typography.h3,
            textAlign = TextAlign.Center
        )
        LazyColumn {
            items(options) { optionPair ->
                RadioButtonItem(
                    optionPair = optionPair,
                    selectedSort = selectedSort,
                    onRadioClicked = { clickedRadio ->
                        onOptionSelected.invoke(clickedRadio)
                        onSortItemClicked.invoke(clickedRadio)
                    }
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun SortingBoxPreview() {
    val sortTypesList = SortTypes.entries
    SortingBox(sortTypesList) {
    }
}

// =================== Result List Box

@Composable
fun ResultListBox(
    sortType: SortTypes,
    dataList: List<FakeDataFlatted>,
    onPlayerClicked: (Player) -> Unit
) {
    LazyColumn(
        modifier = Modifier.fillMaxWidth()
    ) {
        items(
            items = dataList,
            key = {
                when (it) {
                    is FakeDataFlatted.LeagueFlatted -> it.league.rank
                    is FakeDataFlatted.PlayerFlatted -> it.player.fakeId
                }
            }
        ) { item ->
            when (item) {
                is FakeDataFlatted.LeagueFlatted -> {
                    if (sortType == SortTypes.NONE || sortType == SortTypes.TEAM_LEAGUE_RANK)
                        LeagueItem(title = item.league.getNameAndCountry())
                }

                is FakeDataFlatted.PlayerFlatted -> {
                    when (sortType) {
                        SortTypes.NONE ->
                            PlayerItem(
                                index = item.player.team.rank,
                                player = item.player,
                                showTeamName = true,
                                onClicked = onPlayerClicked
                            )


                        SortTypes.TEAM_LEAGUE_RANK ->
                            TeamItem(team = item.player.team)

                        SortTypes.MOST_GOAL ->
                            PlayerItem(
                                index = item.player.index,
                                player = item.player,
                                showTeamName = false,
                                onClicked = onPlayerClicked
                            )

                        SortTypes.AVERAGE_GOAL ->
                            PlayerItem(
                                index = item.player.index,
                                player = item.player,
                                showTeamName = true,
                                onClicked = null
                            )
                    }
                }
            }
            Divider()
        }
    }
}


@Preview(showBackground = true)
@Composable
fun ResultListBoxPreview() {
    val flatted = listOf(FakeData.generateSampleFakeData()).toFakeDataFlatted()
    ResultListBox(dataList = flatted, sortType = SortTypes.NONE) {

    }
}

// =================== Error Box

@Composable
fun ErrorBox(errorMessage: String, onTryAgainClicked: () -> Unit) {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Something went wrong:\n$errorMessage",
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            style = Typography.h3,
            textAlign = TextAlign.Center
        )
        Button(onClick = { onTryAgainClicked.invoke() }) {
            Text(text = "Try Again")
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ErrorBoxPreView() {
    ErrorBox("Api error happened") {

    }
}


