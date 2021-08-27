package com.example.contact.usecases

import com.example.contact.database.entity.Contact
import com.example.contact.repository.ContactRepository

class SetContactUseCase(private val contactRepository: ContactRepository) {
    suspend operator fun invoke(contact: Contact) = contactRepository.insert(contact)
}