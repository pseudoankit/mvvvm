package com.android.mvvm.ui.home.quotes

import com.android.mvvm.R
import com.android.mvvm.data.db.entities.Quote
import com.android.mvvm.databinding.ItemQuoteBinding
import com.android.mvvm.ui.BaseRVAdapter

class QuotesAdapter : BaseRVAdapter<Quote, ItemQuoteBinding>() {
    override fun getLayout() = R.layout.item_quote

    override fun onBindViewHolder(
        holder: Companion.BaseViewHolder<ItemQuoteBinding>,
        position: Int
    ) {

        holder.binding.apply {
            tvQuote.text = list[position].quote
            tvAuthor.text = list[position].author
            root.setOnClickListener{
                listener?.invoke(it,list[position],position)
            }
        }

    }
}