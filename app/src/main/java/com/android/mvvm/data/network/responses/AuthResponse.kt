package com.android.mvvm.data.network.responses

import com.android.mvvm.data.db.entities.User

data class AuthResponse(
    val isSuccessful : Boolean?,
    val message: String?,
    val user: User?
)