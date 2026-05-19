package com.gramasuvidha.data

import android.content.Context
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

object ProjectLoader {

    fun loadProjects(context: Context): List<Project> {
        val json = context.assets.open("projects.json")
            .bufferedReader()
            .use { it.readText() }

        val type = object : TypeToken<List<Project>>() {}.type
        return Gson().fromJson(json, type)
    }
}