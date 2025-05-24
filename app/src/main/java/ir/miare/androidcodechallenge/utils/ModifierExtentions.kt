package ir.miare.androidcodechallenge.utils

import androidx.compose.foundation.clickable
import androidx.compose.ui.Modifier


fun Modifier.clickableIf(condition: Boolean, onClick: () -> Unit): Modifier {
    return if (condition) this.clickable { onClick.invoke() }
    else this
}