package it.polito.musaapp.Backend

import android.annotation.SuppressLint
import android.content.Context
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemColors
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
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
import it.polito.musaapp.Frontend.ModifyExerciseEmpty
import it.polito.musaapp.Frontend.ModifyPlanExercise
import it.polito.musaapp.Frontend.ModifyProfile
import it.polito.musaapp.Frontend.ModifyProject
import it.polito.musaapp.Frontend.NewProject
import it.polito.musaapp.Frontend.ProfilePage
import it.polito.musaapp.Frontend.ProjectPage
import it.polito.musaapp.Frontend.SingleProjectPage
import it.polito.musaapp.Frontend.StoricoProgetti
import it.polito.musaapp.Frontend.TaskFinished
import it.polito.musaapp.Frontend.TaskListPage
import it.polito.musaapp.Frontend.TaskPage
import it.polito.musaapp.Frontend.TaskReference
import it.polito.musaapp.Frontend.WelcomePage
import it.polito.musaapp.Screens

@RequiresApi(Build.VERSION_CODES.O)
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
                    containerColor = MaterialTheme.colorScheme.onPrimary               //COLORE NON MOSTRA QUESTO
                ) {
                    val navBackStackEntry by navController.currentBackStackEntryAsState()
                    val currentDestination = navBackStackEntry?.destination

                    listOfNavItems.forEach { navItem ->
                        NavigationBarItem(
                            selected = currentDestination?.hierarchy?.any { it.route == navItem.route } == true,
                            colors = NavigationBarItemDefaults.colors(
                                indicatorColor = MaterialTheme.colorScheme.onPrimary
                            ),
                            onClick = {
                                listOfNavItems.forEach { it.selected = it == navItem }
                                navController.navigate(navItem.route) {
                                    popUpTo(navController.graph.findStartDestination().id) {
                                        saveState = true
                                    }
                                    launchSingleTop = true
                                    restoreState = true
                                }
                            },
                            icon = {
                                Column(
                                    modifier = Modifier
                                        .fillMaxHeight()
                                        .padding(0.dp, 5.dp),
                                    verticalArrangement = Arrangement.SpaceEvenly,
                                    horizontalAlignment = Alignment.CenterHorizontally
                                ) {
                                    Image(
                                        painter = painterResource(id = if (navItem.selected) navItem.iconId_selected else navItem.iconId_unselected),
                                        contentDescription = null,
                                        modifier = Modifier.size(40.dp) // Puoi regolare la dimensione secondo le tue esigenze
                                    )
                                    Text(
                                        text = navItem.label,
                                        style = MaterialTheme.typography.labelSmall,
                                        color = MaterialTheme.colorScheme.background
                                    )
                                }
                            },
                            modifier = Modifier
                                .fillMaxSize()
                                .alpha(1f)
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
                ProjectPage(navController, vm)
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
            composable(route = Screens.ModifyExerciseEmpty.name)
            {
                //greetings(auth, navController)
                ModifyExerciseEmpty(navController = navController, vm)
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
            composable(route=Screens.TaskListPage.name){
                TaskListPage(navController, vm)
            }
            composable(route=Screens.TaskReference.name){
                TaskReference(navController, vm)
            }
            composable(route=Screens.ModifyPlanExercise.name){
                ModifyPlanExercise(navController = navController, vm)
            }
            composable(route=Screens.NewProject.name){
                NewProject(navController = navController, vm)
            }
            composable(route=Screens.SinglePageProject.name){
                SingleProjectPage(navController = navController, vm)
            }
            composable(route=Screens.ModifyProject.name){
                ModifyProject(navController = navController, vm)
            }
            composable(route=Screens.StoricoProgetti.name){
                StoricoProgetti(navController = navController, vm)
            }
        }
    }
}