package com.example.youtune

import android.util.Log
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import java.util.*

class VideoDetailViewModel(): ViewModel() {
    private val libraryRepository = LibraryRepository.get()
    private val videoIdLiveData = MutableLiveData<UUID>()

    var videoLiveData: LiveData<LibraryVideo?> =
        Transformations.switchMap(videoIdLiveData) {
                videoId ->
            libraryRepository.getVideo(videoId)
        }
    fun loadVideo(videoId: UUID) {
        Log.d("VideoDetailViewModel", "video loaded")
        videoIdLiveData.value = videoId
    }

    fun saveVideo(video: LibraryVideo) {
        Log.d("VideoDetailViewModel", "video saved " + video.videoId + " " + video.title + " " + video.posterId)
        libraryRepository.updateVideo(video)
    }

    fun addVideo(video: LibraryVideo) {
        Log.d("VideoDetailViewModel", "video added")
        libraryRepository.addVideo(video)
    }
    
    fun removeVideo(video: LibraryVideo) {
        Log.d("VideoDetailViewModel", "video deleted")
        libraryRepository.deleteVideo(video)
    }
}