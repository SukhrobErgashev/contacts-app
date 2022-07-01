package dev.sukhrob.contacts.ui.splash

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import dagger.hilt.android.AndroidEntryPoint
import dev.sukhrob.contacts.R
import dev.sukhrob.contacts.databinding.FragmentSplashBinding
import dev.sukhrob.contacts.presentation.viewmodel.SplashViewModel
import dev.sukhrob.contacts.ui.base.BaseFragment

@AndroidEntryPoint
class SplashFragment : BaseFragment<FragmentSplashBinding>(FragmentSplashBinding::inflate) {

    private val viewModel: SplashViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setObservers()

    }

    private fun gotoContact() {
        findNavController()
            .navigate(R.id.action_splashFragment_to_contactFragment)
    }

    private fun gotoLogin() {
        findNavController()
            .navigate(R.id.action_splashFragment_to_loginFragment)
    }

    private fun setObservers() {
        viewModel.timer.observe(viewLifecycleOwner, timerObserver)
    }

    private val timerObserver = Observer<Boolean> {
        if (it) {
            gotoContact()
        } else {
            gotoLogin()
        }
    }

}