package com.example.contact.usecases

import com.example.contact.repository.ContactRepository

class GetAllContactUseCase(private val contactRepository: ContactRepository) {
    operator fun invoke() = contactRepository.getAll()
}