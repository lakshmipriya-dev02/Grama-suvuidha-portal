package com.gramasuvidha.utils

import android.content.Context

object FeedbackManager {

    private const val PREF_NAME = "feedback_prefs"

    // ⭐ Save Rating
    fun saveRating(context: Context, projectId: String, rating: Int) {
        val prefs = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
        val current = prefs.getString("$projectId-ratings", "") ?: ""
        val updated = if (current.isEmpty()) "$rating" else "$current,$rating"
        prefs.edit().putString("$projectId-ratings", updated).apply()
    }

    // ⭐ Get Average Rating
    fun getAverageRating(context: Context, projectId: String): Float {
        val prefs = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
        val data = prefs.getString("$projectId-ratings", "") ?: ""

        if (data.isEmpty()) return 0f

        val list = data.split(",").map { it.toInt() }
        return list.average().toFloat()
    }

    // 💬 Save Feedback
    fun saveFeedback(context: Context, projectId: String, feedback: String) {
        val prefs = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
        val current = prefs.getString("$projectId-feedback", "") ?: ""
        val updated = if (current.isEmpty()) feedback else "$current|$feedback"
        prefs.edit().putString("$projectId-feedback", updated).apply()
    }

    // 💬 Get Feedback List
    fun getFeedback(context: Context, projectId: String): List<String> {
        val prefs = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
        val data = prefs.getString("$projectId-feedback", "") ?: ""

        if (data.isEmpty()) return emptyList()
        return data.split("|")
    }

    // 🚨 Save Issue
    fun saveIssue(context: Context, projectId: String, issue: String) {
        val prefs = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
        val current = prefs.getString("$projectId-issues", "") ?: ""
        val updated = if (current.isEmpty()) issue else "$current|$issue"
        prefs.edit().putString("$projectId-issues", updated).apply()
    }

    // 🚨 Get Issues
    fun getIssues(context: Context, projectId: String): List<String> {
        val prefs = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
        val data = prefs.getString("$projectId-issues", "") ?: ""

        if (data.isEmpty()) return emptyList()
        return data.split("|")
    }
}