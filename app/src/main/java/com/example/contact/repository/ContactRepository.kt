package com.example.contact.repository

import com.example.contact.database.entity.Contact
import kotlinx.coroutines.flow.Flow

interface ContactRepository {

    fun getAll() : Flow<List<Contact>>

    suspend fun insert(contact: Contact)
}