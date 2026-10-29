package com.example.permissions.ui.screens

import android.content.Context
import android.net.ConnectivityManager
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp

@Composable
fun NetworkStatusScreen() {
    val context = LocalContext.current
    var isOnline by remember { mutableStateOf(checkOnline(context)) }

    Column(
        modifier = Modifier.fillMaxSize().padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text("Task 2: Normal Permissions (Internet)", style = MaterialTheme.typography.titleMedium)
        Spacer(modifier = Modifier.height(16.dp))

        // Normal permissions need no runtime dialog; declared in manifest means granted
        Text(
            text = if (isOnline) "Status: Online 🟢" else "Status: Offline 🔴",
            style = MaterialTheme.typography.headlineSmall
        )

        Spacer(modifier = Modifier.height(16.dp))
        Button(onClick = { isOnline = checkOnline(context) }) {
            Text("Refresh Status")
        }
    }
}

fun checkOnline(context: Context): Boolean {
    val cm = context.getSystemService(ConnectivityManager::class.java)
    return cm?.activeNetwork != null
}