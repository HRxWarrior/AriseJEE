package com.arisejee.app

import android.app.Application
import androidx.room.Room
import com.arisejee.app.data.db.AppDatabase

class AriseJeeApp : Application() {
    val database: AppDatabase by lazy {
        Room.databaseBuilder(this, AppDatabase::class.java, "arise_jee_db").fallbackToDestructiveMigration().build()
    }
}
