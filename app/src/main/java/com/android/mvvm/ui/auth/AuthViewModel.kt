package com.android.mvvm.ui.auth

import androidx.lifecycle.ViewModel
import com.android.mvvm.data.db.entities.User
import com.android.mvvm.data.repositories.UserRepository

class AuthViewModel(
    private val repository: UserRepository
) : ViewModel() {

    fun getLoggedInUser() = repository.getUser()

    suspend fun userLogin(email: String, password: String) = repository.userLogin(email, password)

    suspend fun saveLoggedInUser(user: User) = repository.saveUser(user)

    suspend fun userSignUp(name: String, email: String, password: String) =
        repository.userSignUp(name, email, password)
}