package pl.birski.mvvmrecipeapp.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.AlertDialog
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun GenericDialog(
    modifier: Modifier = Modifier,
    onDismiss: () -> Unit,
    title: String,
    description: String? = null,
    positiveAction: PositiveAction? = null,
    negativeAction: NegativeAction? = null,
) {
    AlertDialog(
        modifier = modifier,
        onDismissRequest = onDismiss,
        title = { Text(text = title) },
        text = { description?.let { Text(text = it) } },
        buttons = {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp),
                horizontalArrangement = Arrangement.End
            ) {
                negativeAction?.let {
                    Button(
                        modifier = Modifier.padding(8.dp),
                        colors = ButtonDefaults.buttonColors(backgroundColor = MaterialTheme.colors.error),
                        onClick = it.onNegativeAction
                    ) {
                        Text(text = it.negativeBtnText)
                    }
                }
                positiveAction?.let {
                    Button(
                        modifier = Modifier.padding(8.dp),
                        onClick = it.onPositiveAction
                    ) {
                        Text(text = it.positiveBtnText)
                    }
                }
            }
        }
    )
}

data class PositiveAction(
    val positiveBtnText: String,
    val onPositiveAction: () -> Unit,
)

data class NegativeAction(
    val negativeBtnText: String,
    val onNegativeAction: () -> Unit,
)
