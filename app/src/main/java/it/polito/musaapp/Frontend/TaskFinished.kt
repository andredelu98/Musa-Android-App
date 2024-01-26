package it.polito.musaapp.Frontend

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.NavGraph.Companion.findStartDestination
import com.google.firebase.Firebase
import com.google.firebase.database.database
import it.polito.musaapp.Backend.MusaViewModel
import it.polito.musaapp.Backend.setRoute
import it.polito.musaapp.Screens
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


@Composable
fun TaskFinished(navController: NavController, viewModel: MusaViewModel){
    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        Text("Congratulazioni, hai finito il tuo piano di esercizi!")
        setRoute(Screens.HelpPage.name)
        Firebase.database.getReference("ModuloEsercizi").child("TaskCompletati")
            .setValue(0);
        navController.navigate(Screens.HelpPage.name) {
            popUpTo(navController.graph.findStartDestination().id) {
                saveState = true
            }
            launchSingleTop = true
            restoreState = true
        }

    }
}

