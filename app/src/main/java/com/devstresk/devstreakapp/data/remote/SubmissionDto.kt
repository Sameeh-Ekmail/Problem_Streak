package com.devstresk.devstreakapp.data.remote

import kotlinx.serialization.Serializable

@Serializable
data class CodeforcesSubmissionsResponse(
    val status: String? = null,
    val result: List<SubmissionDto>? = null,
    val comment: String? = null
)

@Serializable
data class SubmissionDto(
    val id: Long? = null,
    val contestId: Int? = null,
    val creationTimeSeconds: Long? = null,
    val relativeTimeSeconds: Long? = null,
    val problem: ProblemDto,
    val author: AuthorDto? = null,
    val programmingLanguage: String? = null,
    val verdict: String? = null,
    val testset: String? = null,
    val passedTestCount: Int? = null,
    val timeConsumedMillis: Int? = null,
    val memoryConsumedBytes: Long? = null
)

@Serializable
data class ProblemDto(
    val contestId: Int? = null,
    val problemsetName: String? = null,
    val index: String = "",
    val name: String = "",
    val type: String? = null,
    val points: Double? = null,
    val rating: Int? = null,
    val tags: List<String>? = null
)

@Serializable
data class AuthorDto(
    val contestId: Int? = null,
    val members: List<MemberDto>? = null,
    val participantType: String? = null,
    val ghost: Boolean? = null,
    val room: Int? = null,
    val startTimeSeconds: Long? = null
)

@Serializable
data class MemberDto(
    val handle: String? = null
)