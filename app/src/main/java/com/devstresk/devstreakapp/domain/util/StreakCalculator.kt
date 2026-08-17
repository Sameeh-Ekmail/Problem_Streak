package com.devstresk.devstreakapp.domain.util

import java.time.Instant
import java.time.ZoneId
import java.time.LocalDate

object StreakCalculator {


    fun calculateCurrentStreak(submissionTimestamps: List<Long>): Int {
        if (submissionTimestamps.isEmpty()) return 0

        val solvedDates = submissionTimestamps.map { timestamp ->
            Instant.ofEpochSecond(timestamp)
                .atZone(ZoneId.systemDefault())
                .toLocalDate()
        }.toSet()

        var currentStreak = 0
        var checkDate = LocalDate.now()

        if (!solvedDates.contains(checkDate)) {
            checkDate = checkDate.minusDays(1)
        }

        while (solvedDates.contains(checkDate)) {
            currentStreak++
            checkDate = checkDate.minusDays(1)
        }

        return currentStreak
    }

    fun calculateSolvedToday(submissionTimestamps: List<Long>): Int {
        val today = LocalDate.now()
        return submissionTimestamps.map { timestamp ->
            Instant.ofEpochSecond(timestamp)
                .atZone(ZoneId.systemDefault())
                .toLocalDate()
        }.count { it == today }
    }

    fun calculateTotalSolved(submissionTimestamps: List<Long>): Int {
        return submissionTimestamps.map { timestamp ->
            Instant.ofEpochSecond(timestamp)
                .atZone(ZoneId.systemDefault())
                .toLocalDate()
        }.toSet().size
    }
}