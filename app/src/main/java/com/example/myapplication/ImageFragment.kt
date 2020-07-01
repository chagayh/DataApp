package com.example.myapplication

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.bumptech.glide.Glide

class ImageFragment: Fragment() {
    private lateinit var userImage: ImageView
    private lateinit var appContext: MyApp

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_image, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        appContext = activity?.applicationContext as MyApp
        userImage = view.findViewById(R.id.userImage)
        setObserver()
    }

    private fun setObserver(){
        appContext.viewModel.imageLiveData.observe(this, Observer { value ->
            if (value != null) {
                Log.d("imageFragment", "value = $value")
                Glide.with(this)
                        .load(ServerHolder.BASE_URL + value)
                        .into(userImage)


            }
        })

    }

}