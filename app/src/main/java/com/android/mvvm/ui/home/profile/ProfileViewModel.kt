package com.android.mvvm.ui.home.profile

import androidx.lifecycle.ViewModel
import com.android.mvvm.data.repositories.UserRepository

class ProfileViewModel(
    private val repository: UserRepository
) : ViewModel() {

    val user = repository.getUser()

}