package com.android.mvvm.ui.home.quotes

import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.mvvm.R
import com.android.mvvm.data.db.entities.Quote
import com.android.mvvm.databinding.QuotesFragmentBinding
import com.android.mvvm.ui.BaseFragment
import com.android.mvvm.util.Coroutines
import com.android.mvvm.util.hide
import com.android.mvvm.util.show
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.ViewHolder
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.kodein
import org.kodein.di.generic.instance

class QuotesFragment : BaseFragment<QuotesFragmentBinding, QuotesViewModel>(),KodeinAware {

    override val kodein by kodein()
    private val factory : QuotesViewModelFactory by instance()

    override fun getFragmentView() = R.layout.quotes_fragment

    override fun getViewModel() = QuotesViewModel::class.java

    override fun getVMFactory() = factory

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        bindUI()
    }

    private fun bindUI() = Coroutines.main{
        binding.progressBar.show()
        viewModel.quotes.await().observe(viewLifecycleOwner, Observer {
            binding.progressBar.hide()
            initRecyclerView(it.toQuoteItem())
        })
    }

    private fun initRecyclerView(quoteItem: List<QuoteItem>) {
        val mAdapter = GroupAdapter<ViewHolder>().apply {
            addAll(quoteItem)
        }
        binding.recyclerView.apply {
            layoutManager = LinearLayoutManager(context)
            setHasFixedSize(true)
            adapter = mAdapter
        }
    }

    private fun List<Quote>.toQuoteItem() : List<QuoteItem>{
        return this.map{
            QuoteItem(it)
        }
    }
}