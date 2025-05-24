package ir.miare.androidcodechallenge.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import ir.miare.androidcodechallenge.data.api.ApiService
import ir.miare.androidcodechallenge.data.repository.FakeDataRepository
import ir.miare.androidcodechallenge.data.repository.FakeDataRepositoryImpl
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
class RepositoryModule {

    @Provides
    @Singleton
    fun provideInfoRepository(apiService: ApiService): FakeDataRepository =
        FakeDataRepositoryImpl(apiService)

}