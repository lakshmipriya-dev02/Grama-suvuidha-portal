package com.gramasuvidha.ui

import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.ui.Modifier
import androidx.compose.foundation.layout.padding
import androidx.navigation.compose.*
import androidx.lifecycle.viewmodel.compose.viewModel
import com.gramasuvidha.viewmodel.ProjectViewModel

sealed class Screen(val route: String, val icon: ImageVector, val title: String) {
    object Home : Screen("home", Icons.Default.Home, "Home")
    object Projects : Screen("projects", Icons.Default.List, "Projects")
    object Profile : Screen("profile", Icons.Default.Person, "Profile")
    object Signup : Screen("signup", Icons.Default.PersonAdd, "Signup") // 🔥 NEW
}

@Composable
fun AppNavigation() {

    val navController = rememberNavController()
    val viewModel: ProjectViewModel = viewModel()

    val screens = listOf(
        Screen.Home,
        Screen.Projects,
        Screen.Profile
    )

    Scaffold(
        bottomBar = {

            NavigationBar {
                val currentRoute =
                    navController.currentBackStackEntryAsState().value?.destination?.route

                screens.forEach { screen ->

                    NavigationBarItem(
                        selected = currentRoute == screen.route,
                        onClick = {
                            navController.navigate(screen.route) {
                                popUpTo("home")
                                launchSingleTop = true
                            }
                        },
                        icon = {
                            Icon(screen.icon, contentDescription = screen.title)
                        },
                        label = {
                            Text(screen.title)
                        }
                    )
                }
            }
        }
    ) { padding ->

        NavHost(
            navController = navController,
            startDestination = "home",
            modifier = Modifier.padding(padding)
        ) {

            composable("home") {
                HomePage(navController)
            }

            composable("projects") {
                HomeScreen(viewModel)
            }

            composable("profile") {
                ProfileScreen()
            }

            // 🔥 NEW SIGNUP SCREEN
            composable("signup") {
                SignupScreen(navController)
            }
        }
    }
}