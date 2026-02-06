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

@file:OptIn(ExperimentalMaterial3Api::class)

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.example.compose.jetchat.R
import com.example.compose.jetchat.theme.JetchatTheme
import com.example.compose.jetchat.websocket.common.conversation.ConversationUiState
import com.example.compose.jetchat.websocket.common.conversation.ui.ChannelNameBar
import com.example.compose.jetchat.websocket.common.conversation.ui.ConversationScaffold
import com.example.compose.jetchat.websocket.common.conversation.ui.DayHeader
import com.example.compose.jetchat.websocket.common.conversation.ui.Messages
import com.example.compose.jetchat.websocket.common.conversation.ui.UserInput
import com.example.compose.jetchat.websocket.common.conversation.ui.rememberConversationDragAndDrop
import kotlinx.coroutines.launch

/**
 * Entry point for a conversation screen.
 *
 * @param uiState [ConversationUiState] that contains messages to display
 * @param navigateToProfile User action when navigation to a profile is requested
 * @param modifier [Modifier] to apply to this layout node
 * @param onNavIconPressed Sends an event up when the user clicks on the menu
 */
@OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)
@Composable
fun ConversationContent(
    uiState: ConversationUiState,
    navigateToProfile: (String) -> Unit,
    onNavIconPressed: () -> Unit,
    onMessageSent: (String) -> Unit,
) {
    val authorMe = stringResource(R.string.author_me)
    val timeNow = stringResource(R.string.now)

    val scrollState = rememberLazyListState()
    val topBarState = rememberTopAppBarState()
    val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior(topBarState)
    val scope = rememberCoroutineScope()

    val background = remember { mutableStateOf(Color.Transparent) }
    val borderStroke = remember { mutableStateOf(Color.Transparent) }

    val dragAndDrop = rememberConversationDragAndDrop(
        authorMe = authorMe,
        timeNow = timeNow,
        onMessageDropped = uiState::addMessage,
        background = background,
        borderStroke = borderStroke
    )

    ConversationScaffold(
        backgroundColor = background.value,
        borderColor = borderStroke.value,
        scrollBehavior = scrollBehavior,
        dragAndDropTarget = dragAndDrop,
        topBar = {
            ChannelNameBar(
                channelName = uiState.channelName,
                channelMembers = uiState.channelMembers,
                onNavIconPressed = onNavIconPressed,
                scrollBehavior = scrollBehavior
            )
        }
    ) {
        Messages(
            messages = uiState.messages,
            navigateToProfile = navigateToProfile,
            scrollState = scrollState,
            modifier = Modifier.weight(1f)
        )
        UserInput(
            onMessageSent = onMessageSent,
            resetScroll = { scope.launch { scrollState.scrollToItem(0) } },
            modifier = Modifier.navigationBarsPadding().imePadding()
        )
    }
}

const val ConversationTestTag = "ConversationTestTag"

@Preview
@Composable
fun ChannelBarPrev() {
    JetchatTheme {
        ChannelNameBar(channelName = "composers", channelMembers = 52)
    }
}

@Preview
@Composable
fun DayHeaderPrev() {
    DayHeader("Aug 6")
}
