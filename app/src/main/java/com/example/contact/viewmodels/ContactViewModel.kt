package com.example.contact.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.contact.usecases.GetAllContactUseCase

class ContactViewModel(
    private val getAllContactUseCase: GetAllContactUseCase
): ViewModel() {

    private val contactEventMutableLiveData = MutableLiveData<ContactEvent>()
    val contactEventLiveData: LiveData<ContactEvent> get() = contactEventMutableLiveData

    fun getAll() {
        contactEventMutableLiveData.value = ContactEvent.SetContactList(getAllContactUseCase.invoke())
    }
}