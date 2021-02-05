package com.example.realmpoc.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.Observer
import com.example.realmpoc.R
import com.example.realmpoc.viewmodel.RealmViewModel
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.activity_main.edittextPwd
import kotlinx.android.synthetic.main.activity_main.edittextemail

class LoginActivity : AppCompatActivity() {
    private lateinit var realmViewModel: RealmViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        setUpViewModel()
        btn_signin.setOnClickListener {
            realmViewModel.retriveUser(
                edittextemail.text.toString(),
                edittextPwd.text.toString()
            )
        }
        observeLiveSignInData()
    }
    private fun setUpViewModel() {
        realmViewModel = RealmViewModel()
    }
    private fun observeLiveSignInData() {
        realmViewModel.signInUser.observe(this, Observer {
            when (it) {
                1 -> {
                    Toast.makeText(
                        this,
                        "LoginSuccess", Toast.LENGTH_SHORT)
                        .show()
                }
                0 -> {
                    Toast.makeText(
                        this,
                        "LoginFailed", Toast.LENGTH_SHORT)
                        .show()
                }
            }
        })
    }
}