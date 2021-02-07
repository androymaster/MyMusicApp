package com.example.mymusicapp

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [Usuarios::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {

    abstract fun usuarios(): UsuariosDao

    companion object{
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            val tempInstance = INSTANCE

            if (tempInstance != null) {
                return tempInstance
            }

            synchronized(this){
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "App_database"
                ).build()

                INSTANCE = instance
                return  instance
            }
        }
    }
}