package com.android.mvvm.data.repositories

import com.android.mvvm.data.db.AppDatabase
import com.android.mvvm.data.db.entities.User
import com.android.mvvm.data.network.MyApi
import com.android.mvvm.data.network.SafeApiRequest
import com.android.mvvm.data.network.responses.AuthResponse
import retrofit2.Response

class UserRepository(
    private val api: MyApi,
    private val db: AppDatabase
) : SafeApiRequest(){
    suspend fun userLogin(email: String, password: String): AuthResponse {
        return apiRequest{api.userLogin(email,password)}
    }

    suspend fun saveUser(user: User)= db.getUserDao().upsert(user)

    fun getUser() = db.getUserDao().getUser()
}