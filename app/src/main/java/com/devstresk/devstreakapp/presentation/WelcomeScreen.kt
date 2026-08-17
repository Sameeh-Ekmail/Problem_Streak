package com.devstresk.devstreakapp.presentation

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Fill
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun WelcomeScreen(
    isDark: Boolean,
    onThemeToggle: () -> Unit,
    onSaveHandle: (String, Boolean) -> Unit
) {
    var handleInput by remember { mutableStateOf("") }
    var rememberMe by remember { mutableStateOf(false) }
    var isError by remember { mutableStateOf(false) }

    Box(modifier = Modifier.fillMaxSize()) {


        IconButton(
            onClick = onThemeToggle,
            modifier = Modifier
                .align(Alignment.TopEnd)
                .padding(16.dp)
        ) {
            ThemeToggleIcon(isDark = isDark)
        }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(24.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Problem Streak",
                fontSize = 32.sp,
                fontWeight = FontWeight.ExtraBold,
                color = MaterialTheme.colorScheme.primary
            )

            Text(
                text = "Track your Codeforces progress",
                fontSize = 16.sp,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )

            Spacer(modifier = Modifier.height(48.dp))

            OutlinedTextField(
                value = handleInput,
                onValueChange = {
                    handleInput = it
                    if (it.isNotBlank()) isError = false
                },
                label = { Text("Codeforces Handle") },
                leadingIcon = { Icon(Icons.Default.Person, contentDescription = null) },
                isError = isError,
                supportingText = {
                    if (isError) {
                        Text("Please enter a valid handle")
                    }
                },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true,
                shape = MaterialTheme.shapes.medium,
                keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
                keyboardActions = KeyboardActions(
                    onDone = {
                        if (handleInput.isNotBlank()) {
                            onSaveHandle(handleInput.trim(), rememberMe)
                        } else {
                            isError = true
                        }
                    }
                )
            )



            Spacer(modifier = Modifier.height(16.dp))

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { rememberMe = !rememberMe }
                    .padding(vertical = 8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Checkbox(
                    checked = rememberMe,
                    onCheckedChange = { rememberMe = it }
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = "Remember me",
                    fontSize = 14.sp,
                    color = MaterialTheme.colorScheme.onSurface
                )
            }

            Spacer(modifier = Modifier.height(32.dp))

            Button(
                onClick = {
                    if (handleInput.isNotBlank()) {
                        onSaveHandle(handleInput.trim(), rememberMe)
                    } else {
                        isError = true
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp),
                shape = MaterialTheme.shapes.medium
            ) {
                Text("Start Tracking", fontSize = 18.sp, fontWeight = FontWeight.Bold)
            }
        }
    }
}

@Composable
fun ThemeToggleIcon(isDark: Boolean) {
    val circleOutlineColor = if (isDark) Color.White else Color.Black
    val leftHalfColor = if (isDark) Color.White else Color.Black
    val rightHalfColor = if (isDark) Color.Black else Color.White

    Canvas(modifier = Modifier.size(24.dp)) {
        val radius = size.minDimension / 2

        drawArc(
            color = leftHalfColor,
            startAngle = 90f,
            sweepAngle = 180f,
            useCenter = true,
            style = Fill
        )

        drawArc(
            color = rightHalfColor,
            startAngle = 270f,
            sweepAngle = 180f,
            useCenter = true,
            style = Fill
        )

        drawCircle(
            color = circleOutlineColor,
            radius = radius,
            style = Stroke(width = 2.dp.toPx())
        )
    }
}
