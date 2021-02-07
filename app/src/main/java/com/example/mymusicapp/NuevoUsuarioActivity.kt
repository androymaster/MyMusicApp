package com.example.mymusicapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.KeyEvent
import kotlinx.android.synthetic.main.activity_nuevo_usuario.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext

class NuevoUsuarioActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_nuevo_usuario)

        supportActionBar!!.title = "Nuevo Perfil"

        var idUsuario: Int? = null

        if (intent.hasExtra("usuario")){
            val usuario = intent.extras?.getSerializable("usuario") as Usuarios

            txtUserName.setText(usuario.lastname)
            txtName.setText(usuario.name)
            txtLastName.setText(usuario.lastname)
            txtBiograph.setText(usuario.biograph)
            idUsuario = usuario.idUsuario
        }

        val dataBase = AppDatabase.getDatabase(this)

        btn_save.setOnClickListener{
            val nombreusuario = txtUserName.text.toString()
            val nombre = txtName.text.toString()
            val apellidos = txtLastName.text.toString()
            val biografia = txtBiograph.text.toString()

            val usuario = Usuarios(R.drawable.ic_launcher_background, nombreusuario, nombre, apellidos, biografia)

            if (idUsuario != null){
                CoroutineScope(Dispatchers.IO).launch {
                    usuario.idUsuario = idUsuario
                    dataBase.usuarios().update(usuario)
                    this@NuevoUsuarioActivity.finish()
                }
            }else {
                CoroutineScope(Dispatchers.IO).launch {
                    dataBase.usuarios().insertAll(usuario)
                    this@NuevoUsuarioActivity.finish()
                }
            }
        }
    }
}