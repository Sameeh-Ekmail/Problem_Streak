package com.devstresk.devstreakapp.domain.repository

import com.devstresk.devstreakapp.domain.model.Contest
import com.devstresk.devstreakapp.domain.model.UserStatus
import kotlinx.coroutines.flow.Flow

interface UserRepository {
    suspend fun refreshUserInfo(handle: String)
    fun getUserInfo(handle: String): Flow<UserStatus?>
    fun getContests(): Flow<List<Contest>>
}
