package ir.miare.androidcodechallenge.ui.theme

import androidx.compose.material.Typography
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp

val Typography = Typography(
    // League Title
    h1 = TextStyle(
        fontSize = 18.sp,
        color = Color.Gray,
        textAlign = TextAlign.Center
    ),

    // bottom sheet title
    h2 = TextStyle(
        fontSize = 16.sp,
        color = Color.Black,
        textAlign = TextAlign.Center,
        fontWeight = FontWeight.Bold
    ),

    // options & player name
    h3=TextStyle(
        fontSize = 15.sp,
        color = Color.Black,
    ),

    // team name
    h5 = TextStyle(
        fontSize = 13.sp,
        color = Color.Gray,
    )


)