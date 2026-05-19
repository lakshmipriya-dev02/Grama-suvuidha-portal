package com.gramasuvidha.data

data class Project(
    val name_en: String,
    val name_kn: String,
    val description_en: String,
    val description_kn: String,
    val budget: String,
    val status_en: String,
    val status_kn: String,
    val progress: Int,
    val expected_completion: String,
    val before_image: String,
    val after_image: String,

    // 🔥 NEW
    val updates: List<String> = emptyList()
)