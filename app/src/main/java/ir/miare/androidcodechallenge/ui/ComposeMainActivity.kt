package ir.miare.androidcodechallenge.ui


import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Surface
import androidx.compose.ui.Modifier
import dagger.hilt.android.AndroidEntryPoint
import ir.miare.androidcodechallenge.ui.homescreen.HomeScreen
import ir.miare.androidcodechallenge.ui.theme.MainAppTheme
import ir.miare.androidcodechallenge.ui.theme.backgroundWhite


@AndroidEntryPoint
class ComposeMainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MainAppTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = backgroundWhite,
                ) {
                    HomeScreen()
                }
            }
        }
    }
}