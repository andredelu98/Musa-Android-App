package it.polito.musaapp.Frontend

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.NavGraph.Companion.findStartDestination
import com.google.firebase.Firebase
import com.google.firebase.database.database
import it.polito.musaapp.Backend.DeletePlanExercise
import it.polito.musaapp.Backend.MusaViewModel
import it.polito.musaapp.Screens

@Composable
fun ModifyPlanExercise(navController: NavController, vm: MusaViewModel){
    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        Text("Modifica piano di esercizi")

        Button(
            onClick={
                navController.navigate(Screens.ModifyExercise.name) {
                    popUpTo(navController.graph.findStartDestination().id) {
                        saveState = true
                    }
                    launchSingleTop = true
                    restoreState = true
                }
            }
        )
        {
            Text("Modifica la durata del piano di esercizi")
        }


        Button(
            onClick={
                navController.navigate(Screens.ModifyExercise.name) {
                    popUpTo(navController.graph.findStartDestination().id) {
                        saveState = true
                    }
                    launchSingleTop = true
                    restoreState = true
                }
            }
        )
        {
            Text("Modifica giorni e orari delle notifiche")
        }

        Button(
            onClick={
                DeletePlanExercise(vm)
                navController.navigate(Screens.HelpPage.name) {
                    popUpTo(navController.graph.findStartDestination().id) {
                        saveState = true
                    }
                    launchSingleTop = true
                    restoreState = true
                }
            }
        )
        {
            Text("Elimina piano di esercizi")
        }
    }
}

