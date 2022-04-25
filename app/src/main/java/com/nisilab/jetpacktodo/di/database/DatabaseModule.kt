package com.nisilab.jetpacktodo.di.database

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {
    @Provides
    @Singleton
    fun getDatabase(@ApplicationContext context: Context): TodoDatabase {
        return Room.databaseBuilder(
            context.applicationContext,
            TodoDatabase::class.java,
            "todo_database"
        ).build()
    }

    @Provides
    @Singleton
    fun getDao(db: TodoDatabase): TodoDao{
        return db.todoDao()
    }
}