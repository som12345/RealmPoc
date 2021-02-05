package com.example.realmpoc.ui

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
        observeLiveSignInData()
        observeLiveDeleteData()
    }

    private fun observeLiveDeleteData() {
        realmViewModel.deleteUser.observe(this, Observer {
            when (it) {
                true -> {
                    Toast.makeText(
                            this,
                            "Deleted!", Toast.LENGTH_SHORT)
                            .show()
                }
                false -> {
                    Toast.makeText(
                            this,
                            "Not Deleted!", Toast.LENGTH_SHORT)
                            .show()
                }
            }
        })
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
                else -> {
                    Toast.makeText(
                            this,
                            "LoginFailed", Toast.LENGTH_SHORT)
                            .show()
                }
            }
        })
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
                else -> {
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
        btn_signin.setOnClickListener {
            realmViewModel.retriveUser(
                    edittextemail.text.toString(),
                    edittextPwd.text.toString()
            )
        }
        btn_delete.setOnClickListener {
            realmViewModel.deleteUser(edittextemail.text.toString())
        }
        btn_update.setOnClickListener {
            realmViewModel.updateUser(edittextemail.text.toString())
        }
    }

    private fun setUpViewModel() {
        realmViewModel = RealmViewModel()
    }
}