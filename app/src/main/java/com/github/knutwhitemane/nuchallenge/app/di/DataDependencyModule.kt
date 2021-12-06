package com.github.knutwhitemane.nuchallenge.app.di

import android.content.Context
import androidx.room.Room
import com.github.knutwhitemane.nuchallenge.app.data.NuChallengeDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object DataDependencyModule {

    @Provides
    fun providesAppDatabase(
        @ApplicationContext context: Context
    ): NuChallengeDatabase {
        return Room
            .inMemoryDatabaseBuilder(context, NuChallengeDatabase::class.java)
            .build()
    }

}
