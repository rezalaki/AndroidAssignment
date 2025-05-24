package ir.miare.androidcodechallenge.ui.homescreen.items


import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material.Divider
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import ir.miare.androidcodechallenge.data.model.response.Player
import ir.miare.androidcodechallenge.ui.theme.Typography
import ir.miare.androidcodechallenge.utils.clickableIf


@Composable
fun PlayerItem(
    index: Int,
    player: Player,
    showTeamName: Boolean,
    onClicked: ((Player) -> Unit)?
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(68.dp)
            .clickableIf(onClicked != null) {
                onClicked!!.invoke(player)
            },
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Text(
            modifier = Modifier.fillMaxWidth(0.09F),
            textAlign = TextAlign.Center,
            text = index.toString(),
            style = Typography.h3
        )
        Column(
            modifier = Modifier.fillMaxWidth(1F), verticalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = player.name,
                textAlign = TextAlign.Center,
                style = Typography.h3
            )
            if (showTeamName) {
                Divider(thickness = 4.dp, color = Color.Transparent)
                Text(
                    text = player.team.name,
                    textAlign = TextAlign.Center,
                    style = Typography.h5
                )
            }
        }
    }
}


@Preview(showBackground = true)
@Composable
private fun PlayerItemPreview() {
    PlayerItem(1, player = Player.generateFakePlayer(), showTeamName = true) {

    }
}