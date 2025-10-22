package com.example.tictaktoeproject2

import android.content.Context
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.edit

class SettingsActivity : AppCompatActivity() {

    private lateinit var difficultyGroup: RadioGroup
    private lateinit var modeGroup: RadioGroup
    private lateinit var difficultySection: LinearLayout
    private lateinit var btnSaveSettings: Button

    companion object {
        private const val PREFS_NAME = "TicTacToePrefs"
        private const val KEY_MODE = "mode"
        private const val KEY_DIFFICULTY = "difficulty"

        private const val MODE_AI = "AI"
        private const val MODE_HUMAN_LOCAL = "Human-OnDevice"
        private const val MODE_HUMAN_REMOTE = "Human-Remote"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)

        modeGroup = findViewById(R.id.modeGroup)
        difficultyGroup = findViewById(R.id.difficultyGroup)
        difficultySection = findViewById(R.id.difficultySection)
        btnSaveSettings = findViewById(R.id.btnSaveSettings)

        val prefs = getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        val savedMode = prefs.getString(KEY_MODE, MODE_AI)
        val savedDifficulty = prefs.getString(KEY_DIFFICULTY, "Easy")

        // Restore saved mode
        when (savedMode) {
            MODE_AI -> {
                modeGroup.check(R.id.rbAI)
                difficultySection.visibility = View.VISIBLE
            }
            MODE_HUMAN_LOCAL -> modeGroup.check(R.id.rbHumanLocal)
            MODE_HUMAN_REMOTE -> modeGroup.check(R.id.rbHumanRemote)
        }

        // Restore difficulty (only if AI was selected)
        when (savedDifficulty) {
            "Easy" -> difficultyGroup.check(R.id.rbEasy)
            "Medium" -> difficultyGroup.check(R.id.rbMedium)
            "Hard" -> difficultyGroup.check(R.id.rbHard)
        }

        // Toggle difficulty visibility when mode changes
        modeGroup.setOnCheckedChangeListener { _, checkedId ->
            difficultySection.visibility =
                if (checkedId == R.id.rbAI) View.VISIBLE else View.GONE
        }

        // ✅ Save settings when button clicked
        btnSaveSettings.setOnClickListener {
            val selectedModeId = modeGroup.checkedRadioButtonId
            val selectedDifficultyId = difficultyGroup.checkedRadioButtonId

            if (selectedModeId == -1) {
                Toast.makeText(this, getString(R.string.select_game_mode_warning), Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val selectedMode = when (selectedModeId) {
                R.id.rbAI -> MODE_AI
                R.id.rbHumanLocal -> MODE_HUMAN_LOCAL
                R.id.rbHumanRemote -> MODE_HUMAN_REMOTE
                else -> MODE_AI
            }

            val selectedDifficulty =
                if (selectedMode == MODE_AI) {
                    if (selectedDifficultyId == -1) {
                        Toast.makeText(this, getString(R.string.select_difficulty_warning), Toast.LENGTH_SHORT).show()
                        return@setOnClickListener
                    }
                    findViewById<RadioButton>(selectedDifficultyId).text.toString()
                } else {
                    "N/A"
                }

            prefs.edit {
                putString(KEY_MODE, selectedMode)
                putString(KEY_DIFFICULTY, selectedDifficulty)
            }

            Toast.makeText(this, getString(R.string.settings_saved_message), Toast.LENGTH_SHORT).show()
            finish()
        }
    }
}
