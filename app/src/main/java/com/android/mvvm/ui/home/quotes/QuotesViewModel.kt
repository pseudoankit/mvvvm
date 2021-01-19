package com.android.mvvm.ui.home.quotes

import androidx.lifecycle.ViewModel
import com.android.mvvm.data.repositories.QuotesRepository
import com.android.mvvm.util.lazyDeferred

class QuotesViewModel(
    repository: QuotesRepository
) : ViewModel() {

    val quotes by lazyDeferred {
        repository.getQuotes()
    }
}