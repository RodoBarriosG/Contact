package com.example.contact.worker

import android.content.Context
import android.database.Cursor
import android.os.Build
import android.provider.ContactsContract
import androidx.annotation.RequiresApi
import com.example.contact.database.entity.Contact

class ContactService(private val context: Context) {

    companion object {
        private val PROJECTION = arrayOf(
            ContactsContract.Contacts._ID,
            ContactsContract.Contacts.DISPLAY_NAME,
            ContactsContract.CommonDataKinds.Email.DATA,
            ContactsContract.CommonDataKinds.Phone.PHOTO_URI,
            ContactsContract.RawContacts.CONTACT_ID
        )

        private val URI_EMAIL = ContactsContract.CommonDataKinds.Email.CONTENT_URI
        private val URI_PHONE = ContactsContract.CommonDataKinds.Phone.CONTENT_URI
        private const val IN_VISIBLE_GROUP = ContactsContract.Contacts.IN_VISIBLE_GROUP + " = 1"
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun data(): List<Cursor?> {
        return listOf(
            context.contentResolver.query(
                URI_EMAIL,
                PROJECTION,
                IN_VISIBLE_GROUP,
                null,
                ContactsContract.Contacts._ID
            ),
            context.contentResolver.query(
                URI_PHONE,
                PROJECTION,
                IN_VISIBLE_GROUP,
                null,
                ContactsContract.Contacts._ID
            )
        )
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun insert(): List<Contact> {
        val cursors = data()
        val contacts = mutableListOf<Contact>()
        for (cursor in cursors) {
            if (cursor?.moveToFirst() == false) {
                break
            }
            cursor?.let { buildContact(it) }
            while (cursor?.moveToNext() == true) {
                contacts.add(buildContact(cursor))
            }
        }
        return contacts
    }

    private fun buildContact(cursor: Cursor): Contact {
        return Contact(
            id = cursor.getInt(cursor.getColumnIndex(ContactsContract.Contacts._ID)),
            name = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME)),
            data = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Email.DATA)),
            photoUri = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.PHOTO_URI)),
            rawId = cursor.getString(cursor.getColumnIndex(ContactsContract.RawContacts.CONTACT_ID))
        )
    }
}