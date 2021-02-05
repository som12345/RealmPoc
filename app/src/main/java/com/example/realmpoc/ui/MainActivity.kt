package com.example.realmpoc.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.Observer
import com.example.realmpoc.R
import com.example.realmpoc.viewmodel.RealmViewModel
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private lateinit var realmViewModel: RealmViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setUpViewModel()
        registerUser()
        observeLiveRegisterData()

    }

    private fun observeLiveRegisterData() {
        realmViewModel.registerStatus.observe(this, Observer {
            when (it) {
                1 -> {
                    Toast.makeText(
                            this,
                            "Congrats user registered successfully!", Toast.LENGTH_SHORT)
                            .show()
                }
                0 -> {
                    Toast.makeText(
                            this,
                            "Sorry!,Something went wrong!!!", Toast.LENGTH_SHORT)
                            .show()
                }
            }
        })
    }

    private fun registerUser() {
        btn_save.setOnClickListener {
            realmViewModel.createUser(
                    edittextName.text.toString(),
                    edittextemail.text.toString(),
                    edittextPwd.text.toString()
            )
        }
        btn_delete.setOnClickListener {
            startActivity(Intent(this@MainActivity, DeleteUserDataActivity::class.java))
        }
        btn_update.setOnClickListener {
            startActivity(Intent(this@MainActivity, UpdateUserActivity::class.java))
        }
        btn_signin.setOnClickListener {
            startActivity(Intent(this@MainActivity, LoginActivity::class.java))
        }
    }

    private fun setUpViewModel() {
        realmViewModel = RealmViewModel()
    }
}