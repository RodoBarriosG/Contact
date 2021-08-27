package com.example.contact.repository

import com.example.contact.database.Contact
import com.example.contact.database.ContactDao

class ContactRepositoryImpl(private val contactDao: ContactDao) : ContactRepository {

    override fun getAll(): List<Contact> {
        return contactDao.getAll()
    }
}