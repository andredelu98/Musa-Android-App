package it.polito.musaapp.Backend

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import it.polito.musaapp.Frontend.FormStart
import it.polito.musaapp.Frontend.HelpPage
import it.polito.musaapp.Frontend.ProfilePage
import it.polito.musaapp.Frontend.ProjectPage
import it.polito.musaapp.Frontend.TaskPage
import it.polito.musaapp.Screens

@SuppressLint("SuspiciousIndentation")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppNavigation() {

    val navController = rememberNavController()
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination

    Scaffold(
        bottomBar = {
                NavigationBar(
                    modifier = Modifier
                        .background(Color(0xFF101010))
                        .padding(PaddingValues(horizontal = 20.dp, vertical = 8.dp))
                        .clip(shape = RoundedCornerShape(50.dp)),
                    containerColor = Color.DarkGray,
                    contentColor = Color.White,
                ) {

                    val navBackStackEntry by navController.currentBackStackEntryAsState()
                    val currentDestination = navBackStackEntry?.destination

                    listOfNavItems.forEach { navItem ->
                        NavigationBarItem(
                            selected = currentDestination?.hierarchy?.any { it.route == navItem.route } == true,
                            onClick = {
                                navController.navigate(navItem.route) {
                                    popUpTo(navController.graph.findStartDestination().id) {
                                        saveState = true
                                    }
                                    launchSingleTop = true
                                    restoreState = true
                                }
                            },
                            icon = {
                                Icon(
                                    imageVector = navItem.icon,
                                    contentDescription = null,
                                    tint = Color.White
                                )
                            },
                            label = {
                                Text(
                                    text = navItem.label,
                                    color = Color.White
                                )
                            },
                            modifier = Modifier.fillMaxHeight()
                        )

                    }
                }
        }
    ) { paddingValues ->
        NavHost(
            navController = navController,
            startDestination = Screens.FormStart.name,
            modifier = Modifier.padding(paddingValues)
        ) {
            this.composable(route = Screens.HelpPage.name) {
                HelpPage(navController)
            }
            composable(route = Screens.ProjectPage.name)
            {
                ProjectPage(navController)
                //Access(auth,auth.currentUser!!, login, navController)
            }
            composable(route = Screens.ProfilePage.name)
            {
                //greetings(auth, navController)
                ProfilePage(navController = navController)
            }
            composable(route = Screens.TaskPage.name)
            {
                //greetings(auth, navController)
                TaskPage(navController = navController)
            }
            composable(route = Screens.FormStart.name)
            {
                //greetings(auth, navController)
                FormStart(navController = navController)
            }
        }
    }
}