package it.polito.musaapp.Frontend

import android.annotation.SuppressLint
import android.content.Context
import android.preference.PreferenceManager
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.NavGraph.Companion.findStartDestination
import com.google.firebase.Firebase
import com.google.firebase.database.database
import it.polito.musaapp.Backend.MusaViewModel
import it.polito.musaapp.R
import it.polito.musaapp.Screens


@Composable
fun HelpPage(navController: NavController, musaViewModel: MusaViewModel, applicationContext: Context){

    /*
    val sharedPreferences = remember {
        applicationContext.getSharedPreferences("com.your.app.name", Context.MODE_PRIVATE)
    }

    var isFirstOpen by remember(sharedPreferences) {
        mutableStateOf(sharedPreferences.getBoolean("first_open", true))
    }

    if (isFirstOpen) {
        // Set tutorialActive to true for the first time
        musaViewModel.setTutorial(true)

        // Update SharedPreferences to mark app as not first open
        sharedPreferences.edit().putBoolean("first_open", false).apply()
    }*/

    Firebase.database.getReference("ModuloEsercizi").child("Inserito").setValue(false);
    PageContent(musaViewModel, navController)
}

@SuppressLint("UnrememberedMutableState", "SuspiciousIndentation")
@Composable
fun PageContent(musaViewModel: MusaViewModel, navController: NavController){
    val tutorialActive by musaViewModel.tutorialActive.observeAsState()
    var tutorialStep by remember { mutableStateOf(0) }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .clickable(enabled = tutorialActive != false) {
                if (tutorialActive == true && tutorialStep >= 0) {
                    tutorialStep++
                    if (tutorialStep >= 2) {
                        musaViewModel.setTutorial(false)
                        tutorialStep = 0
                    }
                }

            }
    ){
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxSize()
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
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier
                    .fillMaxWidth()
                    .background(if (tutorialActive == true) Color.Black.copy(alpha = 0.7f) else Color.Transparent)
                    .padding(horizontal = 22.dp, vertical = 16.dp)
            ){
                Box(modifier = Modifier.size(35.dp))

                Image(
                    painter = painterResource(id = R.drawable.loghetto),
                    contentDescription = null,
                    modifier = Modifier.size(85.dp)
                )

                Icon(
                    painter = if (tutorialActive == true) painterResource(id = R.drawable.info_pieno) else painterResource(id = R.drawable.info),
                    contentDescription = null,
                    modifier = Modifier
                        .size(40.dp)
                        .clickable {
                            musaViewModel.setTutorial(true)
                        }
                )
            }
            //Spacer(modifier = Modifier.height(50.dp))
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .background(if (tutorialActive == true) Color.Black.copy(alpha = 0.7f) else Color.Transparent)
                    .padding(top = 25.dp, bottom = 40.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.SpaceEvenly)
            {

                Box(modifier = Modifier
                    .size(310.dp)
                    .graphicsLayer(
                        scaleX = if (isPulsating && tutorialActive == false) scale else 1.0f,
                        scaleY = if (isPulsating && tutorialActive == false) scale else 1.0f
                    )
                    .clip(CircleShape)
                    .background(
                        color = MaterialTheme.colorScheme.primary,
                        shape = CircleShape
                    )
                    .border(
                        width = 10.dp,
                        color = if (tutorialActive == true && tutorialStep == 0) {
                            MaterialTheme.colorScheme.primaryContainer
                        } else if (tutorialActive == true && tutorialStep != 0) {
                            Color(0xFF3d1f01)
                        } else MaterialTheme.colorScheme.primaryContainer,

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
                        enabled = tutorialActive != true,
                        modifier = Modifier
                            .size(310.dp)  // Imposta un valore fisso per larghezza e altezza
                            .graphicsLayer(
                                scaleX = if (isPulsating && tutorialActive == false) scale else 1.0f,
                                scaleY = if (isPulsating && tutorialActive == false) scale else 1.0f
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
                        Text(
                            modifier = Modifier.offset(y = (-10).dp),
                            text= "Inizia",
                            style = MaterialTheme.typography.titleLarge
                        )

                    }
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .background(
                                if (tutorialActive == true && tutorialStep == 0) {
                                    Color.Transparent
                                } else if (tutorialActive == true && tutorialStep != 0) {
                                    Color.Black.copy(alpha = 0.7f)
                                } else Color.Transparent
                            )
                    )
                }

                Text(
                    text="Crea un nuovo progetto personale",
                    style = MaterialTheme.typography.headlineSmall,
                    textDecoration = TextDecoration.Underline,
                    textAlign = TextAlign.Center,
                    color = if (tutorialActive == true && tutorialStep == 1) {
                        MaterialTheme.colorScheme.onPrimary
                    } else if (tutorialActive == true && tutorialStep != 1) {
                        Color(0xFF00090d)
                    } else MaterialTheme.colorScheme.onPrimary,
                    modifier = Modifier
                        .padding(horizontal = 60.dp)
                        .background(
                            color = if (tutorialActive == true && tutorialStep == 1) MaterialTheme.colorScheme.background else Color.Transparent,
                            shape = RoundedCornerShape(50.dp)
                        )
                        .offset(y = (-5).dp)
                        .clickable(enabled = tutorialActive != true) {
                            if(tutorialActive == false){}
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
        //BOX MESSAGGIO 1
        if (tutorialActive==true && tutorialStep==0)
            Box(modifier = Modifier.fillMaxSize()) {
                Box(
                    modifier = Modifier
                        .height(160.dp)
                        .padding(horizontal = 22.dp)
                        .align(Alignment.TopEnd)
                        .offset(y = (118).dp)
                ) {
                    Text(
                        text = "Clicca qui per programmare un piano\ndi esercizi per mantenere vivo\nil tuo flusso creativo",
                        style = MaterialTheme.typography.bodyMedium,
                        fontSize = 19.sp,
                        color = Color.White,
                        textAlign = TextAlign.End,
                        modifier = Modifier
                            .align(Alignment.TopEnd)
                    )
                    Icon(
                        painter = painterResource(id = R.drawable.freccia_info1),
                        contentDescription = null,
                        tint = Color.White,
                        modifier = Modifier
                            .width(110.dp)
                            .offset(x = (-50).dp, y = (0).dp)
                            .align(Alignment.BottomEnd)
                    )
                }
            }

        //BOX MESSAGGIO 2
        if (tutorialActive==true && tutorialStep==1)
            Box(modifier = Modifier.fillMaxSize()){
                Box(modifier = Modifier
                    .height(160.dp)
                    .padding(horizontal = 10.dp)
                    .align(Alignment.CenterStart)
                    .offset(x = 20.dp, y = (120).dp)
                ){
                    Text(
                        text = "Clicca qui per inserire un progetto\npersonale e ricevere stimoli creativi\nsu misura per il tuo lavoro",
                        style = MaterialTheme.typography.bodyMedium,
                        fontSize = 19.sp,
                        color = Color.White,
                        textAlign = TextAlign.Start,
                        modifier = Modifier
                            .align(Alignment.TopStart)
                    )
                    Icon(
                        painter = painterResource(id = R.drawable.freccia_info1),
                        contentDescription = null,
                        tint = Color.White,
                        modifier = Modifier
                            .width(100.dp)
                            .offset(x = (40).dp, y = (-5).dp)
                            .graphicsLayer(scaleX = -1f)
                            .rotate(-15f)
                            .align(Alignment.BottomStart)
                    )
                }
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