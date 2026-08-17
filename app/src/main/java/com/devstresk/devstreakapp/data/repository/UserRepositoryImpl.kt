package com.devstresk.devstreakapp.data.repository

import android.util.Log
import com.devstresk.devstreakapp.data.remote.CodeforcesApiService
import com.devstresk.devstreakapp.domain.model.Contest
import com.devstresk.devstreakapp.domain.model.UserStatus
import com.devstresk.devstreakapp.domain.repository.UserRepository
import com.devstresk.devstreakapp.domain.util.StreakCalculator
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(
    private val apiService: CodeforcesApiService
) : UserRepository {

    override suspend fun refreshUserInfo(handle: String) {

    }

    override fun getUserInfo(handle: String): Flow<UserStatus?> = flow {
        try {

            val userResponse = apiService.getUserInfo(handle)
            val userInfo = userResponse.result?.firstOrNull()

            if (userInfo != null) {

                val response = try {
                    apiService.getUserSubmissions(handle, from = 1, count = 5000)
                } catch (e: Exception) {
                    null
                }

                val submissions = response?.result ?: emptyList()

                val successfulSubmissions = submissions.filter {
                    it.verdict?.equals("OK", ignoreCase = true) == true
                }
                val successfulTimestamps = successfulSubmissions.mapNotNull { it.creationTimeSeconds }

                val currentStreak = StreakCalculator.calculateCurrentStreak(successfulTimestamps)
                val solvedToday = StreakCalculator.calculateSolvedToday(successfulTimestamps)

                val uniqueSolved = successfulSubmissions.distinctBy { submission ->
                    val p = submission.problem
                    val contestKey = p.contestId?.toString() ?: p.problemsetName ?: "GYM"
                    "${contestKey}_${p.index.trim().uppercase()}_${p.name.trim().lowercase()}"
                }.size


                val userStatus = UserStatus(
                    handle = userInfo.handle,
                    rating = userInfo.rating ?: 0,
                    maxRating = userInfo.maxRating ?: 0,
                    rank = userInfo.rank ?: "Unrated",
                    avatar = userInfo.titlePhoto ?: "",
                    streak = currentStreak,
                    solvedToday = solvedToday,
                    uniqueSolved = uniqueSolved,
                    contests = emptyList()
                )

                emit(userStatus)
            } else {
                emit(null)
            }
        } catch (e: Exception) {
            emit(null)
        }
    }

    override fun getContests(): Flow<List<Contest>> = flow {
        try {
            val response = apiService.getContestList()
            if (response.status == "OK") {
                val filteredContests = response.result.filter {
                    it.phase == "BEFORE" || it.phase == "CODING"
                }
                emit(filteredContests)
            } else {
                emit(emptyList())
            }
        } catch (e: Exception) {
            emit(emptyList())
        }
    }
}