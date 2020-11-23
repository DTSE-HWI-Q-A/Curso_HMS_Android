package com.dtse.demo.tictactoe

import android.content.Context
import android.content.SharedPreferences


object Preference {
    private const val NAME = "TicTacToe"
    private const val MODE = Context.MODE_PRIVATE
    private lateinit var preferences: SharedPreferences

    private val GAMES_WON_PREF = Pair("games_won", 0)

    fun init(context: Context) {
        preferences = context.getSharedPreferences(NAME, MODE)
    }

    private inline fun SharedPreferences.edit(operation: (SharedPreferences.Editor) -> Unit) {
        val editor = edit()
        operation(editor)
        editor.apply()
    }

    var gamesWon: Int
        get() = preferences.getInt(GAMES_WON_PREF.first, GAMES_WON_PREF.second)
        set(value) = preferences.edit {
            it.putInt(GAMES_WON_PREF.first, value)
        }
}