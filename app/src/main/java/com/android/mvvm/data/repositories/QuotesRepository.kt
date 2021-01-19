package com.android.mvvm.data.repositories

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.android.mvvm.data.db.AppDatabase
import com.android.mvvm.data.db.entities.Quote
import com.android.mvvm.data.network.MyApi
import com.android.mvvm.data.network.SafeApiRequest
import com.android.mvvm.util.Coroutines
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class QuotesRepository(
    private val api : MyApi,
    private val db : AppDatabase
) : SafeApiRequest() {

    private val quotes = MutableLiveData<List<Quote>>()
    init {
        quotes.observeForever{
            saveQuotes(it)
        }
    }

    suspend fun getQuotes() : LiveData<List<Quote>>{
        return withContext(Dispatchers.IO){
            fetchQuotes()
            db.getQuoteDao().getQuotes()
        }
    }

    private suspend fun fetchQuotes(){
        if(isFetchNeeded()){
            val response = apiRequest { api.getQuotes() }
            quotes.postValue(response.quotes)
        }
    }

    private fun isFetchNeeded(): Boolean {
        return true
    }

    private fun saveQuotes(it: List<Quote>) {
        Coroutines.io {
            db.getQuoteDao().saveAllQuotes(it)
        }
    }
}