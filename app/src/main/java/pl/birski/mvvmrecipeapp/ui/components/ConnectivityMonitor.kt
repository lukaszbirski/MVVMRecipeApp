package pl.birski.mvvmrecipeapp.ui.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import pl.birski.mvvmrecipeapp.R

@Composable
fun ConnectivityMonitor(
    isNetworkAvailable: Boolean
) {
    if (!isNetworkAvailable) {
        Column(modifier = Modifier.fillMaxWidth()) {
            Text(
                text = stringResource(id = R.string.no_network_connection_text),
                modifier = Modifier.align(Alignment.CenterHorizontally),
                style = MaterialTheme.typography.h6
            )
        }
    }
}
