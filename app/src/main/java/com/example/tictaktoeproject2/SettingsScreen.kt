package com.example.tictaktoeproject2

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable

@Composable
fun SettingsScreen(
    currentDifficulty: Difficulty,
    onDifficultyChange: (Difficulty) -> Unit,
    onBackClick: () -> Unit
) {
    // TODO: Implement settings screen with radio buttons for Easy/Medium/Hard
    Text("Settings Screen")
}