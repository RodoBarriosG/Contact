package com.example.contact.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.contact.database.entity.Contact
import com.example.contact.usecases.GetAllContactUseCase
import com.example.contact.usecases.SetContactUseCase
import kotlinx.coroutines.launch

class ContactViewModel(
    private val getAllContactUseCase: GetAllContactUseCase,
    private val setContactUseCase: SetContactUseCase
): ViewModel() {

    private val contactEventMutableLiveData = MutableLiveData<ContactEvent>()
    val contactEventLiveData: LiveData<ContactEvent> get() = contactEventMutableLiveData

    fun getAll() {
        contactEventMutableLiveData.value = ContactEvent.SetContactList(getAllContactUseCase.invoke())
    }

    fun insert(contact: Contact) {
        viewModelScope.launch {
            setContactUseCase.invoke(contact)
        }
    }
}