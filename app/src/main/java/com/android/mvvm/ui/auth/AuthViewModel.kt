package com.android.mvvm.ui.auth

import android.content.Intent
import android.view.View
import androidx.lifecycle.ViewModel
import com.android.mvvm.data.repositories.UserRepository
import com.android.mvvm.util.ApiException
import com.android.mvvm.util.Coroutines
import com.android.mvvm.util.NoInternetException

class AuthViewModel(
    private val repository: UserRepository
) : ViewModel() {

    var name: String? = null
    var email: String? = null
    var password: String? = null
    var confirmPassword: String? = null
    var authListener: AuthListener? = null

    fun getUsers() = repository.getUser()

    fun onLoginButtonClicked(view: View) {
        authListener?.onStarted()
        if (email.isNullOrEmpty() || password.isNullOrEmpty()) {
            authListener?.onFailure("Invalid data")
            return
        }

        Coroutines.main {
            try {
                val authResponse = repository.userLogin(email!!, password!!)
                authResponse.user?.let {
                    authListener?.onSuccess(it)
                    repository.saveUser(it)
                    return@main
                }
                authListener?.onFailure(authResponse.message!!)
            } catch (e: ApiException) {
                authListener?.onFailure(e.message!!)
            } catch (e: NoInternetException) {
                authListener?.onFailure(e.message!!)
            }

        }
    }

    fun onSignUp(view: View) {
        Intent(view.context, SignUpActivity::class.java).also {
            view.context.startActivity(it)
        }
    }

    fun onLogin(view: View) {
        Intent(view.context, LoginActivity::class.java).also {
            view.context.startActivity(it)
        }
    }

    fun onSignUpButtonClicked(view: View) {
        authListener?.onStarted()

        if (name.isNullOrEmpty()) {
            authListener?.onFailure("Name is Required")
            return
        }
        if (email.isNullOrEmpty()) {
            authListener?.onFailure("Email is Required")
            return
        }
        if (password.isNullOrEmpty()) {
            authListener?.onFailure("Email is Required")
            return
        }
        if (password != confirmPassword) {
            authListener?.onFailure("Password do not match")
            return
        }

        Coroutines.main {
            try {
                val authResponse = repository.userSignUp(name!!, email!!, password!!)
                authResponse.user?.let {
                    authListener?.onSuccess(it)
                    repository.saveUser(it)
                    return@main
                }
                authListener?.onFailure(authResponse.message!!)
            } catch (e: ApiException) {
                authListener?.onFailure(e.message!!)
            } catch (e: NoInternetException) {
                authListener?.onFailure(e.message!!)
            }
        }
    }
}