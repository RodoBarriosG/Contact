package com.example.contact.repository

import com.example.contact.database.Contact

interface ContactRepository {

    fun getAll() : List<Contact>
}