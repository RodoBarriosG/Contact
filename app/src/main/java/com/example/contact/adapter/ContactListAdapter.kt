package com.example.contact.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.contact.database.Contact
import com.example.contact.databinding.ContactsListItemBinding

class ContactListAdapter(
    private val onItemClick: (Contact) -> Unit
) : ListAdapter<Contact, ContactListAdapter.ContactListViewHolder>(DiffCallback) {

    class ContactListViewHolder(private var binding: ContactsListItemBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(contact: Contact) {
            binding.textName.text = contact.name
            binding.textPhoneOrEmail.text = contact.data
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ContactListViewHolder {
        val viewHolder = ContactListViewHolder(
            ContactsListItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
        viewHolder.itemView.setOnClickListener {
            val position = viewHolder.adapterPosition
            onItemClick(getItem(position))
        }
        return viewHolder
    }

    override fun onBindViewHolder(holder: ContactListViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    companion object {
        private val DiffCallback = object : DiffUtil.ItemCallback<Contact>() {
            override fun areItemsTheSame(oldItem: Contact, newItem: Contact): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: Contact, newItem: Contact): Boolean {
                return oldItem == newItem
            }
        }
    }
}