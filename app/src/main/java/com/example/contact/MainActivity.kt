package com.example.contact

import android.Manifest
import android.annotation.SuppressLint
import android.content.pm.PackageManager
import android.database.Cursor
import android.net.Uri
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.ContactsContract
import android.widget.ListView
import android.widget.SimpleCursorAdapter
import androidx.annotation.RequiresApi
import androidx.loader.app.LoaderManager
import androidx.loader.content.CursorLoader
import androidx.loader.content.Loader

class MainActivity : AppCompatActivity(), LoaderManager.LoaderCallbacks<Cursor> {

    companion object {
        private const val REQUEST_CONTACT_CODE = 100
        private const val REQUEST_MESSAGE = "Para continuar debe aceptar el permiso"
    }

    @SuppressLint("InlinedApi")
    private val FROM_COLUMNS: Array<String> = arrayOf(
        ContactsContract.Contacts.DISPLAY_NAME,
        ContactsContract.CommonDataKinds.Email.DATA
    )

    private val PROJECTION: Array<out String> = arrayOf(
        ContactsContract.Contacts._ID,
        ContactsContract.Contacts.DISPLAY_NAME,
        ContactsContract.CommonDataKinds.Email.DATA,
        ContactsContract.CommonDataKinds.Phone.PHOTO_URI,
        ContactsContract.RawContacts.CONTACT_ID
    )

    private val TO_IDS: IntArray = intArrayOf(
        R.id.text_name,
        R.id.text_phone_or_email
    )

    lateinit var contactsList: ListView
    var contactId: Long = 0
    var contactKey: String? = null
    var contactUri: Uri? = null
    private var cursorAdapter: SimpleCursorAdapter? = null

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        requestContactPermission()

        contactsList = findViewById(R.id.list)
        cursorAdapter = SimpleCursorAdapter(
            this,
            R.layout.contacts_list_item,
            null,
            FROM_COLUMNS, TO_IDS,
            0
        )
        contactsList.adapter = cursorAdapter
    }

    @RequiresApi(Build.VERSION_CODES.M)
    fun requestContactPermission() {
        when (PackageManager.PERMISSION_GRANTED) {
            checkSelfPermission(Manifest.permission.READ_CONTACTS) -> {
                supportLoaderManager.initLoader(0, null, this)
            }
            else -> { requestPermissions(arrayOf(Manifest.permission.READ_CONTACTS), REQUEST_CONTACT_CODE) }
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == REQUEST_CONTACT_CODE &&
            grantResults.isNotEmpty() &&
            grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            supportLoaderManager.initLoader(0, null, this)
        } else {

        }
        return
    }

    override fun onCreateLoader(id: Int, args: Bundle?): Loader<Cursor> {
        return CursorLoader(
            this,
            ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
            PROJECTION,
            null,
            null,
            null
        )
    }

    override fun onLoadFinished(loader: Loader<Cursor>, data: Cursor?) {
        cursorAdapter?.swapCursor(data)
    }

    override fun onLoaderReset(loader: Loader<Cursor>) {
        cursorAdapter?.swapCursor(null)
    }
}