package com.gramasuvidha.ui.components

import androidx.compose.animation.core.*
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.draw.scale
import com.gramasuvidha.data.Project
import com.gramasuvidha.utils.LanguageManager

@Composable
fun ProjectCard(
    project: Project,
    onClick: (Project) -> Unit
) {

    val isKannada = LanguageManager.isKannada.value

    var pressed by remember { mutableStateOf(false) }

    val scale by animateFloatAsState(
        targetValue = if (pressed) 0.97f else 1f,
        animationSpec = tween(150),
        label = ""
    )

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(10.dp)
            .scale(scale)
            .clickable {
                pressed = true
                onClick(project)
                pressed = false
            },
        elevation = CardDefaults.cardElevation(6.dp)
    ) {

        Column(modifier = Modifier.padding(16.dp)) {

            Text(
                text = if (isKannada) project.name_kn else project.name_en,
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = "Budget: ${project.budget}",
                color = MaterialTheme.colorScheme.primary,
                fontWeight = FontWeight.SemiBold
            )

            Spacer(modifier = Modifier.height(4.dp))

            Text(
                text = if (isKannada) project.status_kn else project.status_en,
                color = MaterialTheme.colorScheme.secondary
            )
        }
    }
}