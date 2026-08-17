package com.devstresk.devstreakapp.domain.model

import kotlinx.serialization.Serializable

@Serializable
data class Contest(
    val id: Int,
    val name: String,
    val type: String,
    val phase: String,
    val durationSeconds: Long,
    val startTimeSeconds: Long? = null,
    val relativeTimeSeconds: Long? = null
)

@Serializable
data class ContestResponse(
    val status: String,
    val result: List<Contest>
)
