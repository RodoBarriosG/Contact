package com.example.contact

import android.app.Application
import com.example.contact.database.ContactDatabase

class MainApplication: Application() {
    val database: ContactDatabase by lazy { ContactDatabase.getDatabase(this) }
}