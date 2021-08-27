package com.example.contact.ui

import android.Manifest
import android.content.pm.PackageManager
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.annotation.RequiresApi
import androidx.core.view.isGone
import androidx.core.view.isVisible
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import com.example.contact.MainApplication
import com.example.contact.adapter.ContactListAdapter
import com.example.contact.database.entity.Contact
import com.example.contact.databinding.ActivityMainBinding
import com.example.contact.viewmodels.ContactEvent
import com.example.contact.viewmodels.ContactViewModel
import com.example.contact.viewmodels.ContactViewModelFactory
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    companion object {
        private const val REQUEST_CONTACT_CODE = 100
        private const val REQUEST_MESSAGE = "Para continuar debe aceptar el permiso"
        private const val ACCEPT = "Aceptar"
    }

    private lateinit var viewModel: ContactViewModel
    private lateinit var binding: ActivityMainBinding
    private lateinit var recycler: RecyclerView
    private lateinit var adapter: ContactListAdapter

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        requestContactPermission()
        configRecycler()

        binding.progress.isVisible = true
        viewModel = createViewModel()
        viewModel.contactEventLiveData.observe(this, { contactEvent ->
            when (contactEvent) {
                is ContactEvent.SetContactList -> {
                    setContactList(contactEvent.contactList)
                    binding.progress.isGone = true
                }
            }
        })

    }

    private fun setContactList(contacts: Flow<List<Contact>>) {
        GlobalScope.launch {
            contacts.collect {
                adapter.submitList(it)
            }
        }
    }

    private fun configRecycler() {
        recycler = binding.recycler
        adapter = ContactListAdapter()
        recycler.adapter = adapter
    }

    private fun createViewModel(): ContactViewModel = ViewModelProvider(
        this,
        ContactViewModelFactory((application as MainApplication).database.contactDao()),
    ).get(ContactViewModel::class.java)

    @RequiresApi(Build.VERSION_CODES.M)
    fun requestContactPermission() {
        when (PackageManager.PERMISSION_GRANTED) {
            checkSelfPermission(Manifest.permission.READ_CONTACTS) -> {
                viewModel.getAll()
            }
            else -> { requestPermissions(arrayOf(Manifest.permission.READ_CONTACTS), REQUEST_CONTACT_CODE) }
        }
    }

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == REQUEST_CONTACT_CODE &&
            grantResults.isNotEmpty() &&
            grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            viewModel.getAll()
        } else {
            Snackbar.make(
                findViewById(android.R.id.content),
                REQUEST_MESSAGE,
                Snackbar.LENGTH_INDEFINITE
            )
                .setAction(ACCEPT) { requestContactPermission() }
                .show()
        }
        return
    }
}