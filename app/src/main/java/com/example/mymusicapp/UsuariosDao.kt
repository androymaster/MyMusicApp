package com.example.mymusicapp

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface UsuariosDao {

    @Query("SELECT * FROM usuarios")
    fun getAll(): LiveData<List<Usuarios>>

    @Query("SELECT * FROM usuarios WHERE idUsuario = :id")
    fun getUser(id: Int): LiveData<Usuarios>

    @Insert
    fun insertAll(vararg usuarios: Usuarios)

    @Update
    fun update(usuarios: Usuarios)

    @Delete
    fun delete(usuarios: Usuarios)
}