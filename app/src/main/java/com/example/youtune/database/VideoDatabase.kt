package com.example.youtune.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.youtune.LibraryVideo

@Database(entities = [ LibraryVideo::class ], version=2)
@TypeConverters(VideoTypeConverters::class)
abstract class VideoDatabase : RoomDatabase() {
    abstract fun videoDao(): VideoDao
}