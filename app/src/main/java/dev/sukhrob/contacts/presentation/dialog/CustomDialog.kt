package dev.sukhrob.contacts.presentation.dialog

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import dev.sukhrob.contacts.R
import dev.sukhrob.contacts.databinding.DialogCustomBinding
import dev.sukhrob.contacts.domain.model.Contact

class CustomDialog : DialogFragment() {

    private var _binding: DialogCustomBinding? = null
    private val binding get() = _binding!!

    private var listener: ((Contact) -> Unit)? = null

    fun setListener(_listener: (Contact) -> Unit) {
        listener = _listener
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        dialog!!.window?.setBackgroundDrawableResource(R.drawable.bg_container_out)
        _binding = DialogCustomBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.buttonSave.setOnClickListener {
            listener?.invoke(
                Contact(
                    firstName = binding.etFirstnameDialog.text.toString(),
                    lastName = binding.etLastnameDialog.text.toString(),
                    phone = binding.etPhoneDialog.text.toString().toLong()
                )
            )
            clearData()
            dismiss()
        }

        binding.buttonCancel.setOnClickListener {
            dismiss()
        }

    }

    /**
     * Clear dialog data after pressed save button
     */
    private fun clearData() {
        with(binding) {
            etFirstnameDialog.setText("")
            etLastnameDialog.setText("")
            etPhoneDialog.setText("")
        }
    }

    // Resize Dialog Size
    override fun onStart() {
        super.onStart()
        val width = (resources.displayMetrics.widthPixels * 0.90).toInt()
        //val height = (resources.displayMetrics.heightPixels * 0.45).toInt()
        dialog!!.window?.setLayout(width, ViewGroup.LayoutParams.WRAP_CONTENT)
    }


}