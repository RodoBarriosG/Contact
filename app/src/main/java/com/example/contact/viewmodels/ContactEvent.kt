package com.example.contact.viewmodels

import com.example.contact.database.entity.Contact
import kotlinx.coroutines.flow.Flow

sealed class ContactEvent {

    data class SetContactList(val contactList: Flow<List<Contact>>) : ContactEvent()
}
