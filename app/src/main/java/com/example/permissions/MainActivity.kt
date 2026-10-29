package com.example.permissions

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.permissions.ui.screens.*
import com.example.permissions.ui.theme.PermissionsTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            PermissionsTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    var selectedTask by remember { mutableIntStateOf(2) }

                    // statusBarsPadding() qo'shildi 👇
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .statusBarsPadding()
                    ) {
                        ScrollableTabRow(selectedTabIndex = selectedTask - 2) {
                            for (task in 2..6) {
                                Tab(
                                    selected = selectedTask == task,
                                    onClick = { selectedTask = task },
                                    text = { Text("Task $task") }
                                )
                            }
                        }

                        // Display the active task screen
                        Box(
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(16.dp)
                        ) {
                            when (selectedTask) {
                                2 -> NetworkStatusScreen()
                                3 -> CameraPermissionsScreen()
                                4 -> OverlayPermissionScreen()
                                5 -> LocationPermissionScreen()
                                6 -> NotificationScreen()
                            }
                        }
                    }
                }
            }
        }
    }
}