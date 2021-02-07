package com.example.mymusicapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.Observer
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    val navigation = BottomNavigationView.OnNavigationItemSelectedListener { item ->
        when (item.itemId){
            R.id.action_profile ->{
                return@OnNavigationItemSelectedListener false
            }
            R.id.action_music -> {
                val intent = Intent(this@MainActivity, MusicPlayerActivity::class.java)
                startActivity(intent)
                return@OnNavigationItemSelectedListener true
            }
            R.id.action_gallery -> {
                val intent = Intent(this@MainActivity, GalleryActivity::class.java)
                startActivity(intent)
                return@OnNavigationItemSelectedListener true
            }
        }
        false
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        supportActionBar!!.title = "Lista de Usuarios"

        var listaUsuarios = emptyList<Usuarios>()

        val database = AppDatabase.getDatabase(this)
        database.usuarios().getAll().observe(this, Observer {
            listaUsuarios = it

            val adapter = UsuarioAdapter(this, listaUsuarios)
            lista.adapter = adapter
        })

        lista.setOnItemClickListener { parent, view, position, id ->
            val intent = Intent(this, UsuarioActivity::class.java)
            intent.putExtra("id", listaUsuarios[position].idUsuario)
            startActivity(intent)
        }

        val bottomNavigation = findViewById<BottomNavigationView>(R.id.bottom_navigation_view)
        bottomNavigation.setOnNavigationItemSelectedListener(navigation)

        floatingActionButton.setOnClickListener{
            val intent = Intent(this, NuevoUsuarioActivity::class.java)
            startActivity(intent)
        }
    }
}