package ir.miare.androidcodechallenge.ui.homescreen.items

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import ir.miare.androidcodechallenge.data.model.response.Team
import ir.miare.androidcodechallenge.ui.theme.Typography


@Composable
fun TeamItem(team: Team) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 16.dp),
    ) {
        Text(
            text = team.rank.toString(),
            modifier = Modifier
                .fillMaxWidth(0.1F),
            style = Typography.h3,
            textAlign = TextAlign.Center
        )
        Text(
            modifier = Modifier
                .fillMaxWidth(0.8F),
            text = team.name,
            style = Typography.h3
        )
    }
}

@Preview(showBackground = true)
@Composable
fun TeamItemPreview() {
    val sampleTeam = Team.generateFakeTeam()
    TeamItem(team = sampleTeam)
}


