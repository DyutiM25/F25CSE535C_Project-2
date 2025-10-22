package com.example.tictaktoeproject2

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface GameDao {
    // Non-suspend insert returning the row id to avoid kapt / stub conversion issues.
    @Insert
    fun insertGame(game: GameRecord): Long

    @Query("SELECT * FROM past_games ORDER BY id DESC")
    fun getAllGames(): List<GameRecord>
}
