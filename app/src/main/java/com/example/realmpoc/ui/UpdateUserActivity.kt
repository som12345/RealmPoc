package com.example.realmpoc.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.Observer
import com.example.realmpoc.R
import com.example.realmpoc.viewmodel.RealmViewModel
import kotlinx.android.synthetic.main.activity_main.edittextemail
import kotlinx.android.synthetic.main.activity_update_user.*

class UpdateUserActivity : AppCompatActivity() {
    private lateinit var realmViewModel: RealmViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_update_user)
        realmViewModel = RealmViewModel()
        observeLiveUpdateData()
        btn_update.setOnClickListener {
            realmViewModel.updateUser(edittextemail.text.toString())
        }
    }

    private fun observeLiveUpdateData() {
        realmViewModel.updateUser.observe(this, Observer {
            when (it) {
                true -> {
                    var response=""
                    realmViewModel.showAllUser().forEach {
                        response = response + "Name: ${it.name}, Password: ${it.password}" +"\n"
                    }
                    tv_updated.text = response
                    Toast.makeText(
                        this,
                        "Updated!", Toast.LENGTH_SHORT)
                        .show()
                }
                false -> {
                    Toast.makeText(
                        this,
                        "Not Updated!", Toast.LENGTH_SHORT)
                        .show()
                }
            }
        })
    }
}