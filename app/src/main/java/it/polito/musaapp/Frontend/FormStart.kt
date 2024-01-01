package it.polito.musaapp.Frontend

import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.NavGraph.Companion.findStartDestination
import com.google.firebase.Firebase
import com.google.firebase.database.database
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
            Firebase.database.getReference("UtenteGiaRegistrato").setValue(true)
        })
        {
            Text("INVIA")
        }
    }

}