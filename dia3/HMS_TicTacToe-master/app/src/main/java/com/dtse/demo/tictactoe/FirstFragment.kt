package com.dtse.demo.tictactoe

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.fragment_first.*

class FirstFragment : Fragment() {

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
        return inflater.inflate(R.layout.fragment_first, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        buttonHuaweiLogIn.setOnClickListener {
            hmsAuth.signIn()
        }
    }
}