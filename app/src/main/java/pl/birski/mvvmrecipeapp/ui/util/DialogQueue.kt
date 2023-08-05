package pl.birski.mvvmrecipeapp.ui.util

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import pl.birski.mvvmrecipeapp.ui.components.GenericDialogInfo
import pl.birski.mvvmrecipeapp.ui.components.PositiveAction
import java.util.LinkedList
import java.util.Queue

class DialogQueue {
    val queue: MutableState<Queue<GenericDialogInfo>> = mutableStateOf(LinkedList())

    fun appendErrorMessage(title: String, description: String) {
        queue.value.offer(
            GenericDialogInfo.Builder()
                .title(title)
                .onDismiss(this::removeHeadMessage)
                .description(description)
                .positiveAction(
                    PositiveAction(
                        positiveBtnText = "OK",
                        onPositiveAction = this::removeHeadMessage
                    )
                )
                .build()
        )
    }

    private fun removeHeadMessage() {
        if (queue.value.isNotEmpty()) {
            val update = queue.value
            update.remove() // remove first (oldest message)
            queue.value = LinkedList() // force recompose (bug?)        // todo @lukasz this logic don't work correctly
            queue.value = update
        }
    }
}
