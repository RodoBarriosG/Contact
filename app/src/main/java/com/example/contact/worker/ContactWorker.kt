package com.example.contact.worker

import android.content.Context
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.example.contact.MainApplication
import com.example.contact.repository.ContactRepositoryImpl
import com.example.contact.usecases.SetContactUseCase

class ContactWorker(
    private val context: Context,
    workerParameters: WorkerParameters
) : CoroutineWorker(context, workerParameters) {

    companion object {
        const val CONTACT_WORKER_TAG = "contact_worker"
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override suspend fun doWork(): Result {
        val contacts = ContactService(context).insert()
        val useCase = SetContactUseCase(ContactRepositoryImpl((context as MainApplication).database.contactDao()))
        for (contact in contacts) {
            useCase.invoke(contact)
        }
        return Result.success()
    }
}