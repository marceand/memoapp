package com.marceme.mvocabulary

import android.arch.persistence.room.Database
import android.arch.persistence.room.Room
import android.arch.persistence.room.RoomDatabase
import android.content.Context

@Database(entities = [Vocabulary::class], version = 1)
public abstract class VocabularyRoomDatabase : RoomDatabase() {
    abstract fun vocabularyDao(): VocabularyDao

    companion object {
        @Volatile
        private var INSTANCE: VocabularyRoomDatabase? = null

        fun getDatabase(context: Context): VocabularyRoomDatabase {
            val tempInstance = INSTANCE
            if (tempInstance != null) {
                return tempInstance
            }
            synchronized(this) {
                val instance = Room.databaseBuilder(
                        context.applicationContext,
                        VocabularyRoomDatabase::class.java,
                        "vocabulary_database"
                ).build()
                INSTANCE = instance
                return instance
            }
        }
    }
}