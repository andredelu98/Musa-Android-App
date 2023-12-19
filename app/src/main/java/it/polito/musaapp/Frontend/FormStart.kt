package it.polito.musaapp.Frontend

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.NavGraph.Companion.findStartDestination
import it.polito.musaapp.Screens

@Composable
fun FormStart(navController: NavController){
    Column(
        modifier = Modifier.fillMaxWidth()
    ){
        Text("FormStart")
        Button(onClick = {
            navController.navigate(Screens.HelpPage.name) {
                popUpTo(navController.graph.findStartDestination().id) {
                    saveState = true
                }
                launchSingleTop = true
                restoreState = true

            }
        })
        {
            Text("INVIA")
        }
    }

}