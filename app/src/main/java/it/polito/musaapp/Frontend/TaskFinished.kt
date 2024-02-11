package it.polito.musaapp.Frontend

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.NavGraph.Companion.findStartDestination
import com.google.firebase.Firebase
import com.google.firebase.database.database
import it.polito.musaapp.Backend.DeletePlanExercise
import it.polito.musaapp.Backend.MusaViewModel
import it.polito.musaapp.Backend.setRoute
import it.polito.musaapp.R
import it.polito.musaapp.Screens
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


@Composable
fun TaskFinished(navController: NavController, viewModel: MusaViewModel){
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier.fillMaxSize()
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally){

            Icon(
                painter = painterResource(id = R.drawable.successful),
                contentDescription = "",
                tint = MaterialTheme.colorScheme.onPrimary,
                modifier = Modifier.size(100.dp)
            )
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = "Congratulazioni, hai finito il tuo piano di esercizi!",
                style = MaterialTheme.typography.headlineLarge,
                textAlign = TextAlign.Center
            )
        }





        LaunchedEffect(Unit) {
            // Aggiorna il messaggio dopo un ritardo di 2 secondi (puoi personalizzare il ritardo)
            delay(3200)
            DeletePlanExercise(viewModel)
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
}


