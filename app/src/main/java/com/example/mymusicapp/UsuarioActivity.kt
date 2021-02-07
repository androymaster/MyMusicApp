package com.example.mymusicapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import kotlinx.android.synthetic.main.activity_nuevo_usuario.*
import kotlinx.android.synthetic.main.activity_usuario.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext

class UsuarioActivity : AppCompatActivity() {

    private lateinit var database: AppDatabase
    private lateinit var usuario: Usuarios
    private lateinit var usuarioLiveData: LiveData<Usuarios>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_usuario)

        database = AppDatabase.getDatabase(this)
        val idUsuario = intent.getIntExtra("id", 0)

        usuarioLiveData = database.usuarios().getUser(idUsuario)
        usuarioLiveData.observe(this, Observer {
           usuario = it

           photo.setImageResource(usuario.image)
           textViewUserName.text = usuario.username
           textName.text = usuario.name
           textViewLastName.text = usuario.lastname
           textViewBio.text = usuario.biograph
        })
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.usuario_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
           R.id.edit_item ->{
               val intent = Intent(this, NuevoUsuarioActivity::class.java)
               intent.putExtra("usuario", usuario)
               startActivity(intent)
           }
           R.id.delete_item ->{
               usuarioLiveData.removeObserver { this }
               CoroutineScope(Dispatchers.IO).launch(){
                  database.usuarios().delete(usuario)
                   this@UsuarioActivity.finish()
               }
           }
        }
        return super.onOptionsItemSelected(item)
    }
}