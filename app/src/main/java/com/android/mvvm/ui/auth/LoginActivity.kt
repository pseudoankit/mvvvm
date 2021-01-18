package com.android.mvvm.ui.auth

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.android.mvvm.R
import com.android.mvvm.data.db.AppDatabase
import com.android.mvvm.data.db.entities.User
import com.android.mvvm.data.network.MyApi
import com.android.mvvm.data.network.NetworkConnectionInterceptor
import com.android.mvvm.data.repositories.UserRepository
import com.android.mvvm.databinding.ActivityLoginBinding
import com.android.mvvm.ui.home.HomeActivity
import com.android.mvvm.util.hide
import com.android.mvvm.util.show
import com.android.mvvm.util.snackbar
import com.android.mvvm.util.toast
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : AppCompatActivity(), AuthListener {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val networkConnectionInterceptor = NetworkConnectionInterceptor(this)
        val api = MyApi(networkConnectionInterceptor)
        val db = AppDatabase(this)
        val repository = UserRepository(api, db)
        val factory = AuthViewModelFactory(repository)

        val binding: ActivityLoginBinding =
            DataBindingUtil.setContentView(this, R.layout.activity_login)
        val viewModel = ViewModelProviders.of(this, factory).get(AuthViewModel::class.java)
//        val viewModel :AuthViewModel = AuthViewModel
        binding.viewModel = viewModel
        viewModel.authListener = this

        viewModel.getUsers().observe(this, Observer { user ->
            if (user != null) {
                Intent(this, HomeActivity::class.java).also {
                    it.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                    startActivity(it)
                }
            }
        })
    }

    override fun onStarted() {
        progress_bar.show()
    }

    override fun onSuccess(user: User) {
        progress_bar.hide()
        root_layout.snackbar("${user.name} is logged in")
    }

    override fun onFailure(message: String) {
        progress_bar.hide()
        root_layout.snackbar(message)
    }
}