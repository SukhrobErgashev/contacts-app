package dev.sukhrob.contacts.ui.contact

import android.Manifest
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import dev.sukhrob.contacts.R
import dev.sukhrob.contacts.databinding.FragmentContactBinding
import dev.sukhrob.contacts.domain.model.Contact
import dev.sukhrob.contacts.presentation.adapter.ContactAdapter
import dev.sukhrob.contacts.presentation.adapter.ContactCardListener
import dev.sukhrob.contacts.presentation.dialog.CustomDialog
import dev.sukhrob.contacts.presentation.viewmodel.ContactViewModel
import dev.sukhrob.contacts.ui.base.BaseFragment
import dev.sukhrob.contacts.util.CheckPermissions

@AndroidEntryPoint
class ContactFragment : BaseFragment<FragmentContactBinding>(FragmentContactBinding::inflate) {

    private val dialog: CustomDialog by lazy { CustomDialog() }
    private lateinit var adapter: ContactAdapter
    private val viewModel: ContactViewModel by viewModels()
    private lateinit var checkPermissions: CheckPermissions

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        checkPermissions = CheckPermissions(requireContext())

        setAdapterListener()
        setListenerToFab()
        setupRV()
        setObservers()
        setHasOptionsMenu(true)

    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.logout_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.logout -> {
                logoutUser()
            }
        }

        return super.onOptionsItemSelected(item)
    }

    private fun logoutUser() {
        viewModel.logoutUser()
        viewModel.deleteAll()
    }

    private fun setAdapterListener() {
        adapter = ContactAdapter(
            ContactCardListener(
                deleteListener = {
                    viewModel.deleteContact(it)
                },
                updateListener = {
                    viewModel.editContact(it)
                },
                callListener = {
                    onCallClicked(it)
                }
            )
        )
    }

    private fun onCallClicked(item: Contact) {
        val phone = item.phone
        if (checkPermissions.checkPermission(Manifest.permission.CALL_PHONE)) {
            val callIntent = Intent(Intent.ACTION_CALL)
            callIntent.data = Uri.parse("tel:+$phone")
            startActivity(callIntent)
        } else {
            checkPermissions.requestPermission(Manifest.permission.CALL_PHONE)
        }
    }

    private fun setupRV() {
        with(binding) {
            recyclerView.adapter = this@ContactFragment.adapter
            recyclerView.layoutManager = LinearLayoutManager(requireContext())
        }
    }

    private fun setListenerToFab() {
        binding.floatingActionButton.setOnClickListener {
            openDialog()
        }
    }

    private fun navigateToLogin() {
        findNavController().navigate(R.id.action_contactFragment_to_loginFragment)
    }

    private fun openDialog() {
        dialog.show(parentFragmentManager, "custom_dialog")
        dialog.isCancelable = false

        dialog.setListener {
            viewModel.addContact(it)
        }
    }

    private fun setObservers() {
        viewModel.contactList.observe(viewLifecycleOwner, contactListObserver)
        viewModel.logoutUserLiveData.observe(viewLifecycleOwner, logoutUserObserver)
    }

    private val contactListObserver = Observer<List<Contact>> {
        adapter.submitContactList(it)
    }

    private val logoutUserObserver = Observer<Boolean> {
        navigateToLogin()
    }
}