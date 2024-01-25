package it.polito.musaapp.Frontend

import android.annotation.SuppressLint
import android.content.Context
import android.util.Log
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.paddingFromBaseline
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ButtonElevation
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collection.mutableVectorOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontVariation
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.em
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.NavGraph.Companion.findStartDestination
import com.google.firebase.Firebase
import com.google.firebase.database.database
import it.polito.musaapp.Backend.MusaViewModel
import it.polito.musaapp.Screens
import it.polito.musaapp.ui.theme.bangers
import java.util.Vector


@Composable
fun HelpPage(navController: NavController, musaViewModel: MusaViewModel,
             applicationContext: Context){
    //Text("HelpPage")
    Firebase.database.getReference("ModuloEsercizi").child("Inserito").setValue(false);
    PageContent(musaViewModel, navController)
}

@SuppressLint("UnrememberedMutableState")
@Composable
fun PageContent(musaViewModel: MusaViewModel, navController: NavController){
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(top=20.dp)
            .background(MaterialTheme.colorScheme.background)
    ){
        var isPulsating by remember { mutableStateOf(true) }

        // Pulsating animation
        val infiniteTransition = rememberInfiniteTransition()
        val scale by infiniteTransition.animateFloat(
            initialValue = 1.0f,
            targetValue = 1.03f,
            animationSpec = infiniteRepeatable(
                animation = tween(durationMillis = 1000, easing = FastOutSlowInEasing),
                repeatMode = RepeatMode.Reverse), label = ""
        )
        Column(
            modifier = Modifier
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center)
        {
            Box(modifier = Modifier
                .size(300.dp)
                .graphicsLayer(
                    scaleX = if (isPulsating) scale else 1.0f,
                    scaleY = if (isPulsating) scale else 1.0f
                )
                .clip(CircleShape)
                .background(
                    color = MaterialTheme.colorScheme.primary,
                    shape = CircleShape
                )
                .border(
                    width = 10.dp,
                    color = MaterialTheme.colorScheme.primaryContainer,
                    shape = CircleShape
                ),
                contentAlignment = Alignment.Center
            )
            {
                Button(
                    onClick = {
                        navController.navigate(Screens.FormExercise.name) {
                            popUpTo(navController.graph.findStartDestination().id) {
                                saveState = true
                            }
                            launchSingleTop = true
                            restoreState = true
                        }
                    },
                    modifier = Modifier
                        .size(300.dp)  // Imposta un valore fisso per larghezza e altezza
                        .graphicsLayer(
                            scaleX = if (isPulsating) scale else 1.0f,
                            scaleY = if (isPulsating) scale else 1.0f
                        )
                        .clip(CircleShape)
                        .background(
                            color = MaterialTheme.colorScheme.primary,
                            shape = CircleShape
                        )
                        .border(
                            width = 12.dp,
                            color = MaterialTheme.colorScheme.primaryContainer,
                            shape = CircleShape
                        )

                ){
                    Box(modifier = Modifier.padding(bottom = 20.dp)){
                        Text(
                            text= "Aiuto!",
                            style = MaterialTheme.typography.titleLarge
                            )
                    }
                }
            }
            Spacer(modifier = Modifier.height(40.dp))
            Text(
                text="Crea un nuovo progetto personale",
                style = MaterialTheme.typography.headlineMedium,
                textDecoration = TextDecoration.Underline,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .padding(horizontal = 50.dp)
                    .clickable {
                        navController.navigate(Screens.ProjectPage.name) {
                            popUpTo(navController.graph.findStartDestination().id) {
                                saveState = true
                            }
                            launchSingleTop = true
                            restoreState = true
                        }
                    },
            )
        }
    }
}

/*@Composable
fun PopUp(vm: MusaViewModel, applicationContext: Context){

   // val value by vm.popUpHelp.
   if(vm.popUpHelp.value == true)
   {

        Box(
            modifier= Modifier
                .fillMaxSize()
                .padding(horizontal = 10.dp, vertical = 10.dp)
                .background(
                    Color.Gray
                )
        ){
            Column(
                modifier= Modifier.fillMaxSize()
            ){
                Text(
                    text= "Quanti giorni a settimana vuoi lavorare?",
                    modifier= Modifier.fillMaxWidth()
                )
                SelettoreCountGiorni()
                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text= "Quali giorni preferisci lavorare?",
                    modifier= Modifier.fillMaxWidth()
                )
                SelettoreGiorni()
                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text= "Per quante settimane vuoi avere degli esercizi per la tua creatività?",
                    modifier= Modifier.fillMaxWidth()
                )
                SelettoreCountSettimane()
                Spacer(modifier = Modifier.height(8.dp))

                Button(
                    onClick = {
                      vm.unsetPopUpHelp()
                        Log.d("POPUP", "${vm.popUpHelp.value}")
                    }
                ){
                    Text("Avvia")
                }
            }
        }
    }
}
*/