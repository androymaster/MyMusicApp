package com.example.mymusicapp

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import kotlinx.android.synthetic.main.item_users.view.*

class UsuarioAdapter (private val mContext: Context, private val listaUsuarios: List<Usuarios>) : ArrayAdapter<Usuarios>(mContext, 0 , listaUsuarios){
    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {

        val layout = LayoutInflater.from(mContext).inflate(R.layout.item_users, parent, false)
        val usuario = listaUsuarios[position]

        layout.txtViewUserName.text = usuario.lastname
        layout.txtViewName.text = usuario.name
        layout.imageView.setImageResource(usuario.image)

        return layout
    }
}