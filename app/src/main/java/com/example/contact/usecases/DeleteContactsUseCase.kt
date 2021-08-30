package com.example.contact.usecases

import com.example.contact.repository.ContactRepository

class DeleteContactsUseCase(private val contactRepository: ContactRepository) {
    suspend operator fun invoke() = contactRepository.delete()
}