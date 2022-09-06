package com.example.kotlintest3

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import com.example.kotlintest3.databinding.PhotoCollectionBinding
import com.example.kotlintest3.model.Photo
import com.example.kotlintest3.recyclerview.PhotoGridRecyclerViewAdapter
import com.example.kotlintest3.utils.Constants.TAG

class PhotoCollectionActivity:AppCompatActivity() {
    private lateinit var binding: PhotoCollectionBinding

    //데이터
    var photoList = ArrayList<Photo>()

    //어댑터
    private lateinit var photoGridRecyclerViewAdapter: PhotoGridRecyclerViewAdapter



    override fun onCreate(savedInstanceState: Bundle?) {
        binding = PhotoCollectionBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)


        val bundle = intent.getBundleExtra("array_bundle")

        val searchTerm = intent.getStringExtra("search_term")

        photoList = bundle?.getSerializable("photo_array_list") as ArrayList<Photo>

        Log.d(TAG, "PhotoCollectionActivity - onCreate() called / searchTerm : $searchTerm , photoList.count(): ${photoList.count()}")

        binding.topAppBar.title = searchTerm

        this.photoGridRecyclerViewAdapter = PhotoGridRecyclerViewAdapter()
        this.photoGridRecyclerViewAdapter.submitList(photoList)

        binding.myPhotoRecyclerView.layoutManager = GridLayoutManager(this,2, GridLayoutManager.VERTICAL, false)
        binding.myPhotoRecyclerView.adapter = this.photoGridRecyclerViewAdapter
    }//onCreate

}