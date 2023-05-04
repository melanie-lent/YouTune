package com.example.youtune

import androidx.lifecycle.ViewModel

class VideoListViewModel: ViewModel() {
        private val libraryRepository = LibraryRepository.get()
        val videoListLiveData = libraryRepository.getVideos()
        val videos = libraryRepository.getVideos()
        fun addVideo(video: LibraryVideo) {
                libraryRepository.addVideo(video)
        }
}