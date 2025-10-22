package com.example.tictaktoeproject2

import android.os.Bundle
import android.view.View
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.tictaktoeproject2.adapter.PastGamesAdapter
import com.example.tictaktoeproject2.model.Game
import org.json.JSONArray

class PastGamesActivity : AppCompatActivity() {

    private lateinit var rvPastGames: RecyclerView
    private lateinit var emptyStateLayout: LinearLayout
    private lateinit var tvSampleGame: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_past_games)

        rvPastGames = findViewById(R.id.rvPastGames)
        emptyStateLayout = findViewById(R.id.emptyStateLayout)
        tvSampleGame = findViewById(R.id.tvSampleGame)

        val pastGamesList = loadPastGames()

        if (pastGamesList.isNotEmpty()) {
            rvPastGames.layoutManager = LinearLayoutManager(this)
            rvPastGames.adapter = PastGamesAdapter(pastGamesList)
            rvPastGames.visibility = View.VISIBLE
            emptyStateLayout.visibility = View.GONE
        } else {
            rvPastGames.visibility = View.GONE
            emptyStateLayout.visibility = View.VISIBLE
            tvSampleGame.text = getString(
                R.string.no_previous_games_message,
                "Oct 22, 2025 10:45",
                "Player X",
                "Hard"
            )
        }
    }

    private fun loadPastGames(): List<Game> {
        val sharedPrefs = getSharedPreferences("past_games", MODE_PRIVATE)
        val jsonString = sharedPrefs.getString("games_list", "[]") ?: "[]"
        val jsonArray = JSONArray(jsonString)
        val gamesList = mutableListOf<Game>()

        for (i in 0 until jsonArray.length()) {
            val obj = jsonArray.getJSONObject(i)
            gamesList.add(
                Game(
                    dateTime = obj.getString("dateTime"),
                    winner = obj.getString("winner"),
                    difficulty = obj.getString("difficulty")
                )
            )
        }

        return gamesList
    }
}
