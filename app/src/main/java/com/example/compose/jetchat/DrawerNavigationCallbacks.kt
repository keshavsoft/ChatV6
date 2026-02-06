package com.example.compose.jetchat

import androidx.compose.material3.DrawerState
import androidx.navigation.NavController
import com.example.compose.jetchat.components.CHAT_VERSIONS
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import androidx.core.os.bundleOf

fun handleChatClick(
    item: String,
    navController: NavController,
    drawerState: DrawerState,
    scope: CoroutineScope,
    onSelected: (String) -> Unit
) {
    val version = CHAT_VERSIONS.find { it.id == item }

    if (version != null) {
        navController.navigate(version.navId)
    } else {
        navController.popBackStack(R.id.nav_home, false)
    }

    scope.launch { drawerState.close() }
    onSelected(item)
}

fun handleProfileClick(
    userId: String,
    navController: NavController,
    drawerState: DrawerState,
    scope: CoroutineScope,
    onSelected: (String) -> Unit
) {
    navController.navigate(
        R.id.nav_profile,
        bundleOf("userId" to userId)
    )
    scope.launch { drawerState.close() }
    onSelected(userId)
}