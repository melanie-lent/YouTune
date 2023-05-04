package com.example.youtune

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.google.android.youtube.player.YouTubeInitializationResult
import com.google.android.youtube.player.YouTubePlayer
import com.google.android.youtube.player.YouTubePlayerView
import com.google.android.youtube.player.YouTubePlayerSupportFragmentX
import java.util.*

private const val ARG_VIDEO_ID = "video_id"
private const val TAG = "VideoFragment"

class VideoFragment: Fragment() {
//    val api_key =  "AIzaSyBE8MO2PrtPjPBrquPOXem0LABnfROCcfc"

    private lateinit var video: LibraryVideo
    private lateinit var titleField: EditText
    private lateinit var posterField: EditText
    private lateinit var deleteButton: Button
    private lateinit var editIdField: EditText

    private val videoDetailViewModel: VideoDetailViewModel by lazy {
        ViewModelProviders.of(this).get(VideoDetailViewModel::class.java)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        video = LibraryVideo()
        val videoId: UUID = arguments?.getSerializable(ARG_VIDEO_ID) as UUID
//        Log.d(TAG, "args bundle video ID: $videoId")
        videoDetailViewModel.loadVideo(videoId)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_library, container, false)
        titleField = view.findViewById(R.id.video_title_edit) as EditText
        posterField = view.findViewById(R.id.video_poster_edit) as EditText

        deleteButton = view.findViewById(R.id.remove_video_button) as Button

        editIdField = view.findViewById(R.id.video_id_edit) as EditText

        deleteButton.setOnClickListener {
            videoDetailViewModel.removeVideo(video)
        }

        val youtubePlayerSupportFragment = YouTubePlayerSupportFragmentX.newInstance() //notice this is my custom class, it ends with an X
        parentFragmentManager.beginTransaction()
            .add(R.id.main_player, youtubePlayerSupportFragment).commit()

        youtubePlayerSupportFragment.initialize(
            resources.getString(R.string.api_key), //IF YOU HAVE NO API KEY IT WONT WORK. But that's actually explained in the docs. So you can google it easily if you don't have one
            object : YouTubePlayer.OnInitializedListener {
                override fun onInitializationSuccess(
                    p0: YouTubePlayer.Provider?,
                    p1: YouTubePlayer?,
                    p2: Boolean
                ) {
                    p1?.loadVideo(video.videoId) // string has to be https://www.youtube.com/watch?v=----------->9ET6R_MR1Ag<---------
                }

                override fun onInitializationFailure(
                    p0: YouTubePlayer.Provider?,
                    p1: YouTubeInitializationResult?
                ) {
//                    createToast(
//                        applicationContext,
//                        "ERROR INITIATING YOUTUBE"
//                    )
                }
            })

        // return view
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        videoDetailViewModel.videoLiveData.observe(
            viewLifecycleOwner,
            Observer {
                video ->
                video?.let {
                    this.video = video
                    updateUI()
                    Log.d(TAG, this.video.title + " " + this.video.videoId + " " + this.video.posterId)
                }

            }
        )

//        val titleWatcher = object: TextWatcher {
//            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
//            }
//
//            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
//                video.title = p0.toString()
//                Log.d(TAG, "hee hopoooooooooooooooooooooooooo")
//            }
//
//            override fun afterTextChanged(p0: Editable?) {
//            }
//        }
//        val posterWatcher = object: TextWatcher {
//            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
//            }
//
//            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
//                video.posterId = p0.toString()
//                Log.d(TAG, "hee hopoooooooooooooooooooooooooo")
//                //bg2wtUfEtEk
//            }
//
//            override fun afterTextChanged(p0: Editable?) {
//            }
//        }
//
//        val idWatcher = object: TextWatcher {
//            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
//            }
//
//            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
//                video.videoId = p0.toString()
//                Log.d(TAG, "hee hopoooooooooooooooooooooooooo")
//            }
//
//            override fun afterTextChanged(p0: Editable?) {
//            }
//        }
//
//        titleField.addTextChangedListener(titleWatcher)
//        posterField.addTextChangedListener(posterWatcher)
//        editIdField.addTextChangedListener(idWatcher)
    }

    override fun onStart() {
        super.onStart()
        titleField.setText(video.title)
        posterField.setText(video.posterId)
        editIdField.setText(video.videoId)
    }

    override fun onStop() {
        super.onStop()
        video.videoId = editIdField.text.toString()
        video.title = titleField.text.toString()
        video.posterId = posterField.text.toString()

        videoDetailViewModel.saveVideo(video)
    }

    private fun updateUI() {
        titleField.setText(video.title)
        posterField.setText(video.posterId)
        editIdField.setText(video.videoId)
    }

    companion object {
        fun newInstance(videoId: UUID): VideoFragment {
            val args = Bundle().apply {
                putSerializable(ARG_VIDEO_ID, videoId)
            }
            return VideoFragment().apply {
                arguments = args
            }
        }
    }
}