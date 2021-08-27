package com.example.contact.viewmodels

import com.example.contact.database.Contact

sealed class ContactEvent {

    data class setContactList(val contactList: List<Contact>) : ContactEvent()
}
