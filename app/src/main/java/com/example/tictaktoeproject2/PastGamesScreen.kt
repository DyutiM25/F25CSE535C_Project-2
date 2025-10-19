package com.example.tictaktoeproject2

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable

@Composable
fun PastGamesScreen(
    gameRecords: List<GameRecord>,
    onBackClick: () -> Unit
) {
    // TODO: Implement past games screen with table showing date/time, winner, difficulty
    Text("Past Games Screen")
}