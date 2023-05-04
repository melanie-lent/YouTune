package com.example.youtune
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.youtube.player.YouTubeInitializationResult
import com.google.android.youtube.player.YouTubePlayer
import com.google.android.youtube.player.YouTubePlayerView
import com.google.android.youtube.player.YouTubeBaseActivity
import java.util.*

//class MainActivity : YouTubeBaseActivity() {
//
//    // Change the AppCompactActivity to YouTubeBaseActivity()
//
//    // Add the api key that you had
//    // copied from google API
//    // This is a dummy api key
//
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_main)
//
//        // Get reference to the view of Video player
//        val ytPlayer = findViewById<YouTubePlayerView>(R.id.ytPlayer)
//
//        ytPlayer.initialize(api_key, object : YouTubePlayer.OnInitializedListener{
//            // Implement two methods by clicking on red error bulb
//            // inside onInitializationSuccess method
//            // add the video link or the
//            // playlist link that you want to play
//            // In here we also handle the play and pause functionality
//            override fun onInitializationSuccess(
//                provider: YouTubePlayer.Provider?,
//                player: YouTubePlayer?,
//                p2: Boolean
//            ) {
//                player?.loadVideo("HzeK7g8cD0Y")
//                player?.play()
//            }
//
//            // Inside onInitializationFailure
//            // implement the failure functionality
//            // Here we will show toast
//            override fun onInitializationFailure(
//                p0: YouTubePlayer.Provider?,
//                p1: YouTubeInitializationResult?
//            ) {
//                Toast.makeText(this@MainActivity , "Video player Failed" , Toast.LENGTH_SHORT).show()
//            }
//        })
//    }
//}

class MainActivity : AppCompatActivity(), VideoListFragment.Callbacks {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

            val currentFragment = supportFragmentManager.findFragmentById(R.id.fragment_container)
            if (currentFragment == null) {
                val fragment = VideoListFragment.newInstance()
                supportFragmentManager
                    .beginTransaction()
                    .add(R.id.fragment_container, fragment)
                    .commit()
            }
        }

        override fun onVideoSelected(videoId: UUID) {
            val fragment = VideoFragment.newInstance(videoId)
            supportFragmentManager
                .beginTransaction()
                .replace(R.id.fragment_container, fragment)
                .addToBackStack(null)
                .commit()
        }
    }