package com.gramasuvidha.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.gramasuvidha.data.Project
import com.gramasuvidha.data.ProjectLoader

class ProjectViewModel(application: Application) : AndroidViewModel(application) {

    val projectList: List<Project> =
        ProjectLoader.loadProjects(application)
}