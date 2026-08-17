package com.devstresk.devstreakapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import com.devstresk.devstreakapp.presentation.DashboardScreen
import com.devstresk.devstreakapp.presentation.UserViewModel
import com.devstresk.devstreakapp.presentation.WelcomeScreen
import com.devstresk.devstreakapp.ui.theme.DevStreakAppTheme
import dagger.hilt.android.AndroidEntryPoint

 @AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val userViewModel: UserViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val isDark by userViewModel.isDarkMode

            DevStreakAppTheme(darkTheme = isDark) {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val handle by userViewModel.savedHandle

                    if (handle == null) {
                        WelcomeScreen(
                            isDark = isDark,
                            onThemeToggle = { userViewModel.toggleTheme() },
                            onSaveHandle = { inputHandle, isRemembered ->
                                userViewModel.saveNewHandle(inputHandle, isRemembered)
                            }
                        )
                    } else {
                        DashboardScreen(
                            viewModel = userViewModel,
                            onNavigateBack = {
                                userViewModel.clearSavedHandle()
                            }
                        )
                    }
                }
            }
        }
    }
}
