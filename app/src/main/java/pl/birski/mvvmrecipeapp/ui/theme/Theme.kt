package pl.birski.mvvmrecipeapp.ui.theme

import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import pl.birski.mvvmrecipeapp.ui.components.CircularProgressBar
import pl.birski.mvvmrecipeapp.ui.components.ConnectivityMonitor
import pl.birski.mvvmrecipeapp.ui.components.GenericDialog
import pl.birski.mvvmrecipeapp.ui.components.GenericDialogInfo
import java.util.Queue

private val LightThemeColors = lightColors(
    primary = Blue600,
    primaryVariant = Blue400,
    onPrimary = Black2,
    secondary = Color.White,
    secondaryVariant = Teal300,
    onSecondary = Color.Black,
    error = RedErrorDark,
    onError = RedErrorLight,
    background = Grey1,
    onBackground = Color.Black,
    surface = Color.White,
    onSurface = Black2
)

private val DarkThemeColors = darkColors(
    primary = Blue700,
    primaryVariant = Color.White,
    onPrimary = Color.White,
    secondary = Black1,
    onSecondary = Color.White,
    error = RedErrorLight,
    background = Color.Black,
    onBackground = Color.White,
    surface = Black1,
    onSurface = Color.White
)

@Composable
fun AppTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    isNetworkAvailable: Boolean,
    displayProgressBar: Boolean,
    dialogQueue: Queue<GenericDialogInfo>,
    content: @Composable () -> Unit
) {
    val colors = if (darkTheme) {
        DarkThemeColors
    } else {
        LightThemeColors
    }

    MaterialTheme(
        colors = colors,
        typography = Typography,
        shapes = Shapes
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(color = if (!darkTheme) Grey1 else Color.Black)
        ) {
            Column {
                ConnectivityMonitor(isNetworkAvailable = isNetworkAvailable)
                content()
            }
            CircularProgressBar(isDisplayed = displayProgressBar, 0.3f)
            ProcessDialogQueue(dialogQueue = dialogQueue)
        }
    }
}

@Composable
fun ProcessDialogQueue(
    dialogQueue: Queue<GenericDialogInfo>
) {
    dialogQueue.peek()?.let {
        GenericDialog(
            dialogInfo = GenericDialogInfo.Builder()
                .onDismiss { it.onDismiss }
                .title(it.title)
                .description(it.description)
                .positiveAction(it.positiveAction)
                .negativeAction(it.negativeAction)
                .build()
        )
    }
}
