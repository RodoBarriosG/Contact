package com.example.contact

import android.app.Application
import androidx.work.Configuration
import com.example.contact.database.ContactDatabase

class MainApplication : Application(), Configuration.Provider {

    val database: ContactDatabase by lazy { ContactDatabase.getDatabase(this) }

    override fun getWorkManagerConfiguration(): Configuration =
        Configuration.Builder()
            .setMinimumLoggingLevel(android.util.Log.DEBUG)
            .build()
}