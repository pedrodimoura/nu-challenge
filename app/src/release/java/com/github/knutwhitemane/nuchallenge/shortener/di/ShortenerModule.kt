package com.github.knutwhitemane.nuchallenge.shortener.di

import com.github.knutwhitemane.nuchallenge.app.data.NuChallengeDatabase
import com.github.knutwhitemane.nuchallenge.common.data.service.HttpClient
import com.github.knutwhitemane.nuchallenge.shortener.data.dao.ShortenerDao
import com.github.knutwhitemane.nuchallenge.shortener.data.datasource.local.ShortenerLocalDatasource
import com.github.knutwhitemane.nuchallenge.shortener.data.datasource.local.impl.ShortenerLocalDatasourceImpl
import com.github.knutwhitemane.nuchallenge.shortener.data.datasource.remote.ShortenerRemoteDatasource
import com.github.knutwhitemane.nuchallenge.shortener.data.datasource.remote.impl.ShortenerRemoteDatasourceImpl
import com.github.knutwhitemane.nuchallenge.shortener.data.repository.ShortenerRepositoryImpl
import com.github.knutwhitemane.nuchallenge.shortener.data.service.ShortenerService
import com.github.knutwhitemane.nuchallenge.shortener.domain.repository.ShortenerRepository
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
