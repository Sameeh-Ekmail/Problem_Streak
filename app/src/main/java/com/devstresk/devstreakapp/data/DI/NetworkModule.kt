package com.devstresk.devstreakapp.data.di

import com.devstresk.devstreakapp.data.remote.CodeforcesApiService
import com.devstresk.devstreakapp.data.remote.CodeforcesApiServiceImpl
import com.devstresk.devstreakapp.data.repository.UserRepositoryImpl
import com.devstresk.devstreakapp.domain.repository.UserRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.ktor.client.HttpClient
import io.ktor.client.engine.cio.CIO
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Provides
    @Singleton
    fun provideHttpClient(): HttpClient {
        return HttpClient(CIO) {
            install(ContentNegotiation) {
                json(Json {
                    ignoreUnknownKeys = true
                    coerceInputValues = true
                })
            }
        }
    }

    @Provides
    @Singleton
    fun provideCodeforcesApiService(httpClient: HttpClient): CodeforcesApiService {
        return CodeforcesApiServiceImpl(httpClient)
    }

    @Provides
    @Singleton
    fun provideUserRepository(
        apiService: CodeforcesApiService
    ): UserRepository {
        return UserRepositoryImpl(apiService)
    }
}
