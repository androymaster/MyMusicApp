package com.example.mymusicapp

import android.app.PendingIntent.getActivity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.BaseAdapter
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.android.synthetic.main.activity_gallery.*

class GalleryActivity : AppCompatActivity(){

    val navigation = BottomNavigationView.OnNavigationItemSelectedListener { item ->
        when (item.itemId){
            R.id.action_profile ->{
                val intent = Intent(this@GalleryActivity, MainActivity::class.java)
                startActivity(intent)
                return@OnNavigationItemSelectedListener true
            }
            R.id.action_music -> {
                val intent = Intent(this@GalleryActivity, MusicPlayerActivity::class.java)
                startActivity(intent)
                return@OnNavigationItemSelectedListener true
            }
            R.id.action_gallery -> {
                return@OnNavigationItemSelectedListener false
            }
        }
        false
    }

    var modalist = ArrayList<ImageModal>()

    var images = intArrayOf(
        R.drawable.image1,
        R.drawable.image2,
        R.drawable.image3,
        R.drawable.image4,
        R.drawable.image5,
        R.drawable.image6
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_gallery)

        val bottomNavigationG = findViewById<BottomNavigationView>(R.id.bottom_navigation_view)
        bottomNavigationG.setOnNavigationItemSelectedListener(navigation)

        supportActionBar!!.title = "Galeria de Imagenes"

        for (i in images.indices){
            modalist.add(ImageModal(images[i]))
        }

        var customAdapter = CustomAdapter(modalist, this)
        imageGallery.adapter = customAdapter;

        imageGallery.setOnItemClickListener{parent: AdapterView<*>?, view: View?, position: Int, id: Long ->
            var intent = Intent(this, ViewActivity::class.java)
            intent.putExtra("data", modalist[position])
            startActivity(intent)
        }
    }


    class CustomAdapter(
        var itemModel: ArrayList<ImageModal>,
        var context: Context
        ): BaseAdapter(){

        var layoutInflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        override fun getView(position: Int, view: View?, viewGroup: ViewGroup?): View {
            var view = view;
            if (view == null){
                view = layoutInflater.inflate(R.layout.row_tiems, viewGroup, false)
            }
            var imageView = view?.findViewById<ImageView>(R.id.ViewImage)

            imageView?.setImageResource(itemModel[position].image!!)

            return view!!
        }

        override fun getItem(position: Int): Any {
            return  itemModel[position]
        }

        override fun getItemId(position: Int): Long {
            return  position.toLong()
        }

        override fun getCount(): Int {
            return itemModel.size
        }

    }

}