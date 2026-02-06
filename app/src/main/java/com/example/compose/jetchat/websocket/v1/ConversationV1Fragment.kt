package com.example.compose.jetchat.websocket.v1

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.ComposeView
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.findNavController
import com.example.compose.jetchat.AppWebSocketManager
import com.example.compose.jetchat.MainViewModel
import com.example.compose.jetchat.R
import com.example.compose.jetchat.theme.JetchatTheme
import com.example.compose.jetchat.websocket.common.conversation.ConversationContent
import com.example.compose.jetchat.websocket.common.conversation.ConversationUiState
import com.example.compose.jetchat.websocket.common.conversation.Message

class ConversationV1Fragment : Fragment() {
    private val activityViewModel: MainViewModel by activityViewModels()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View =
        ComposeView(inflater.context).apply {
            layoutParams = ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT
            )

            setContent {
                // 1. Local UI messages
                val messages = remember { mutableStateListOf<Message>() }

// 2. Listen to GLOBAL socket
                LaunchedEffect(Unit) {
                    AppWebSocketManager.events.collect { text ->
                        Log.d("V1", "UI received: $text")
                        messages.add(
                            Message(
                                author = "WS",
                                content = text,
                                timestamp = "now"
                            )
                        )
                    }
                }

// 3. Build UI state from local messages
                val uiState = ConversationUiState(
                    initialMessages = messages,
                    channelName = "ws-v1",
                    channelMembers = 1
                )

                JetchatTheme {
                    ConversationContent(
                        uiState = uiState,
                        navigateToProfile = { user ->
                            // Click callback
                            val bundle = bundleOf("userId" to user)
                            findNavController().navigate(
                                R.id.nav_profile,
                                bundle,
                            )
                        },
                        onNavIconPressed = {
                            activityViewModel.openDrawer()
                        },
                        onMessageSent = { text ->
                            messages.add(
                                Message("me", text, "now")
                            )
                            AppWebSocketManager.send(text)
                        }
                    )
                }
            }
        }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //socketManager.connect()
    }
}