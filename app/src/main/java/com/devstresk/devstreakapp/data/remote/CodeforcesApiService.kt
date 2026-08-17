package com.devstresk.devstreakapp.data.remote

import com.devstresk.devstreakapp.domain.model.ContestResponse

interface CodeforcesApiService {

    companion object {
        const val BASE_URL = "https://codeforces.com/api"
    }

    suspend fun getUserSubmissions(
        handle: String,
        from: Int,
        count: Int
    ): CodeforcesSubmissionsResponse

    suspend fun getUserInfo(handle: String): CodeforcesUserResponse

    suspend fun getContestList(): ContestResponse
}