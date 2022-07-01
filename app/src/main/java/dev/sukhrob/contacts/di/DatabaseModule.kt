package dev.sukhrob.contacts.di

import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import dev.sukhrob.contacts.data.source.local.datastore.UserPreferences
import dev.sukhrob.contacts.data.source.local.db.ContactDao
import dev.sukhrob.contacts.data.source.local.db.ContactDatabase
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DatabaseModule {

    @[Provides Singleton]
    fun provideDatabase(@ApplicationContext context: Context): ContactDatabase =
        Room.databaseBuilder(
            context,
            ContactDatabase::class.java,
            "contact_db"
        ).build()

    @Provides
    @Singleton
    fun provideDao(db: ContactDatabase): ContactDao = db.contactDao()

    @Provides
    @Singleton
    fun provideUserPref(@ApplicationContext context: Context): UserPreferences =
        UserPreferences(context)

}