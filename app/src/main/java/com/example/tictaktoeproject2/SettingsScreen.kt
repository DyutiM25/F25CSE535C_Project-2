package com.example.tictaktoeproject2

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.selection.selectable
import androidx.compose.material3.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsScreen(
    currentDifficulty: Difficulty,
    onDifficultyChange: (Difficulty) -> Unit,
    onBackClick: () -> Unit
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Settings") },
                navigationIcon = {
                    IconButton(onClick = onBackClick) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp)
        ) {
            DifficultySelection(
                currentDifficulty = currentDifficulty,
                onDifficultyChange = onDifficultyChange
            )

            // Add more for peer-to-peer here
        }
    }
}

@Composable
fun DifficultySelection(
    currentDifficulty: Difficulty,
    onDifficultyChange: (Difficulty) -> Unit
) {
    Text(
        text = "DIFFICULTY LEVEL",
        fontSize = 16.sp,
        fontWeight = FontWeight.Bold,
        modifier = Modifier.padding(bottom = 12.dp)
    )

    val difficulties = listOf(Difficulty.Easy, Difficulty.Medium, Difficulty.Hard)

    difficulties.forEach { difficulty ->
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .selectable(
                    selected = currentDifficulty == difficulty,
                    onClick = { onDifficultyChange(difficulty) }
                )
                .padding(vertical = 8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            RadioButton(
                selected = currentDifficulty == difficulty,
                onClick = { onDifficultyChange(difficulty) }
            )
            Text(
                text = difficulty.name,
                fontSize = 15.sp,
                fontWeight = FontWeight.Medium,
                modifier = Modifier.padding(start = 8.dp)
            )
        }
    }
}