package com.android.mvvm.ui.home.profile

import android.os.Bundle
import androidx.lifecycle.Observer
import com.android.mvvm.R
import com.android.mvvm.databinding.ProfileFragmentBinding
import com.android.mvvm.ui.BaseFragment
import com.android.mvvm.util.Coroutines
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.kodein
import org.kodein.di.generic.instance

class ProfileFragment : BaseFragment<ProfileFragmentBinding, ProfileViewModel>(),KodeinAware {

    override val kodein by kodein()
    private val factory : ProfileViewModelFactory by instance()
    override fun getFragmentView() = R.layout.profile_fragment

    override fun getViewModel() = ProfileViewModel::class.java

    override fun getVMFactory() = factory

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        Coroutines.main {
            viewModel.user.await().observe(viewLifecycleOwner, Observer {
                binding.tvEmail.text=it.email
                binding.tvName.text=it.name
            })
        }
    }

}