package com.example.mymusicapp

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity(tableName = "usuarios")
class Usuarios(
      val image: Int,
      val username: String,
      val name: String,
      val lastname: String,
      val biograph: String,
      @PrimaryKey(autoGenerate = true)
      var idUsuario: Int = 0
):Serializable