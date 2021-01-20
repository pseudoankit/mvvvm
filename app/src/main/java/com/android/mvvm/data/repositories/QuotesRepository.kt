package com.android.mvvm.data.repositories

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.android.mvvm.data.db.AppDatabase
import com.android.mvvm.data.db.entities.Quote
import com.android.mvvm.data.db.preferences.PreferenceProvider
import com.android.mvvm.data.network.MyApi
import com.android.mvvm.data.network.SafeApiRequest
import com.android.mvvm.util.Coroutines
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.time.LocalDateTime
import java.time.temporal.ChronoUnit

class QuotesRepository(
    private val api : MyApi,
    private val db : AppDatabase,
    private val prefs: PreferenceProvider
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
        val lastSavedAt = prefs.getLastSavedAt()
        if(lastSavedAt == null || isFetchNeeded(LocalDateTime.parse(lastSavedAt))){
            val response = apiRequest { api.getQuotes() }
            quotes.postValue(response.quotes)
        }
    }

    private fun isFetchNeeded(lastSavedAt: LocalDateTime): Boolean {
        return ChronoUnit.HOURS.between(lastSavedAt,LocalDateTime.now())>6
    }

    private fun saveQuotes(it: List<Quote>) {
        Coroutines.io {
            prefs.saveLastSavedAt(LocalDateTime.now().toString())
            db.getQuoteDao().saveAllQuotes(it)
        }
    }
}