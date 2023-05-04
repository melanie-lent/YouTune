package com.example.youtune

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import java.util.*


private const val TAG="LibraryVideo"

class VideoListFragment: Fragment() {
    private val libraryRepository = LibraryRepository.get()

    interface Callbacks {
        fun onVideoSelected(videoId: UUID)
    }
    private var callbacks: Callbacks? = null

    private lateinit var videoRecyclerView: RecyclerView
    private var adapter: VideoAdapter = VideoAdapter(emptyList())

    private val videoListViewModel: VideoListViewModel by lazy {
        ViewModelProviders.of(this).get(VideoListViewModel::class.java)
    }

    private val videoDetailViewModel: VideoDetailViewModel by lazy {
        ViewModelProviders.of(this).get(VideoDetailViewModel::class.java)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        callbacks = context as Callbacks?
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_library_list, container, false)
        videoRecyclerView = view.findViewById(R.id.library_recycler_view) as RecyclerView
        videoRecyclerView.layoutManager = LinearLayoutManager(context)
//        videoRecyclerView.adapter = adapter

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        videoListViewModel.videoListLiveData.observe(
            viewLifecycleOwner,
            Observer { videos ->
                videos?.let {
                    Log.i(TAG, "got videos ${videos.size}")
                    updateUI(videos)
                }
            }
        )
    }

    private fun updateUI(videos: List<LibraryVideo>) {
        adapter = VideoAdapter(videos)
        videoRecyclerView.adapter = adapter
    }

    override fun onDetach() {
        super.onDetach()
        callbacks = null
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.fragment_library_list, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.new_video -> {
                val video = LibraryVideo()
                videoListViewModel.addVideo(video)
                callbacks?.onVideoSelected(video.id)
                true
            }
            else -> return super.onOptionsItemSelected(item)
        }
    }

    private inner class VideoHolder(view: View) : RecyclerView.ViewHolder(view), View.OnClickListener {
        private lateinit var video: LibraryVideo

        private val titleTextView: TextView = itemView.findViewById(R.id.video_title)
        private val posterTextView: TextView = itemView.findViewById(R.id.video_poster)

        init {
            itemView.setOnClickListener(this)
        }

        fun bind(video: LibraryVideo) {
            this.video = video
            //bg2wtUfEtEk

            titleTextView.text = this.video.title
            posterTextView.text = this.video.posterId

//            Toast.makeText(getActivity(), "${this.video.title}, ${this.video.posterId}, ${this.video.videoId}" , Toast.LENGTH_SHORT).show()

        }
        override fun onClick(v: View?) {
            callbacks?.onVideoSelected(video.id)
        }
    }

    private inner class VideoAdapter(var videos: List<LibraryVideo>): RecyclerView.Adapter<VideoHolder>() {
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VideoHolder {
            val view = layoutInflater.inflate(R.layout.list_item_library, parent, false)
            return VideoHolder(view)
        }

        override fun onBindViewHolder(holder: VideoHolder, position: Int) {
            val video = videos[position]
            holder.bind(video)
        }

        override fun getItemCount() = videos.size
    }

    companion object {
        fun newInstance(): VideoListFragment {
            return VideoListFragment()
        }
    }
}