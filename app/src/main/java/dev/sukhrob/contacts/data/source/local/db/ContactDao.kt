package dev.sukhrob.contacts.data.source.local.db

import androidx.lifecycle.LiveData
import androidx.room.*
import dev.sukhrob.contacts.domain.model.Contact
import kotlinx.coroutines.flow.Flow

@Dao
interface ContactDao {

    @Insert
    suspend fun insert(contact: Contact)

    @Insert
    suspend fun insertAll(data: List<Contact>)

    @Delete
    suspend fun delete(contact: Contact)

    @Update
    suspend fun update(contact: Contact)

    @Query("delete from contacts")
    suspend fun deleteAll()

    @Query("select * from contacts")
    fun getContacts(): Flow<List<Contact>>

    @Query("select * from contacts where isBookmarked = 1 order by firstName ASC")
    fun getBookmarks(): Flow<List<Contact>>

}