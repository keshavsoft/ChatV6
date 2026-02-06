package com.example.compose.jetchat

import androidx.compose.material3.DrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle

@Composable
fun HandleDrawerOpen(
    drawerState: DrawerState,
    viewModel: MainViewModel
) {
    val drawerOpen by viewModel.drawerShouldBeOpened.collectAsStateWithLifecycle()

    if (drawerOpen) {
        LaunchedEffect(Unit) {
            try {
                drawerState.open()
            } finally {
                viewModel.resetOpenDrawerAction()
            }
        }
    }
}