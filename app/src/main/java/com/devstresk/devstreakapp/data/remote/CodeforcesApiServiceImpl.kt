package com.devstresk.devstreakapp.data.remote

import com.devstresk.devstreakapp.domain.model.ContestResponse
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import javax.inject.Inject

private const val BASE_URL = "https://codeforces.com/api"

class CodeforcesApiServiceImpl @Inject constructor(
    private val client: HttpClient
) : CodeforcesApiService {

    override suspend fun getUserSubmissions(
        handle: String,
        from: Int,
        count: Int
    ): CodeforcesSubmissionsResponse {
        return client.get("$BASE_URL/user.status") {
            parameter("handle", handle)
            parameter("from", from)
            parameter("count", count)
        }.body()
    }

    override suspend fun getUserInfo(handle: String): CodeforcesUserResponse {
        return client.get("$BASE_URL/user.info") {
            parameter("handles", handle)
        }.body()
    }

    override suspend fun getContestList(): ContestResponse {
        return client.get("$BASE_URL/contest.list") {
            parameter("gym", false)
        }.body()
    }
}