package dev.sukhrob.contacts.ui.auth

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import dagger.hilt.android.AndroidEntryPoint
import dev.sukhrob.contacts.data.model.request.auth.LoginRequest
import dev.sukhrob.contacts.databinding.FragmentLoginBinding
import dev.sukhrob.contacts.presentation.viewmodel.AuthViewModel
import dev.sukhrob.contacts.ui.base.BaseFragment
import dev.sukhrob.contacts.util.snackBar

@AndroidEntryPoint
class LoginFragment : BaseFragment<FragmentLoginBinding>(FragmentLoginBinding::inflate) {

    private val viewModel: AuthViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupListeners()
        setupObservers()

        phoneFocusListener()
        passwordFocusListener()

    }

    private fun phoneFocusListener() {
        binding.etPhoneRegister.setOnFocusChangeListener { _, hasFocus ->
            if (!hasFocus) {
                binding.loginPhoneLayout.helperText = validPhone()
            }
        }
    }

    private fun passwordFocusListener() {
        binding.etPasswordLogin.setOnFocusChangeListener { _, hasFocus ->
            if (!hasFocus) {
                binding.loginPasswordLayout.helperText = validPassword()
            }
        }
    }

    private fun validPhone(): String? {
        val phoneText = binding.etPhoneRegister.text.toString()
        if (phoneText.length < 9) {
            return "Number is not full!"
        }
        return null
    }

    private fun validPassword(): String? {
        val passwordText = binding.etPasswordLogin.text.toString()
        if (passwordText.length < 6) {
            return "Minimum 6 character password"
        }
        return null
    }

    private fun setupListeners() {
        binding.gotoRegister.setOnClickListener {
            gotoRegister()
        }
        binding.btnLogin.setOnClickListener {
            loginUser()
        }
    }

    private fun loginUser() {
        val phone = binding.etPhoneRegister.text.toString()
        val password = binding.etPasswordLogin.text.toString()

        val loginRequest = LoginRequest(
            phone, password
        )

        viewModel.loginUser(loginRequest)
    }

    private fun gotoRegister() {
        findNavController()
            .navigate(LoginFragmentDirections.actionLoginFragmentToRegisterFragment())
    }

    private fun gotoContact() {
        findNavController()
            .navigate(LoginFragmentDirections.actionLoginFragmentToContactFragment())
    }

    private fun setupObservers() {
        with(viewModel) {
            loading.observe(viewLifecycleOwner, loadingObserver)
            gotoContact.observe(viewLifecycleOwner, gotoContactObserver)
            errorMessage.observe(viewLifecycleOwner, errorMessageObserver)
        }
    }

    private val gotoContactObserver = Observer<Boolean> {
        if (it) {
            gotoContact()
        }
    }

    private val errorMessageObserver = Observer<String> {
        binding.btnLogin.snackBar(it) { loginUser() }
    }

    private val loadingObserver = Observer<Boolean> {
        if (it) {
            showProgressBar()
        } else {
            hideProgressBar()
        }
    }

    private fun showProgressBar() {
        binding.progressBar.visibility = View.VISIBLE
    }

    private fun hideProgressBar() {
        binding.progressBar.visibility = View.INVISIBLE
    }

}