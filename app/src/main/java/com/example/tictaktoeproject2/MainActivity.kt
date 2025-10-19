package com.example.tictaktoeproject2

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.Text
import androidx.compose.runtime.*

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TicTacToeApp()
        }
    }
}

@Composable
fun TicTacToeApp() {

    Text("TicTacToe App UI to be implemented")
}