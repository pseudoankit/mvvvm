package com.android.mvvm.ui.home.quotes

import androidx.databinding.Bindable
import com.android.mvvm.R
import com.android.mvvm.data.db.entities.Quote
import com.android.mvvm.databinding.ItemQuoteBinding
import com.xwray.groupie.databinding.BindableItem

class QuoteItem(
    private val quote : Quote
) : BindableItem<ItemQuoteBinding>() {
    override fun bind(viewBinding: ItemQuoteBinding, position: Int) {
        viewBinding.setQuote(quote)
    }

    override fun getLayout() = R.layout.item_quote
}