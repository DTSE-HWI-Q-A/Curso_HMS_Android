package com.dtse.demo.tictactoe

import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TableRow
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.fragment_second.*
import java.util.*

class SecondFragment : Fragment(), View.OnClickListener {

    companion object {
        private const val TAG = "HMS_SecondFragment"

        private const val WIN_ONE =
            "****"
        private const val WIN_THREE =
            "****"
        private const val WIN_FIVE =
            "****"
        private const val WIN_FIVE_IN_ROW =
            "****"
        private const val LEADER_BOARD_ID =
            "****"
    }

    /*
    private val playersClient by lazy {
        Games.getPlayersClient(activity)
    }

    private val rankingsClient by lazy {
        Games.getRankingsClient(activity)
    }

    private val achievementsClient by lazy {
        Games.getAchievementsClient(activity)
    }
     */

    private var playerId: String? = null
    private var activePlayer = 1
    private var player1 = ArrayList<Int>()
    private var player2 = ArrayList<Int>()
    private var btnUsed = 0
    private var winInARow = 0
    private var gamesWon = 0

    private lateinit var hmsAuth: HmsAuth

    override fun onAttach(context: Context) {
        super.onAttach(context)
        try {
            hmsAuth = context as HmsAuth
        } catch (ex: ClassCastException) {
            throw ClassCastException("${ex.message} must implement HmsAuth interface")
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_second, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        gamesWon = Preference.gamesWon

        initializePlayer()
        initializePlayerScore()
        initializeViews()
    }

    private fun initializePlayer() {
    }

    private fun initializePlayerScore() {
        textViewGamesWon.text =
            getString(R.string.games_win_text, gamesWon)
        textViewGamesWonInARow.text =
            getString(
                R.string.games_win_in_a_row_text,
                winInARow
            )
    }

    private fun initializeViews() {
        btn1.setOnClickListener(this)
        btn2.setOnClickListener(this)
        btn3.setOnClickListener(this)
        btn4.setOnClickListener(this)
        btn5.setOnClickListener(this)
        btn6.setOnClickListener(this)
        btn7.setOnClickListener(this)
        btn8.setOnClickListener(this)
        btn9.setOnClickListener(this)

        buttonReset.setOnClickListener {
            clearBoard()
        }

        buttonSignOut.setOnClickListener {
            hmsAuth.signOut()
        }

        buttonLeaderBoard.setOnClickListener {
            showLeadersBoards()
        }

        buttonAchivementList.setOnClickListener {
            showAchievementsList()
        }
    }

    override fun onClick(v: View?) {
        val btnSelected = v as Button
        var cellID = 0
        when (btnSelected.id) {
            R.id.btn1 -> cellID = 1
            R.id.btn2 -> cellID = 2
            R.id.btn3 -> cellID = 3
            R.id.btn4 -> cellID = 4
            R.id.btn5 -> cellID = 5
            R.id.btn6 -> cellID = 6
            R.id.btn7 -> cellID = 7
            R.id.btn8 -> cellID = 8
            R.id.btn9 -> cellID = 9
        }
        playGame(cellID, btnSelected)
    }

    private fun playGame(cellID: Int, btnSelected: Button) {
        if (activePlayer == 1) {
            btnSelected.text = "X"
            context?.let { ContextCompat.getColor(it, R.color.colorPlayer) }?.let {
                btnSelected.setBackgroundColor(
                    it
                )
            }
            player1.add(cellID)
            activePlayer = 2
            autoPlay()
        } else {
            btnSelected.text = "O"
            context?.let { ContextCompat.getColor(it, R.color.colorMachine) }?.let {
                btnSelected.setBackgroundColor(
                    it
                )
            }
            player2.add(cellID)
            activePlayer = 1
        }

        btnSelected.isEnabled = false

        if (txtView.text.toString().isEmpty()) {
            checkWinner()
        }
    }

    private fun autoPlay() {
        val emptyCells = ArrayList<Int>()
        for (cellID in 1..9) {
            if (!((player1.contains(cellID)) || (player2.contains(cellID)))) {
                emptyCells.add(cellID)
            }
        }
        val random = Random()
        val randIndex = random.nextInt(emptyCells.size - 0) + 0
        val cellID = emptyCells[randIndex]

        if (emptyCells.isNotEmpty()) {
            val btnSelected: Button = when (cellID) {
                1 -> btn1
                2 -> btn2
                3 -> btn3
                4 -> btn4
                5 -> btn5
                6 -> btn6
                7 -> btn7
                8 -> btn8
                9 -> btn9
                else -> btn1
            }
            playGame(cellID, btnSelected)
        }
    }

    private fun checkWinner() {
        var winner = -1

        //row1
        if (player1.contains(1) && player1.contains(2) && player1.contains(3)) {
            winner = 1
        }
        if (player2.contains(1) && player2.contains(2) && player2.contains(3)) {
            winner = 2
        }
        //row2
        if (player1.contains(4) && player1.contains(5) && player1.contains(6)) {
            winner = 1
        }
        if (player2.contains(4) && player2.contains(5) && player2.contains(6)) {
            winner = 2
        }
        //row3
        if (player1.contains(7) && player1.contains(8) && player1.contains(9)) {
            winner = 1
        }
        if (player2.contains(7) && player2.contains(8) && player2.contains(9)) {
            winner = 2
        }

        //col1
        if (player1.contains(1) && player1.contains(4) && player1.contains(7)) {
            winner = 1
        }
        if (player2.contains(1) && player2.contains(4) && player2.contains(7)) {
            winner = 2
        }
        //col2
        if (player1.contains(2) && player1.contains(5) && player1.contains(8)) {
            winner = 1
        }
        if (player2.contains(2) && player2.contains(5) && player2.contains(8)) {
            winner = 2
        }
        //col3
        if (player1.contains(3) && player1.contains(6) && player1.contains(9)) {
            winner = 1
        }
        if (player2.contains(3) && player2.contains(6) && player2.contains(9)) {
            winner = 2
        }

        // "\"
        if (player1.contains(1) && player1.contains(5) && player1.contains(9)) {
            winner = 1
        }
        if (player2.contains(1) && player2.contains(5) && player2.contains(9)) {
            winner = 2
        }
        // "/"
        if (player1.contains(3) && player1.contains(5) && player1.contains(7)) {
            winner = 1
        }
        if (player2.contains(3) && player2.contains(5) && player2.contains(7)) {
            winner = 2
        }


        if (winner != -1) {
            if (winner == 1) {
                Log.i(TAG, "Player won the game")
                txtView.text = getString(R.string.winner_text)

                winInARow++
                gamesWon++
                Preference.gamesWon = gamesWon
                textViewGamesWon.text = getString(R.string.games_win_text, gamesWon)
                //reviewAchievements()
                //submitRankingScore(gamesWon)
            } else {
                Log.i(TAG, "Computer won the game.")
                txtView.text = getString(R.string.loser_text)
                winInARow = 0
            }
            if (winner == 1 || winner == 2) {
                disabledTable()
            }
        }
        btnUsed = 0
        for (cellID in 0..9) {
            if (player1.contains(cellID) || player2.contains(cellID)) {
                btnUsed++
            }
        }
        if (btnUsed >= 8 && winner == -1) {
            Log.i(TAG, "No one won the Game.")
            txtView.text = getString(R.string.tie_text)
            disabledTable()
            winInARow = 0
        }
        textViewGamesWonInARow.text = getString(R.string.games_win_in_a_row_text, winInARow)
    }

    private fun disabledTable() {
        for (i in 0..2) {
            val row: TableRow = tableLayout.getChildAt(i) as TableRow
            for (j in 0..2) {
                val button = row.getChildAt(j) as Button // get child index on particular row
                button.isEnabled = false
                button.isClickable = false
            }
        }
    }

    private fun clearBoard() {
        for (i in 0..2) {
            val row: TableRow = tableLayout.getChildAt(i) as TableRow
            for (j in 0..2) {
                val button = row.getChildAt(j) as Button // get child index on particular row
                button.text = ""
                button.setBackgroundColor(Color.WHITE)
                button.isEnabled = true
                button.isClickable = true
            }
        }
        player1.clear()
        player2.clear()
        txtView.text = ""
    }

    //private fun reviewAchievements() {}

    //private fun reachAchievement(achievement: Achievement) { }

    //private fun reveledAchievement(achievementId: String) {}

    private fun showAchievementsList() {}

    private fun submitRankingScore(score: Int) {}

    private fun showLeadersBoards() {}
}