package dev.sukhrob.contacts.di

import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dev.sukhrob.contacts.data.repository.AuthRepositoryImpl
import dev.sukhrob.contacts.data.repository.ContactRepositoryImpl
import dev.sukhrob.contacts.domain.repository.AuthRepository
import dev.sukhrob.contacts.domain.repository.ContactRepository
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
interface RepoModule {

    @Binds
    @Singleton
    fun bindAuthRepo(authRepo: AuthRepositoryImpl): AuthRepository

    @Binds
    @Singleton
    fun bindContactRepo(contactRepo: ContactRepositoryImpl): ContactRepository

}