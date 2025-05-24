package ir.miare.androidcodechallenge.ui.homescreen.items

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.selection.selectable
import androidx.compose.material.RadioButton
import androidx.compose.material.RadioButtonDefaults
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import ir.miare.androidcodechallenge.ui.homescreen.viewmodel.SortTypes
import ir.miare.androidcodechallenge.ui.theme.Typography


@Composable
fun RadioButtonItem(
    optionPair: List<SortTypes>,
    selectedSort: SortTypes,
    onRadioClicked: (SortTypes) -> Unit
) {
    Row(modifier = Modifier.fillMaxWidth()) {
        optionPair.onEach { singleOption ->
            Row(
                Modifier
                    .fillMaxWidth()
                    .height(50.dp)
                    .weight(1f)
                    .padding(horizontal = 8.dp, vertical = 4.dp)
                    .selectable(
                        selected = (singleOption == selectedSort),
                        onClick = { onRadioClicked.invoke(singleOption) },
                        role = Role.RadioButton
                    ), verticalAlignment = Alignment.CenterVertically
            ) {
                RadioButton(
                    selected = (singleOption == selectedSort),
                    onClick = null,
                    colors = RadioButtonDefaults.colors(selectedColor = Color.Blue)
                )
                Text(
                    text = singleOption.title,
                    modifier = Modifier.padding(start = 8.dp),
                    style = Typography.h3
                )
            }
        }
    }
}


@Preview(showBackground = true)
@Composable
private fun RadioButtonItemPreview() {
    RadioButtonItem(optionPair = SortTypes.entries, selectedSort = SortTypes.NONE) {

    }
}
