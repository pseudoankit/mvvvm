package com.android.mvvm.ui.home.profile

import androidx.lifecycle.ViewModel
import com.android.mvvm.data.repositories.UserRepository
import com.android.mvvm.util.lazyDeferred

class ProfileViewModel(
    private val repository: UserRepository
) : ViewModel() {

    val user by lazyDeferred {
        repository.getUser()
    }

}