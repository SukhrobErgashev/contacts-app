package dev.sukhrob.contacts.ui.auth

import android.os.Bundle
import android.view.View
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import dagger.hilt.android.AndroidEntryPoint
import dev.sukhrob.contacts.data.model.request.auth.RegisterRequest
import dev.sukhrob.contacts.databinding.FragmentRegisterBinding
import dev.sukhrob.contacts.presentation.viewmodel.AuthViewModel
import dev.sukhrob.contacts.ui.base.BaseFragment
import dev.sukhrob.contacts.util.enable
import dev.sukhrob.contacts.util.snackBar

@AndroidEntryPoint
class RegisterFragment : BaseFragment<FragmentRegisterBinding>(FragmentRegisterBinding::inflate) {

    private val viewModel: AuthViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupObservers()
        setButtonEnable()
        setButtonDisable()

        binding.btnRegister.setOnClickListener {
            registerUser()
        }

    }

    private fun registerUser() {
        val user = RegisterRequest(
            binding.etFirstnameRegister.text.toString(),
            binding.etLastnameRegister.text.toString(),
            binding.etPhoneRegister.text.toString(),
            binding.etPasswordRegister.text.toString()
        )

        viewModel.registerUser(user)
    }

    private fun showProgressBar() {
        binding.progressBarRegister.visibility = View.VISIBLE
    }

    private fun hideProgressBar() {
        binding.progressBarRegister.visibility = View.INVISIBLE
    }

    private fun navigateToVerify() {
        findNavController().navigate(
            RegisterFragmentDirections.actionRegisterFragmentToVerifyFragment(
                binding.etPhoneRegister.text.toString()
            )
        )
    }

    private fun setButtonEnable() {
        binding.etFirstnameRegister.addTextChangedListener { setEE() }
        binding.etLastnameRegister.addTextChangedListener { setEE() }
        binding.etPhoneRegister.addTextChangedListener { setEE() }
        binding.etPasswordRegister.addTextChangedListener { setEE() }
    }

    private fun setEE() {
        binding.btnRegister.enable(checkFields())
    }

    private fun checkFields(): Boolean {
        val firstname = binding.etFirstnameRegister.text.toString()
        val lastname = binding.etLastnameRegister.text.toString()
        val phone = binding.etPhoneRegister.text.toString()
        val password = binding.etPasswordRegister.text.toString()

        return firstname.isNotEmpty()
                && lastname.isNotEmpty()
                && phone.isNotEmpty()
                && password.isNotEmpty()
    }

    private fun setButtonDisable() {
        binding.btnRegister.enable(false)
    }

    private fun setupObservers() {
        viewModel.loading.observe(viewLifecycleOwner, loadingObserver)
        viewModel.gotoVerify.observe(viewLifecycleOwner, gotoVerifyObserver)
        viewModel.errorMessage.observe(viewLifecycleOwner, errorMessageObserver)
    }

    private val loadingObserver = Observer<Boolean> {
        if (it) {
            showProgressBar()
        } else {
            hideProgressBar()
        }
    }

    private val gotoVerifyObserver = Observer<Boolean> {
        if (it) {
            hideProgressBar()
            navigateToVerify()
        }
    }

    private val errorMessageObserver = Observer<String> {
        binding.progressBarRegister.snackBar(it) { registerUser() }
    }
}