package com.example.contact.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.contact.database.dao.ContactDao
import com.example.contact.repository.ContactRepositoryImpl
import com.example.contact.usecases.GetAllContactUseCase

class ContactViewModelFactory(private val contactDao: ContactDao): ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return ContactViewModel(
            GetAllContactUseCase(ContactRepositoryImpl(contactDao))
        ) as T
    }
}