package dev.sukhrob.contacts.data.source.local.datastore

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class UserPreferences(private val context: Context) {

    companion object {
        private val Context.userDataStore: DataStore<Preferences>
                by preferencesDataStore(name = "data_store")

        private val KEY_TOKEN = stringPreferencesKey("key_token")
        private val KEY_PHONE = stringPreferencesKey("key_phone")
    }

    // Get authToken from DataStore
    val authToken: Flow<String?>
        get() = context.userDataStore.data.map {
            it[KEY_TOKEN]
        }

    // Get phone from DataStore
    val phone: Flow<String?>
        get() = context.userDataStore.data.map {
            it[KEY_PHONE]
        }

    // Save token to DataStore
    suspend fun saveAuthToken(authToken: String) {
        context.userDataStore.edit {
            it[KEY_TOKEN] = authToken
        }
    }

    // Save token to DataStore
    suspend fun savePhone(phone: String) {
        context.userDataStore.edit {
            it[KEY_PHONE] = phone
        }
    }

    // Clear token
    suspend fun clear() {
        context.userDataStore.edit {
            it.clear()
        }
    }

}