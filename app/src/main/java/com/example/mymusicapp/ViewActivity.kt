package com.example.mymusicapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_view.*

class ViewActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_view)

        var modalItems: ImageModal =  intent.getSerializableExtra("data") as ImageModal

        viewImage.setImageResource(modalItems.image!!)

    }
}