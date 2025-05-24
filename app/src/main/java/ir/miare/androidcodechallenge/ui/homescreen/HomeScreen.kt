package ir.miare.androidcodechallenge.ui.homescreen

import android.widget.Toast
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import ir.miare.androidcodechallenge.data.model.FakeDataFlatted
import ir.miare.androidcodechallenge.data.model.base.FakeData
import ir.miare.androidcodechallenge.data.model.base.Player
import ir.miare.androidcodechallenge.data.model.base.toFakeDataFlatted
import ir.miare.androidcodechallenge.ui.homescreen.items.LeagueItem
import ir.miare.androidcodechallenge.ui.homescreen.items.PlayerItem
import ir.miare.androidcodechallenge.ui.homescreen.items.RadioButtonItem
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
    val homeState = viewModel.uiState.collectAsStateWithLifecycle()
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
            when (homeState.value) {
                HomeUiState.Loading -> CircularProgressIndicator(
                    modifier = Modifier
                        .width(48.dp)
                        .height(48.dp)
                )

                is HomeUiState.LoadFailed -> {
                    val (errorMsg, lastSortingType) = (homeState.value as HomeUiState.LoadFailed)
                    ErrorBox(errorMessage = errorMsg) {
                        viewModel.handleSortTypeUpdates(lastSortingType)
                    }
                }

                is HomeUiState.LoadSuccessFlattedFakeData -> {
                    val flattedFakeData =
                        (homeState.value as HomeUiState.LoadSuccessFlattedFakeData).data
                    ResultListBox(
                        dataList = flattedFakeData,
                        onPlayerClicked = onPlayerClicked
                    )
                }

                is HomeUiState.LoadSuccessPlayer -> {
                    val players = (homeState.value as HomeUiState.LoadSuccessPlayer).data
                    ResultBoxPlayers(
                        dataList = players,
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
                RadioButtonItem(optionPair = optionPair,
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


@Composable
fun ResultBoxPlayers(
    dataList: List<Player>,
    onPlayerClicked: (Player) -> Unit
) {
    var i = 0
    LazyColumn(modifier = Modifier.fillMaxWidth()) {
        items(
            items = dataList,
            key = { (it.totalGoal * 10) + it.team.rank }
        ) { player ->
            PlayerItem(index = ++i, player = player, showTeamName = true) {
                onPlayerClicked.invoke(it)
            }
            Divider()
        }
    }
}

// =================== Result List Box

@Composable
fun ResultListBox(
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
                    LeagueItem(title = item.league.getTitleAndCountry())
                }

                is FakeDataFlatted.PlayerFlatted -> {
                    PlayerItem(item.player.team.rank, item.player, true) {
                        onPlayerClicked.invoke(it)
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
    val data = listOf(FakeData.generateSampleFakeData())
    val flatted = data.toFakeDataFlatted()
    ResultListBox(dataList = flatted) {

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


