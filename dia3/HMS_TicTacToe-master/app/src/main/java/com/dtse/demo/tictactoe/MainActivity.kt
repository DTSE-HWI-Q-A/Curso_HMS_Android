package com.dtse.demo.tictactoe

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.findNavController
import kotlinx.android.synthetic.main.content_main.*

class MainActivity : AppCompatActivity(), HmsAuth {

    companion object {
        private const val TAG = "HMS_MainActivity"
        private const val HMS_REQUEST_CODE = 8888
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(findViewById(R.id.toolbar))

        initializeGame()

        authSilentSignIn()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == HMS_REQUEST_CODE) {

        } else {

        }
    }

    private fun initializeGame() {
        Log.i(TAG, "initialize game")
    }

    private fun authSilentSignIn() {

    }

    override fun signIn() {
        nav_host_fragment.findNavController().navigate(R.id.action_FirstFragment_to_SecondFragment)
    }

    override fun signOut() {
        nav_host_fragment.findNavController().navigate(R.id.action_SecondFragment_to_FirstFragment)
    }
}