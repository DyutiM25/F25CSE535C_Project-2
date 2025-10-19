package com.example.tictaktoeproject2

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface GameDao {
    @Insert
    suspend fun insertGame(game: GameRecord)
    
    @Query("SELECT * FROM past_games ORDER BY id DESC")
    suspend fun getAllGames(): List<GameRecord>
}