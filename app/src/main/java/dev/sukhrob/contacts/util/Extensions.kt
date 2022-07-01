package dev.sukhrob.contacts.util

import android.graphics.Color
import android.view.View
import com.google.android.material.snackbar.Snackbar
import dev.sukhrob.contacts.domain.model.Contact
import dev.sukhrob.contacts.presentation.adapter.ContactDataItem

fun List<Contact>.toListOfDataItem(): List<ContactDataItem> {
    val grouping = this.groupBy {
        it.firstName.first()
    }

    val listDataItem = mutableListOf<ContactDataItem>()
    grouping.forEach {mapEntry ->
        listDataItem.add(ContactDataItem.HeaderCardItem(mapEntry.key))
        listDataItem.addAll(
            mapEntry.value.map { contactCard ->
                ContactDataItem.ContactCardItem(contactCard)
            }
        )
    }

    return listDataItem
}

fun View.snackBar(message: String, action: (() -> Unit)? = null) {
    val snackBar = Snackbar.make(this, message, Snackbar.LENGTH_LONG)
    action?.let {
        snackBar.setAction("Retry") {
            it()
        }
    }
    snackBar.setBackgroundTint(Color.parseColor("#E66E7C"))
    snackBar.setActionTextColor(Color.parseColor("#ffffff"))
    snackBar.setTextColor(Color.parseColor("#ffffff"))
    snackBar.show()
}

fun View.enable(enabled: Boolean) {
    isEnabled = enabled
    alpha = if (enabled) 1f else 0.7f
}