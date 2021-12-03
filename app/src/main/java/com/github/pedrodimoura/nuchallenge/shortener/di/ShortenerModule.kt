package com.github.pedrodimoura.nuchallenge.shortener.di

import com.github.pedrodimoura.nuchallenge.app.data.NuChallengeDatabase
import com.github.pedrodimoura.nuchallenge.common.data.service.HttpClient
import com.github.pedrodimoura.nuchallenge.shortener.data.dao.ShortenerDao
import com.github.pedrodimoura.nuchallenge.shortener.data.datasource.local.ShortenerLocalDatasource
import com.github.pedrodimoura.nuchallenge.shortener.data.datasource.local.impl.ShortenerLocalDatasourceImpl
import com.github.pedrodimoura.nuchallenge.shortener.data.datasource.remote.ShortenerRemoteDatasource
import com.github.pedrodimoura.nuchallenge.shortener.data.datasource.remote.impl.ShortenerRemoteDatasourceImpl
import com.github.pedrodimoura.nuchallenge.shortener.data.repository.ShortenerRepositoryImpl
import com.github.pedrodimoura.nuchallenge.shortener.data.service.ShortenerService
import com.github.pedrodimoura.nuchallenge.shortener.domain.repository.ShortenerRepository
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
abstract class RepositoryDependencyModule {
    @Binds
    abstract fun bindsShortenerRepository(
        shortenerRepositoryImpl: ShortenerRepositoryImpl
    ): ShortenerRepository

    @Binds
    abstract fun bindsShortenerLocalDatasource(
        shortenerLocalDatasourceImpl: ShortenerLocalDatasourceImpl
    ): ShortenerLocalDatasource

    @Binds
    abstract fun bindsShortenerRemoteDatasource(
        shortenerRemoteDatasourceImpl: ShortenerRemoteDatasourceImpl
    ): ShortenerRemoteDatasource
}

@Module
@InstallIn(ViewModelComponent::class)
object LocalDependencyModule {
    @Provides
    fun providesShortenerDao(
        nuChallengeDatabase: NuChallengeDatabase
    ): ShortenerDao = nuChallengeDatabase.getShortenerDao()
}

@Module
@InstallIn(ViewModelComponent::class)
object RemoteDependencyModule {
    @Provides
    fun providesShortenerService(
        retrofitClient: HttpClient.RetrofitClient
    ): ShortenerService = retrofitClient.create(ShortenerService::class.java)
}
