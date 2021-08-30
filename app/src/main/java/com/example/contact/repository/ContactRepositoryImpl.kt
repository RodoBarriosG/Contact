package com.example.contact.repository

import com.example.contact.database.entity.Contact
import com.example.contact.database.dao.ContactDao
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext

class ContactRepositoryImpl(private val contactDao: ContactDao) : ContactRepository {

    override fun getAll(): Flow<List<Contact>> {
        return contactDao.getAll()
    }

    override suspend fun insert(contact: Contact) {
        withContext(Dispatchers.IO) {
            contactDao.insert(contact)
        }
    }

    override suspend fun delete() {
        withContext(Dispatchers.IO) {
            contactDao.deleteALl()
        }
    }
}