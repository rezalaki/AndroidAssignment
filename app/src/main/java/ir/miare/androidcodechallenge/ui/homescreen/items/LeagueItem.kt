package ir.miare.androidcodechallenge.ui.homescreen.items


import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import ir.miare.androidcodechallenge.ui.theme.Typography
import ir.miare.androidcodechallenge.ui.theme.backgroundGray


@Composable
fun LeagueItem(title: String) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(48.dp)
            .background(color = backgroundGray),
        contentAlignment = Alignment.Center
    ) {
        Text(
            modifier = Modifier.fillMaxWidth(),
            text = title,
            style = Typography.h1
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun LeagueItemPreview() {
    LeagueItem(title = "Premier League")
}

