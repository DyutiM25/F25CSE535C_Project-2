package com.example.tictaktoeproject2


import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity


class DifficultyActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_difficulty)

        val btnEasy: Button = findViewById(R.id.btnEasy)
        val btnMedium: Button = findViewById(R.id.btnMedium)
        val btnHard: Button = findViewById(R.id.btnHard)

        btnEasy.setOnClickListener {
            startActivity(Intent(this, EasyActivity::class.java))
        }
        btnMedium.setOnClickListener {
            startActivity(Intent(this, MediumActivity::class.java))
        }
        btnHard.setOnClickListener {
            startActivity(Intent(this, HardActivity::class.java))
        }

    }
}
