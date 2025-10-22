package com.example.tictaktoeproject2

import android.content.Context
import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import org.json.JSONArray
import org.json.JSONObject
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class GameActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game)

        val tvDifficulty = findViewById<TextView>(R.id.tvDifficulty)

        // ✅ Load saved settings from SharedPreferences
        val prefs = getSharedPreferences("TicTacToePrefs", Context.MODE_PRIVATE)
        val mode = prefs.getString("mode", "AI")
        val difficulty = prefs.getString("difficulty", "Easy")

        val difficultyText = if (mode == "AI") {
            getString(R.string.difficulty_label, difficulty)
        } else {
            getString(R.string.mode_label, mode)
        }
        tvDifficulty.text = difficultyText

        // 🧩 Example call (simulate end of a game)
        // Later, replace this with your real game logic
        saveGameResult(this, winner = "Player X", difficulty = "Medium")
    }

    private fun saveGameResult(context: Context, winner: String, difficulty: String) {
        val sharedPrefs = context.getSharedPreferences("past_games", Context.MODE_PRIVATE)
        val jsonString = sharedPrefs.getString("games_list", "[]")
        val gamesArray = JSONArray(jsonString)

        val currentDateTime =
            SimpleDateFormat("MMM dd, yyyy HH:mm", Locale.getDefault()).format(Date())

        val newGame = JSONObject().apply {
            put("dateTime", currentDateTime)
            put("winner", winner)
            put("difficulty", difficulty)
        }

//        val prefs = getSharedPreferences("TicTacToePrefs", Context.MODE_PRIVATE)
//        val difficulty = prefs.getString("difficulty", "Easy") // Default if none
//        val mode = prefs.getString("mode", "AI")


        gamesArray.put(newGame)
        sharedPrefs.edit().putString("games_list", gamesArray.toString()).apply()
    }
}
