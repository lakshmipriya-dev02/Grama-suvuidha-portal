package com.gramasuvidha.ui

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.FilterList
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.text.font.FontWeight
import androidx.lifecycle.viewmodel.compose.viewModel
import com.gramasuvidha.viewmodel.ProjectViewModel
import com.gramasuvidha.ui.components.ProjectCard
import com.gramasuvidha.data.Project
import com.gramasuvidha.utils.LanguageManager

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(viewModel: ProjectViewModel = viewModel()) {

    var selectedProject by remember { mutableStateOf<Project?>(null) }

    val isKannadaState = LanguageManager.isKannada
    val isKannada = isKannadaState.value

    var expanded by remember { mutableStateOf(false) }
    var selectedFilter by remember { mutableStateOf("ALL") }

    val projectList = viewModel.projectList

    val filteredList = when (selectedFilter) {
        "COMPLETED" -> projectList.filter { it.status_en == "Completed" }
        "IN_PROGRESS" -> projectList.filter { it.status_en == "In Progress" }
        else -> projectList
    }

    val allText = if (isKannada) "ಎಲ್ಲಾ" else "All"
    val completedText = if (isKannada) "ಪೂರ್ಣಗೊಂಡಿದೆ" else "Completed"
    val inProgressText = if (isKannada) "ಪ್ರಗತಿಯಲ್ಲಿದೆ" else "In Progress"

    val currentFilterText = when (selectedFilter) {
        "COMPLETED" -> completedText
        "IN_PROGRESS" -> inProgressText
        else -> allText
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        "GramaSuvidha",
                        fontWeight = FontWeight.Bold
                    )
                },
                actions = {

                    // 🌐 Language Toggle
                    TextButton(onClick = {
                        isKannadaState.value = !isKannadaState.value
                    }) {
                        Text(if (isKannada) "EN" else "ಕನ್ನಡ")
                    }

                    // 🔽 Filter Icon
                    Box {

                        IconButton(onClick = { expanded = true }) {
                            Icon(
                                imageVector = Icons.Default.FilterList,
                                contentDescription = "Filter"
                            )
                        }

                        DropdownMenu(
                            expanded = expanded,
                            onDismissRequest = { expanded = false }
                        ) {

                            DropdownMenuItem(
                                text = { Text(allText) },
                                onClick = {
                                    selectedFilter = "ALL"
                                    expanded = false
                                }
                            )

                            DropdownMenuItem(
                                text = { Text(completedText) },
                                onClick = {
                                    selectedFilter = "COMPLETED"
                                    expanded = false
                                }
                            )

                            DropdownMenuItem(
                                text = { Text(inProgressText) },
                                onClick = {
                                    selectedFilter = "IN_PROGRESS"
                                    expanded = false
                                }
                            )
                        }
                    }
                }
            )
        }
    ) { padding ->

        Box(
            modifier = Modifier
                .padding(padding)
                .fillMaxSize()
        ) {

            if (selectedProject == null) {

                LazyColumn(
                    contentPadding = PaddingValues(vertical = 8.dp)
                ) {
                    items(filteredList) { project ->
                        ProjectCard(
                            project = project,
                            onClick = { selectedProject = it }
                        )
                    }

                    // 🔽 bottom spacing
                    item {
                        Spacer(modifier = Modifier.height(12.dp))
                    }
                }

            } else {

                DetailScreen(
                    project = selectedProject!!,
                    onBack = { selectedProject = null }
                )
            }
        }
    }
}