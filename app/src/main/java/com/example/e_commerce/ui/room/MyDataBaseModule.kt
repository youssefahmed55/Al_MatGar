package com.example.e_commerce.ui.room

import android.app.Application
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object MyDataBaseModule {

    @Provides
    @Singleton
    @Synchronized
    fun provideDatabase(application: Application?): AppDatabase {
        return Room.databaseBuilder(application!!, AppDatabase::class.java, "myDataBase")
            .fallbackToDestructiveMigration()
            .build()
    }

    @Provides
    @Singleton
    @Synchronized
    fun provideProductModelDao(myDatabase: AppDatabase): ProductModelDao {
        return myDatabase.productModelDao()
    }

    @Provides
    @Singleton
    @Synchronized
    fun provideCategoryModelDao(myDatabase: AppDatabase): CategoryModelDao {
        return myDatabase.categoryModelDao()
    }

    @Provides
    @Singleton
    @Synchronized
    fun provideSliderModelDao(myDatabase: AppDatabase): SliderModelDao {
        return myDatabase.sliderModelDao()
    }

}