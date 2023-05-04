package com.example.youtune.database

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.youtune.LibraryVideo
import java.util.*

@Dao
interface VideoDao {
    @Query("SELECT * FROM libraryvideo")
    fun getVideos(): LiveData<List<LibraryVideo>>

    @Query("SELECT * FROM libraryvideo WHERE id=(:id)")
    fun getVideo(id: UUID): LiveData<LibraryVideo?>

    @Update
    fun updateVideo(libraryvideo: LibraryVideo)

    @Insert
    fun addVideo(libraryvideo: LibraryVideo)

    @Delete
    fun deleteVideo(libraryvideo: LibraryVideo)

}