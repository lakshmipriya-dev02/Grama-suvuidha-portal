package com.gramasuvidha.utils

import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.mutableStateOf

data class InteractionState(
    var rating: Float = 0f,
    var feedbacks: MutableList<String> = mutableListOf(),
    var supportCount: Int = 0,
    var reports: MutableList<String> = mutableListOf()
)

object InteractionManager {

    // key = project name (EN is enough as unique id here)
    private val map = mutableStateMapOf<String, InteractionState>()

    fun get(projectKey: String): InteractionState {
        return map.getOrPut(projectKey) { InteractionState() }
    }
}