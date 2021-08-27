package com.example.contact.database.entity

import androidx.annotation.NonNull
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Contact(
    @PrimaryKey val id: Int,
    @NonNull @ColumnInfo(name = "name") val name: String,
    @NonNull @ColumnInfo(name = "data") val data: String,
    @ColumnInfo(name = "photo_uri") val photoUri: String?,
    @NonNull @ColumnInfo(name = "raw_id") val rawId: String
)
