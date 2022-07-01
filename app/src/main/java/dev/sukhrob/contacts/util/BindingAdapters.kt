package dev.sukhrob.contacts.util

import android.widget.TextView
import androidx.databinding.BindingAdapter
import dev.sukhrob.contacts.domain.model.Contact

object BindingAdapters {

    @BindingAdapter("android:setPhoneNumber")
    @JvmStatic
    fun setPhoneNumber(textView: TextView, l: Long) {
        textView.text = "+ $l"
    }

    @BindingAdapter("android:setName")
    @JvmStatic
    fun setName(textView: TextView, contact: Contact) {
        textView.text = "${contact.firstName} ${contact.lastName}"
    }

}