package com.example.compose.jetchat

import androidx.navigation.NavController
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import androidx.core.os.bundleOf
import androidx.compose.material3.DrawerState
fun handleChatClick(
    item: String,
    navController: NavController,
    drawerState: DrawerState,
    scope: CoroutineScope,
    onSelected: (String) -> Unit
) {
    when (item) {
        "ws_v1" -> navController.navigate(R.id.nav_ws_v1)
        "ws_v2" -> navController.navigate(R.id.nav_ws_v2)
        else -> navController.popBackStack(R.id.nav_home, false)
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
    val bundle = bundleOf("userId" to userId)
    navController.navigate(R.id.nav_profile, bundle)
    scope.launch { drawerState.close() }
    onSelected(userId)
}