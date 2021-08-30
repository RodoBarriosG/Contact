package com.example.contact.worker

import android.content.Context
import androidx.work.ExistingWorkPolicy
import androidx.work.OneTimeWorkRequest
import androidx.work.WorkManager

class ContactSync(private val context: Context) {

    fun startSyncContacts() {
        val workRequest = OneTimeWorkRequest.Builder(ContactWorker::class.java).build()

        WorkManager.getInstance(context)
            .enqueueUniqueWork(ContactWorker.CONTACT_WORKER_TAG, ExistingWorkPolicy.KEEP, workRequest)
    }

    fun stopSyncContacts() {
        WorkManager.getInstance(context).cancelUniqueWork(ContactWorker.CONTACT_WORKER_TAG)
    }
}