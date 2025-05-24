package ir.miare.androidcodechallenge.ui.homescreen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import ir.miare.androidcodechallenge.data.model.base.Player
import ir.miare.androidcodechallenge.ui.theme.Typography


@Composable
fun PlayerDetailBottomSheetContent(
    player: Player,
    onBackButtonClicked: () -> Unit
) {
    Column(
        modifier = Modifier.padding(horizontal = 16.dp)
    ) {
        Text(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 16.dp),
            text = player.name,
            style = Typography.h2
        )
        Row(modifier = Modifier.padding(vertical = 8.dp)) {
            Text(
                modifier = Modifier.padding(end = 8.dp),
                text = "Club:",
                style = Typography.h5
            )
            Text(
                text = player.team.name,
                style = Typography.h3
            )
        }
        Row(modifier = Modifier.padding(vertical = 8.dp)) {
            Text(
                modifier = Modifier.padding(end = 8.dp),
                text = "Goals:",
                color = Color.Gray,
                style = Typography.h5
            )
            Text(
                text = player.totalGoal.toString(),
                style = Typography.h3
            )
        }
        Button(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 32.dp),
            onClick = onBackButtonClicked
        ) {
            Text(
                modifier = Modifier.padding(vertical = 8.dp),
                text = "Back"
            )
        }
    }
}


@Preview(showBackground = true)
@Composable
private fun PlayerDetailBottomSheetContentPreview() {
    PlayerDetailBottomSheetContent(player = Player.generateFakePlayer()) {

    }
}