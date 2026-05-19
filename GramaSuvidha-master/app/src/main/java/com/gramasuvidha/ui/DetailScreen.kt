package com.gramasuvidha.ui

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.slideInVertically
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import com.gramasuvidha.data.Project
import com.gramasuvidha.utils.LanguageManager
import com.gramasuvidha.utils.FeedbackManager
import kotlinx.coroutines.launch

@Composable
fun DetailScreen(
    project: Project,
    onBack: () -> Unit
) {

    val context = LocalContext.current
    val isKannada = LanguageManager.isKannada.value

    var rating by remember { mutableStateOf(0) }
    var feedbackText by remember { mutableStateOf("") }
    var issueText by remember { mutableStateOf("") }

    var feedbackList by remember { mutableStateOf(listOf<String>()) }

    val projectId = project.name_en

    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()

    // 🔥 Animation control
    var visible by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        feedbackList = FeedbackManager.getFeedback(context, projectId)
        visible = true
    }

    val avgRating = FeedbackManager.getAverageRating(context, projectId)

    Scaffold(
        snackbarHost = { SnackbarHost(snackbarHostState) }
    ) { padding ->

        AnimatedVisibility(
            visible = visible,
            enter = fadeIn() + slideInVertically(initialOffsetY = { it / 2 })
        ) {

            Column(
                modifier = Modifier
                    .padding(padding)
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState())
                    .padding(16.dp)
            ) {

                // 🔹 TITLE
                Text(
                    text = if (isKannada) project.name_kn else project.name_en,
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold
                )

                Spacer(modifier = Modifier.height(12.dp))

                // 🔹 DESCRIPTION
                Text(
                    text = if (isKannada) project.description_kn else project.description_en,
                    fontSize = 15.sp,
                    lineHeight = 22.sp
                )

                Spacer(modifier = Modifier.height(16.dp))

                // 🔹 IMAGES
                val beforeId = context.resources.getIdentifier(
                    project.before_image,
                    "drawable",
                    context.packageName
                )

                val afterId = context.resources.getIdentifier(
                    project.after_image,
                    "drawable",
                    context.packageName
                )

                Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {

                    if (beforeId != 0) {
                        Image(
                            painter = painterResource(beforeId),
                            contentDescription = "Before",
                            modifier = Modifier
                                .weight(1f)
                                .height(150.dp),
                            contentScale = ContentScale.Crop
                        )
                    }

                    if (afterId != 0) {
                        Image(
                            painter = painterResource(afterId),
                            contentDescription = "After",
                            modifier = Modifier
                                .weight(1f)
                                .height(150.dp),
                            contentScale = ContentScale.Crop
                        )
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                // 🔹 PROJECT INFO CARD
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    elevation = CardDefaults.cardElevation(6.dp)
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {

                        Text("Budget: ${project.budget}")

                        Text(
                            if (isKannada)
                                "ಸ್ಥಿತಿ: ${project.status_kn}"
                            else
                                "Status: ${project.status_en}"
                        )

                        Spacer(modifier = Modifier.height(10.dp))

                        LinearProgressIndicator(
                            progress = { project.progress / 100f },
                            modifier = Modifier.fillMaxWidth()
                        )

                        Spacer(modifier = Modifier.height(6.dp))

                        Text("Progress: ${project.progress}%")

                        Spacer(modifier = Modifier.height(6.dp))

                        Text("Expected: ${project.expected_completion}")
                    }
                }

                Spacer(modifier = Modifier.height(20.dp))

                // ⭐ RATING SECTION
                Text(
                    text = "⭐ ${"%.1f".format(avgRating)} / 5",
                    fontWeight = FontWeight.SemiBold
                )

                Spacer(modifier = Modifier.height(8.dp))

                Row {
                    for (i in 1..5) {
                        Text(
                            text = if (i <= rating) "★" else "☆",
                            fontSize = 28.sp,
                            modifier = Modifier
                                .padding(4.dp)
                                .clickable { rating = i }
                        )
                    }
                }

                Spacer(modifier = Modifier.height(8.dp))

                Button(
                    onClick = {
                        FeedbackManager.saveRating(context, projectId, rating)
                        scope.launch {
                            snackbarHostState.showSnackbar("Rating submitted 🎉")
                        }
                    },
                    enabled = rating > 0,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Submit Rating")
                }

                Spacer(modifier = Modifier.height(20.dp))

                // 💬 FEEDBACK
                OutlinedTextField(
                    value = feedbackText,
                    onValueChange = { feedbackText = it },
                    label = { Text("Write feedback") },
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(8.dp))

                Button(
                    onClick = {
                        FeedbackManager.saveFeedback(context, projectId, feedbackText)
                        feedbackList = FeedbackManager.getFeedback(context, projectId)
                        feedbackText = ""

                        scope.launch {
                            snackbarHostState.showSnackbar("Feedback added 💬")
                        }
                    },
                    enabled = feedbackText.isNotEmpty(),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Submit Feedback")
                }

                Spacer(modifier = Modifier.height(12.dp))

                if (feedbackList.isEmpty()) {
                    Text("No feedback yet. Be the first!")
                } else {
                    feedbackList.forEach {
                        Text("• $it")
                    }
                }

                Spacer(modifier = Modifier.height(20.dp))

                // 🚨 ISSUE REPORT
                OutlinedTextField(
                    value = issueText,
                    onValueChange = { issueText = it },
                    label = { Text("Report issue") },
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(8.dp))

                Button(
                    onClick = {
                        FeedbackManager.saveIssue(context, projectId, issueText)
                        issueText = ""

                        scope.launch {
                            snackbarHostState.showSnackbar("Issue reported 🚨")
                        }
                    },
                    enabled = issueText.isNotEmpty(),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Submit Issue")
                }

                Spacer(modifier = Modifier.height(24.dp))

                Button(
                    onClick = onBack,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Back")
                }
            }
        }
    }
}