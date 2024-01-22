package it.polito.musaapp.Backend

import android.annotation.SuppressLint
import android.content.Context
import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
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
import com.google.firebase.Firebase
import com.google.firebase.database.database
import it.polito.musaapp.Frontend.FormExercise
import it.polito.musaapp.Frontend.FormStart
import it.polito.musaapp.Frontend.HelpPage
import it.polito.musaapp.Frontend.ModifyExercise
import it.polito.musaapp.Frontend.ModifyProfile
import it.polito.musaapp.Frontend.ProfilePage
import it.polito.musaapp.Frontend.ProjectPage
import it.polito.musaapp.Frontend.TaskFinished
import it.polito.musaapp.Frontend.TaskPage
import it.polito.musaapp.Frontend.WelcomePage
import it.polito.musaapp.Screens

@SuppressLint("SuspiciousIndentation")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppNavigation(vm: MusaViewModel, applicationContext: Context) {

    val navController = rememberNavController()
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination


    Scaffold(
        bottomBar = {
            if (navBackStackEntry?.destination?.route != (Screens.WelcomePage.name)
                && navBackStackEntry?.destination?.route != (Screens.FormStart.name)){
                NavigationBar(
                    modifier = Modifier
                        .background(MaterialTheme.colorScheme.background)
                        .clip(shape = RoundedCornerShape(15.dp, 20.dp, 0.dp, 0.dp)),
                    containerColor = MaterialTheme.colorScheme.surface,
                    contentColor = MaterialTheme.colorScheme.onSurface,
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
        }
    ) {

        paddingValues ->
        NavHost(
            navController = navController,
            startDestination = Screens.WelcomePage.name,
            modifier = Modifier.padding(paddingValues)
        ) {
            this.composable(route = Screens.HelpPage.name) {
                HelpPage(navController, vm, applicationContext)
            }
            composable(route = Screens.ProjectPage.name)
            {
                ProjectPage(navController)
                //Access(auth,auth.currentUser!!, login, navController)
            }
            composable(route = Screens.ProfilePage.name)
            {
                //greetings(auth, navController)
                ProfilePage(navController = navController, vm)
            }
            composable(route = Screens.TaskPage.name)
            {
                //greetings(auth, navController)
                TaskPage(navController = navController,vm)
            }
            composable(route = Screens.FormStart.name)
            {
                //greetings(auth, navController)
                FormStart(navController = navController, vm)
            }
            composable(route = Screens.FormExercise.name)
            {
                //greetings(auth, navController)
                FormExercise(navController = navController, vm)
            }
            composable(route = Screens.ModifyProfile.name)
            {
                //greetings(auth, navController)
                ModifyProfile(navController = navController, vm)
            }
            composable(route = Screens.ModifyExercise.name)
            {
                //greetings(auth, navController)
                ModifyExercise(navController = navController, vm)
            }

            composable(route=Screens.WelcomePage.name){
                WelcomePage(navController = navController, vm)
            }

            composable(route=Screens.CalendarClass.name){
                CalendarActivity()
            }
            composable(route=Screens.TaskFinished.name){
                TaskFinished(navController, vm)
            }
        }
    }
}