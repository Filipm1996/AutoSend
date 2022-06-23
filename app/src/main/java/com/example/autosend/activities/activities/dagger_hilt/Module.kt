package com.example.autosend.activities.activities.dagger_hilt

import android.content.Context
import androidx.room.Room
import com.example.autosend.activities.activities.db.ContactInfoDao
import com.example.autosend.activities.activities.db.ContactInfoDatabase
import com.example.autosend.activities.activities.repositories.Repository
import com.example.autosend.activities.activities.repositories.RepositoryDefault
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object Module {

    @Singleton
    @Provides
    fun provideRoomDb(
        @ApplicationContext context: Context
    )= Room.databaseBuilder(context, ContactInfoDatabase::class.java,"ContactInfoDb").fallbackToDestructiveMigration().build()

    @Singleton
    @Provides
    fun provideRepository(
        contactInfoDao : ContactInfoDao
    ) = Repository(contactInfoDao) as RepositoryDefault

    @Singleton
    @Provides
    fun provideContactInfoDao(
        database: ContactInfoDatabase
    )=database.contactInfoDao()
}