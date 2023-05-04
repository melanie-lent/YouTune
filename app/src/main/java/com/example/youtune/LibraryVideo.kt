package com.example.youtune

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*
@Entity
data class LibraryVideo(@PrimaryKey val id: UUID = UUID.randomUUID(),
                 var title: String = "",
                 var posterId: String = "",
                 var videoId: String = "")