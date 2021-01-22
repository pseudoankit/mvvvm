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
import com.android.mvvm.util.snackbar
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.kodein
import org.kodein.di.generic.instance

class QuotesFragment : BaseFragment<QuotesFragmentBinding, QuotesViewModel>(),KodeinAware {

    override val kodein by kodein()
    private val factory : QuotesViewModelFactory by instance()

    override fun getFragmentView() = R.layout.quotes_fragment

    override fun getViewModel() = QuotesViewModel::class.java

    override fun getVMFactory() = factory

    private val mAdapter by lazy {
        QuotesAdapter()
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        bindUI()
    }

    private fun bindUI() = Coroutines.main{
        binding.progressBar.show()
        viewModel.quotes.await().observe(viewLifecycleOwner, Observer {
            binding.progressBar.hide()
            initRecyclerView(it)
        })
    }

    private fun initRecyclerView(list: List<Quote>) {
        mAdapter.addItems(list)
        binding.recyclerView.apply {
            layoutManager = LinearLayoutManager(context)
            setHasFixedSize(true)
            adapter = mAdapter
        }
        mAdapter.listener = {view, item, position ->
            binding.root.snackbar(item.author)
        }
    }
}