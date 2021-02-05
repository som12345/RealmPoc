package com.example.realmpoc.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.realmpoc.manager.RealmManager
import com.example.realmpoc.model.RegisterModel
import com.google.gson.Gson
import io.realm.RealmResults
import java.lang.Exception

class RealmViewModel : ViewModel() {

     val registerStatus = MutableLiveData<Int>()
     val signInUser = MutableLiveData<Int>()
     val deleteUser = MutableLiveData<Boolean>()
     val updateUser = MutableLiveData<Boolean>()
    /**
     * Method to register user
     */
    fun createUser(name: String, email: String, password: String) {
        RealmManager.openLocalInstance()
        RealmManager.localInstance.executeTransactionAsync({
            val registerModel = it.createObject(RegisterModel::class.java)
            registerModel.name = name
            registerModel.email = email
            registerModel.password = password
            registerModel.status = 1
        }, {
            registerStatus.value = 1
        }, {
            registerStatus.value = 0
        })
    }

    /**
     * Method to logged in user
     */
    fun retriveUser(email: String, password: String) {
        RealmManager.openLocalInstance()
        val students = RealmManager.localInstance.where(
            RegisterModel::class.java
        ).findAll()
        val listResult = Gson().fromJson(
            students.asJSON(),
            Array<RegisterModel>::class.java
        ).toMutableList()
        listResult.forEach {
            when {
                it.email == email && it.password == password -> {
                    signInUser.value = 1
                }
                else -> {
                    signInUser.value = 0
                }
            }
        }
    }

    /**
     * Method to delete user
     */
    fun deleteUser(email: String) {
        RealmManager.openLocalInstance()
        val results: RealmResults<RegisterModel> =
            RealmManager.localInstance.where(RegisterModel::class.java).equalTo("email", email).findAll()
        RealmManager.localInstance.beginTransaction()
        deleteUser.value = results.deleteAllFromRealm()
        RealmManager.localInstance.commitTransaction()
        RealmManager.localInstance.close()
    }
    /**
     * Method to update user
     */
    fun updateUser(email: String) {
        try {
            RealmManager.openLocalInstance()
            val toEdit: RegisterModel? = RealmManager.localInstance.where(RegisterModel::class.java)
                .equalTo("email", email).findFirst()
            RealmManager.localInstance.beginTransaction()
            toEdit?.name = "ANDROID"
            toEdit?.password = "ADMIN"
            RealmManager.localInstance.commitTransaction()
            RealmManager.localInstance.close()
            updateUser.value = true
        }catch (e:Exception) {
            updateUser.value = false
        }

    }
}