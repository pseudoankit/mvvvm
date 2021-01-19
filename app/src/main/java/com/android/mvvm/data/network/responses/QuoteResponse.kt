package com.android.mvvm.data.network.responses

import com.android.mvvm.data.db.entities.Quote

data class QuoteResponse(
    val isSuccessful: Boolean,
    val quotes: List<Quote>
)
