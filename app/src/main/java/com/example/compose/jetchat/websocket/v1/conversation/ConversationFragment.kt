/*
 * Copyright 2020 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.example.compose.jetchat.websocket.v1.conversation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.ViewGroup.LayoutParams
import android.view.ViewGroup.LayoutParams.MATCH_PARENT
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.platform.ComposeView
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.findNavController
import com.example.compose.jetchat.MainViewModel
import com.example.compose.jetchat.R
import com.example.compose.jetchat.websocket.v1.conversation.ConversationContent
import com.example.compose.jetchat.websocket.v1.conversation.ConversationUiState
import com.example.compose.jetchat.websocket.v1.conversation.Message
import com.example.compose.jetchat.theme.JetchatTheme
import com.example.compose.jetchat.websocket.v1.WsV1SocketManager
import androidx.compose.runtime.remember
import androidx.compose.runtime.mutableStateListOf

class ConversationFragment : Fragment() {
    private val activityViewModel: MainViewModel by activityViewModels()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View =
        ComposeView(inflater.context).apply {
            layoutParams = LayoutParams(MATCH_PARENT, MATCH_PARENT)

            setContent {
                val messages = remember {
                    mutableStateListOf<Message>()
                }

                val socketManager = remember {
                    WsV1SocketManager { text ->
                        messages.add(
                            Message(
                                author = "WS",
                                content = text,
                                timestamp = "now"
                            )
                        )
                    }
                }

                val uiState = ConversationUiState(
                    initialMessages = messages,
                    channelName = "ws-v1",
                    channelMembers = 1
                )

                val emptyUiState = ConversationUiState(
                    initialMessages = listOf(
                        Message(
                            author = "System",
                            content = "This message is controlled by me",
                            timestamp = "now"
                        )
                    ),
                    channelName = "123",
                    channelMembers = 42,
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
                            // 1. Show immediately in UI
                            messages.add(
                                Message(
                                    author = "me",
                                    content = text,
                                    timestamp = "now"
                                )
                            )

                            // 2. Send to WebSocket
                            socketManager.send(text)
                        }
                    )
                }

                LaunchedEffect(Unit) {
                    socketManager.connect()
                }
            }
        }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //socketManager.connect()
    }
}
