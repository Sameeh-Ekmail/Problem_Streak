package com.devstresk.devstreakapp.domain.model

import kotlinx.serialization.Serializable

@Serializable
data class UserStatus(
    val handle: String,
    val rating: Int,
    val maxRating: Int,
    val rank: String,
    val avatar: String,
    val streak: Int,
    val solvedToday: Int,
    val uniqueSolved: Int,
    val contests: List<Contest> = emptyList()
)

@Serializable
data class UserInfo(
    val handle: String,
    val rating: Int? = 0,
    val maxRating: Int? = 0,
    val rank: String? = "Unrated",
    val maxRank: String? = "Unrated",
    val titlePhoto: String = "",
    val streak: Int = 0,
    val solvedToday: Int = 0,
    val uniqueSolved: Int = 0 ,
    val contests: List<com.devstresk.devstreakapp.domain.model.Contest> = emptyList()
)
