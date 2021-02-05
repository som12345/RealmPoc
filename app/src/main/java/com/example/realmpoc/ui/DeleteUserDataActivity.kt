package com.example.realmpoc.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.Observer
import com.example.realmpoc.R
import com.example.realmpoc.viewmodel.RealmViewModel
import kotlinx.android.synthetic.main.activity_delete_user_data.*

class DeleteUserDataActivity : AppCompatActivity() {
    private lateinit var realmViewModel: RealmViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_delete_user_data)
        realmViewModel = RealmViewModel()
        observeLiveDeleteData()
        btn_delete.setOnClickListener {
            realmViewModel.deleteUser(edittextemail.text.toString())
        }
    }

    private fun observeLiveDeleteData() {
        realmViewModel.deleteUser.observe(this, Observer {
            when (it) {
                true -> {
                    Toast.makeText(
                        this,
                        "Deleted!", Toast.LENGTH_SHORT)
                        .show()
                    var response=""
                    realmViewModel.showAllUser().forEach {
                        response = response + "Name: ${it.name}, Email: ${it.email}" +"\n"
                    }
                    tv_updated.text = response
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
}