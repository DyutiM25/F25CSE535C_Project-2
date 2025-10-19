package com.example.tictaktoeproject2

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable

@Composable
fun GameScreen(
    difficulty: Difficulty,
    board: Array<Player?>,
    isPlayerTurn: Boolean,
    gameOver: Boolean,
    winner: Player?,
    winMessage: String,
    aiThinking: Boolean,
    onCellClick: (Int) -> Unit,
    onReset: () -> Unit,
    onSettingsClick: () -> Unit,
    onPastGamesClick: () -> Unit
) {
    // TODO: Implement UI game screen with 3x3 tic-tac-toe along with buttons to navigate to settings and past games pages
    Text("Game Screen - TODO")
}