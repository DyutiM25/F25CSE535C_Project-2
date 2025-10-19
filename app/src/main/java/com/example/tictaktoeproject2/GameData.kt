package com.example.tictaktoeproject2

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "past_games")
data class GameRecord(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val dateTime: String,
    val winner: String,
    val difficulty: String
)

enum class Difficulty {
    Easy, Medium, Hard
}

enum class Player {
    X, O
}

enum class Screen {
    Game, Settings, PastGames
}