package com.example.tictaktoeproject2.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.tictaktoeproject2.R
import com.example.tictaktoeproject2.model.Game

class PastGamesAdapter(private val games: List<Game>) :
    RecyclerView.Adapter<PastGamesAdapter.GameViewHolder>() {

    inner class GameViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvDetails: TextView = itemView.findViewById(R.id.tvGameDetails)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GameViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_game, parent, false)
        return GameViewHolder(view)
    }

    override fun onBindViewHolder(holder: GameViewHolder, position: Int) {
        val game = games[position]
        holder.tvDetails.text =
            "Date/Time: ${game.dateTime}\nWinner: ${game.winner}\nDifficulty: ${game.difficulty}"
    }

    override fun getItemCount() = games.size
}
