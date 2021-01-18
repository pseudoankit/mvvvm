package com.android.mvvm.ui.auth

import androidx.lifecycle.LiveData
import com.android.mvvm.data.db.entities.User

interface AuthListener {
    fun onStarted()
    fun onSuccess(user : User)
    fun onFailure(message : String)
}