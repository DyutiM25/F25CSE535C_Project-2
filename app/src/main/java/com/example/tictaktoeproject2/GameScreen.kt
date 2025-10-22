package com.example.tictaktoeproject2

import android.content.Context
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
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
    val context = LocalContext.current
    val scope = rememberCoroutineScope()

    // Local UI state for AI thinking indicator (we may override incoming aiThinking)
    var localAiThinking by remember { mutableStateOf(false) }

    // Helper: check and persist game if ended
    fun checkAndSaveIfEnded(ctx: Context) {
        val result = checkWinner(board) // returns Pair<Player?, String> - Player? null => draw
        val resPlayer = result.first
        val resReason = result.second
        if (resPlayer != null || resReason == "Draw") {
            // Map result to winner Player? as expected by saveGameRecord
            // saveGameRecord(context, winner: Player?, difficulty: Difficulty)
            saveGameRecord(ctx, resPlayer, difficulty)
        }
    }

    // Called after a human move: schedule AI move if applicable
    fun onHumanMove(index: Int) {
        // Prevent clicking if not player's turn or game already over
        if (!isPlayerTurn || gameOver) return

        // Update via callback (teammate code likely expects this)
        onCellClick(index)

        // Re-check: if game ended after player's move, save and do not invoke AI
        val resultAfterHuman = checkWinner(board)
        if (resultAfterHuman.first != null || resultAfterHuman.second == "Draw") {
            checkAndSaveIfEnded(context)
            return
        }

        // Launch AI move
        scope.launch {
            localAiThinking = true
            // small delay to let UI show thinking state (optional)
            // and to emulate the "AI is thinking" as required on Hard mode if algorithm slow
            delay(150) // 150ms gives a small breathing room for UX

            val aiIndex = when (difficulty) {
                Difficulty.Easy -> getRandomMove(board)
                Difficulty.Medium -> getMediumMove(board)
                Difficulty.Hard -> getBestMove(board)
            }

            if (aiIndex != -1) {
                // update board directly (board is a mutable Array passed from host)
                board[aiIndex] = Player.O
            }

            // After AI move, check & save if game ended
            val resultAfterAI = checkWinner(board)
            if (resultAfterAI.first != null || resultAfterAI.second == "Draw") {
                checkAndSaveIfEnded(context)
            }

            localAiThinking = false
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(title = { Text("Misère Tic-Tac-Toe") },
                actions = {
                    TextButton(onClick = onPastGamesClick) { Text("Past Games") }
                    TextButton(onClick = onSettingsClick) { Text("Settings") }
                })
        },
        content = { paddingValues ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
                    .padding(12.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                // Status / turn indicator
                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                    Text(
                        text = if (gameOver) "Game Over" else if (localAiThinking || aiThinking) "AI is thinking..." else if (isPlayerTurn) "Your turn" else "Opponent's turn",
                        modifier = Modifier.padding(8.dp)
                    )

                    TextButton(onClick = onReset) {
                        Text("Reset")
                    }
                }

                Spacer(modifier = Modifier.height(8.dp))

                // Board (3x3)
                val cellSize = 100.dp
                Column {
                    for (row in 0..2) {
                        Row {
                            for (col in 0..2) {
                                val idx = row * 3 + col
                                Box(
                                    modifier = Modifier
                                        .size(cellSize)
                                        .padding(4.dp)
                                        .background(color = Color(0xFFECECEC), shape = RoundedCornerShape(8.dp))
                                        .clickable(enabled = !gameOver && (isPlayerTurn) && board[idx] == null && !localAiThinking) {
                                            // On click -> handle human move (index)
                                            onHumanMove(idx)
                                        },
                                    contentAlignment = Alignment.Center
                                ) {
                                    Text(
                                        text = when (board[idx]) {
                                            Player.X -> "X"
                                            Player.O -> "O"
                                            else -> ""
                                        },
                                        fontSize = 36.sp,
                                        textAlign = TextAlign.Center
                                    )
                                }
                            }
                        }
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                // Win message
                if (gameOver) {
                    Text(
                        text = when {
                            winner == Player.X -> "Player (X) wins — but remember Misère rules!"
                            winner == Player.O -> "Opponent (O) wins — but remember Misère rules!"
                            else -> "Draw"
                        },
                        fontSize = 18.sp,
                        modifier = Modifier.padding(8.dp)
                    )
                }

                Spacer(modifier = Modifier.height(8.dp))

                // Optional small legend + difficulty label
                Text(text = "Difficulty: ${difficulty.name}", modifier = Modifier.padding(4.dp))

                Spacer(modifier = Modifier.height(8.dp))

                // If AI thinking, show a progress indicator
                if (localAiThinking || aiThinking) {
                    CircularProgressIndicator(modifier = Modifier.size(36.dp))
                }
            }
        }
    )
}
