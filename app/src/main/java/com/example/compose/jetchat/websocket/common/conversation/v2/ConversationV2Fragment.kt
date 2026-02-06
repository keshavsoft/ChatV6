package com.example.compose.jetchat.websocket.common.conversation.v2

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.ui.platform.ComposeView
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.findNavController
import com.example.compose.jetchat.MainViewModel
import com.example.compose.jetchat.R
import com.example.compose.jetchat.theme.JetchatTheme

class ConversationV2Fragment : Fragment() {
    private val activityViewModel: MainViewModel by activityViewModels()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View =
        ComposeView(inflater.context).apply {
            layoutParams = ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT
            )

            setContent {
                JetchatTheme {
                    ConversationV2Engine(
                        channelName = "ws-v2",
                        logTag = "V2",
                        navigateToProfile = { user ->
                            findNavController().navigate(
                                R.id.nav_profile,
                                bundleOf("userId" to user)
                            )
                        },
                        onNavIconPressed = {
                            activityViewModel.openDrawer()
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