package ir.miare.androidcodechallenge.ui.theme


import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Shapes // For Material 2
import androidx.compose.ui.unit.dp


val Shapes = Shapes(
    small = RoundedCornerShape(4.dp),
    medium = RoundedCornerShape(
        topEnd = 24.dp,
        topStart = 24.dp,
        bottomEnd = 0.dp,
        bottomStart = 0.dp
    ),
    large = RoundedCornerShape(0.dp)
)
