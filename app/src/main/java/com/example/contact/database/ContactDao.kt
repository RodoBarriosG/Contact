package com.example.contact.database

import androidx.room.Dao
import androidx.room.Query

@Dao
interface ContactDao {

    @Query("SELECT * FROM contact ORDER BY name ASC")
    fun getAll() : List<Contact>
}