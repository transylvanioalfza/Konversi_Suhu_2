package org.d3if4118.assessment2.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase


@Database(entities = [Suhu::class], version = 1, exportSchema = false)
abstract class DatabaseSuhu : RoomDatabase() {
    abstract val suhuBadanDao: SuhuDao

    companion object {
        @Volatile
        private var INSTANCE: DatabaseSuhu? = null
        fun getInstance(context: Context): DatabaseSuhu {
            synchronized(this) {
                var instance = INSTANCE

                if (instance == null) {
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        DatabaseSuhu::class.java,
                        "suhu_database"
                    )
                        .fallbackToDestructiveMigration().build()
                    INSTANCE = instance
                }
                return instance

            }
        }

    }

}