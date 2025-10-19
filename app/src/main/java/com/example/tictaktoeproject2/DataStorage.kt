package com.example.tictaktoeproject2

import android.content.Context
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import java.text.SimpleDateFormat
import java.util.*

fun saveGameRecord(context: Context, winner: Player?, difficulty: Difficulty) {
    val database = GameDatabase.getDatabase(context)
    val dateFormat = SimpleDateFormat("MM/dd/yyyy hh:mm a", Locale.getDefault())
    
    val winnerText = when (winner) {
        Player.X -> "Player"
        Player.O -> "Opponent"
        null -> "Draw"
    }
    
    val diffText = when (difficulty) {
        Difficulty.Easy -> "Easy"
        Difficulty.Medium -> "Medium"
        Difficulty.Hard -> "Hard"
    }
    
    val gameRecord = GameRecord(
        dateTime = dateFormat.format(Date()),
        winner = winnerText,
        difficulty = diffText
    )
    
    CoroutineScope(Dispatchers.IO).launch {
        database.gameDao().insertGame(gameRecord)
    }
}

fun loadGameRecords(context: Context): List<GameRecord> {
    val database = GameDatabase.getDatabase(context)
    return runBlocking { //Block the thread to prevent users from doing anything else in UI until all records fetched
        database.gameDao().getAllGames()
    }
}