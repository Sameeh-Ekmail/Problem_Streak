package com.devstresk.devstreakapp.data.remote

import kotlinx.serialization.Serializable

@Serializable
data class CodeforcesUserResponse(
    val status: String? = null,
    val result: List<UserInfoDto>? = null
)

@Serializable
data class UserInfoDto(
    val handle: String = "",
    val rating: Int? = 0,
    val maxRating: Int? = 0,
    val rank: String? = "Unrated",
    val titlePhoto: String? = ""
)