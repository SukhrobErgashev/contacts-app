package dev.sukhrob.contacts.ui.auth

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import dagger.hilt.android.AndroidEntryPoint
import dev.sukhrob.contacts.R
import dev.sukhrob.contacts.data.model.request.auth.VerifyRequest
import dev.sukhrob.contacts.databinding.FragmentVerifyBinding
import dev.sukhrob.contacts.presentation.viewmodel.AuthViewModel
import dev.sukhrob.contacts.ui.base.BaseFragment
import dev.sukhrob.contacts.util.snackBar

@AndroidEntryPoint
class VerifyFragment : BaseFragment<FragmentVerifyBinding>(FragmentVerifyBinding::inflate) {

    private val viewModel: AuthViewModel by viewModels()
    private val args: VerifyFragmentArgs by navArgs()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        showPhoneNumber()
        setupObservers()

        binding.btnVerify.setOnClickListener {
            verifyUser()
        }

    }

    private fun showPhoneNumber() {
        binding.phoneNumberVerify.text = "Sms habar +998${args.phone} raqamiga yuborildi."
    }

    private fun verifyUser() {
        val user = VerifyRequest(
            args.phone,
            binding.etVerifyCode.text.toString()
        )

        viewModel.verifyUser(user)
    }

    private fun setupObservers() {
        viewModel.loading.observe(viewLifecycleOwner, loadingObserver)
        viewModel.gotoContact.observe(viewLifecycleOwner, gotoContactObserver)
        viewModel.errorMessage.observe(viewLifecycleOwner, errorMessageObserver)
    }

    private val loadingObserver = Observer<Boolean> {
        if (it) {
            showProgressBar()
        } else {
            hideProgressBar()
        }
    }

    private val gotoContactObserver = Observer<Boolean> {
        if (it) {
            hideProgressBar()
            navigateToContact()
        }
    }

    private val errorMessageObserver = Observer<String> {
        binding.progressBarVerify.snackBar(it) {
            verifyUser()
        }
    }

    private fun navigateToContact() {
        findNavController().navigate(R.id.action_verifyFragment_to_contactFragment)
    }

    private fun showProgressBar() {
        binding.progressBarVerify.visibility = View.VISIBLE
    }

    private fun hideProgressBar() {
        binding.progressBarVerify.visibility = View.INVISIBLE
    }

}