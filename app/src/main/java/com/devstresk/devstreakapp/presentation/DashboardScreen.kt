package com.devstresk.devstreakapp.presentation

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.devstresk.devstreakapp.domain.model.Contest
import com.devstresk.devstreakapp.domain.model.UserStatus
import java.text.SimpleDateFormat
import java.util.*

@Composable
fun DashboardScreen(
    viewModel: UserViewModel,
    onNavigateBack: () -> Unit
) {
    val state = viewModel.uiState.value
    val isDark = viewModel.isDarkMode.value

    Box(modifier = Modifier.fillMaxSize()) {
        Button(
            onClick = {
                viewModel.clearSavedHandle()
                onNavigateBack()
            },
            modifier = Modifier
                .align(Alignment.TopStart)
                .padding(16.dp)
        ) {
            Text("Log out")
        }

        IconButton(
            onClick = { viewModel.toggleTheme() },
            modifier = Modifier
                .align(Alignment.TopEnd)
                .padding(16.dp)
        ) {
            ThemeToggleIcon(isDark = isDark)
        }

        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = 80.dp, start = 16.dp, end = 16.dp, bottom = 16.dp),
            contentAlignment = Alignment.Center
        ) {
            Column(
                modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                when (state) {
                    is UiState.Loading -> {
                        Box(modifier = Modifier.weight(1f), contentAlignment = Alignment.Center) {
                            CircularProgressIndicator(color = MaterialTheme.colorScheme.primary)
                        }
                    }
                    is UiState.Success -> {
                        UserInfoCard(user = state.data, contests = state.contests)
                    }
                    is UiState.Error -> {
                        Column(
                            modifier = Modifier.weight(1f),
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.Center
                        ) {
                            Text(
                                text = state.message,
                                color = MaterialTheme.colorScheme.error,
                                fontSize = 16.sp,
                                fontWeight = FontWeight.Bold,
                                modifier = Modifier.padding(bottom = 16.dp)
                            )

                            Button(
                                onClick = onNavigateBack,
                                colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.secondary)
                            ) {
                                Text("try again")
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun UserInfoCard(user: UserStatus, contests: List<Contest>) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(24.dp))

        AsyncImage(
            model = user.avatar.ifBlank { "https://userpic.codeforces.org/no-title.jpg" },
            contentDescription = "Profile Picture ",
            modifier = Modifier
                .size(110.dp)
                .clip(CircleShape)
                .border(2.dp, MaterialTheme.colorScheme.primary, CircleShape),
            contentScale = ContentScale.Crop
        )

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = "Codeforces Handle: ${user.handle}",
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.onBackground,
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(24.dp))

        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            InfoRow(label = "Current Rating", value = "${user.rating} (${user.rank})")
            InfoRow(label = "Max Rating", value = "${user.maxRating}")
            InfoRow(label = "Problem Solving Streak", value = "${user.streak} days")
            InfoRow(label = "Solved Today", value = "${user.solvedToday}")
            InfoRow(label = "Unique Solved", value = "${user.uniqueSolved}")
        }

        Spacer(modifier = Modifier.height(32.dp))

        ContestsSection(contests = contests)

        Spacer(modifier = Modifier.height(32.dp))
    }
}

@Composable
fun InfoRow(label: String, value: String) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = "$label: ",
            fontSize = 16.sp,
            fontWeight = FontWeight.Normal,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
        Text(
            text = value,
            fontSize = 16.sp,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.onBackground
        )
    }
}

@Composable
fun ContestsSection(contests: List<Contest>) {
    val activeContests = contests.filter { it.phase == "CODING" }
    val upcomingContests = contests.filter { it.phase == "BEFORE" }.take(5)

    Column(
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        if (activeContests.isNotEmpty()) {
            Text(
                text = "🔴 Live Contests",
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFFE53935)
            )
            activeContests.forEach { contest ->
                ContestItemCard(contest = contest, isLive = true)
            }
        }

        Text(
            text = "📅 Upcoming Contests",
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.primary
        )

        if (upcomingContests.isEmpty()) {
            Text(
                text = "No upcoming contests found.",
                fontSize = 14.sp,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        } else {
            upcomingContests.forEach { contest ->
                ContestItemCard(contest = contest, isLive = false)
            }
        }
    }
}

@Composable
fun ContestItemCard(contest: Contest, isLive: Boolean) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(
            containerColor = if (isLive) {
                Color(0xFFFFEBEE)
            } else {
                MaterialTheme.colorScheme.surfaceVariant
            }
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Text(
                text = contest.name,
                fontSize = 15.sp,
                fontWeight = FontWeight.Bold,
                color = if (isLive) Color(0xFFC62828) else MaterialTheme.colorScheme.onSurface,
                maxLines = 2
            )

            Spacer(modifier = Modifier.height(8.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                val startTimeFormatted = contest.startTimeSeconds?.let {
                    val date = Date(it * 1000)
                    val format = SimpleDateFormat("MMM dd, yyyy - hh:mm a", Locale.getDefault())
                    format.format(date)
                } ?: "Unknown Time"

                Text(
                    text = "Starts: $startTimeFormatted",
                    fontSize = 13.sp,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )

                val durationHours = contest.durationSeconds / 3600
                Text(
                    text = "${durationHours}h duration",
                    fontSize = 13.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = MaterialTheme.colorScheme.primary
                )
            }
        }
    }
}
