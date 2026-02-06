package com.example.compose.jetchat.websocket.common.conversation.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.remember
import androidx.compose.ui.draganddrop.DragAndDropEvent
import androidx.compose.ui.draganddrop.DragAndDropTarget
import androidx.compose.ui.draganddrop.toAndroidDragEvent
import androidx.compose.ui.graphics.Color
import com.example.compose.jetchat.websocket.common.conversation.Message

@Composable
fun rememberConversationDragAndDrop(
    authorMe: String,
    timeNow: String,
    onMessageDropped: (Message) -> Unit,
    background: MutableState<Color>,
    borderStroke: MutableState<Color>,
): DragAndDropTarget {
    return remember {
        object : DragAndDropTarget {

            override fun onDrop(event: DragAndDropEvent): Boolean {
                val clipData = event.toAndroidDragEvent().clipData
                if (clipData.itemCount < 1) return false

                onMessageDropped(
                    Message(
                        author = authorMe,
                        content = clipData.getItemAt(0).text.toString(),
                        timestamp = timeNow,
                    )
                )
                return true
            }

            override fun onStarted(event: DragAndDropEvent) {
                borderStroke.value = Color.Red
            }

            override fun onEntered(event: DragAndDropEvent) {
                background.value = Color.Red.copy(alpha = .3f)
            }

            override fun onExited(event: DragAndDropEvent) {
                background.value = Color.Transparent
            }

            override fun onEnded(event: DragAndDropEvent) {
                background.value = Color.Transparent
                borderStroke.value = Color.Transparent
            }
        }
    }
}