package com.example.youtune

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.room.Room
import com.example.youtune.database.VideoDatabase
import java.lang.IllegalStateException
import java.util.*
import java.util.concurrent.Executors

private const val DATABASE_NAME = "library-video-database"

class LibraryRepository private constructor(context: Context) {
    private val database : VideoDatabase = Room.databaseBuilder(
        context.applicationContext,
        VideoDatabase::class.java,
        DATABASE_NAME
    ).build()

    private val videoDao = database.videoDao()

    private val executor = Executors.newSingleThreadExecutor()

    fun updateVideo(video: LibraryVideo) {
        executor.execute {
            videoDao.updateVideo(video)
        }
    }

    fun addVideo(video: LibraryVideo) {
        executor.execute {
            videoDao.addVideo(video)
        }
    }

    fun deleteVideo(video: LibraryVideo) {
        executor.execute {
            videoDao.deleteVideo(video)
        }
    }

    fun getVideos(): LiveData<List<LibraryVideo>> = videoDao.getVideos()

    fun getVideo(id: UUID): LiveData<LibraryVideo?> = videoDao.getVideo(id)

    companion object {
        private var INSTANCE: LibraryRepository? = null

        fun initialize(context: Context) {
            if (INSTANCE == null) {
                INSTANCE = LibraryRepository(context)
            }
        }

        fun get(): LibraryRepository {
            return INSTANCE ?:
            throw IllegalStateException("LibraryRepository must be initialized")
        }
    }
}