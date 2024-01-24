package it.polito.musaapp.Frontend

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
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.NavGraph.Companion.findStartDestination
import com.google.firebase.Firebase
import com.google.firebase.database.database
import it.polito.musaapp.Backend.MusaViewModel
import it.polito.musaapp.Screens


@Composable
fun FormExercise(navController: NavController, vm: MusaViewModel){
    Box(
        modifier= Modifier
            .fillMaxSize()
            .padding(horizontal = 10.dp, vertical = 10.dp)
    ){
        Box( //box effettivo
            modifier= Modifier
                .fillMaxSize()
                .padding(horizontal = 10.dp, vertical = 20.dp)
                .background(
                    MaterialTheme.colorScheme.primary
                )
        ){
            Column(
                verticalArrangement = Arrangement.SpaceEvenly,
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier= Modifier
                    .fillMaxSize()
                    .padding(top = 10.dp, bottom = 15.dp, start = 10.dp, end = 10.dp)
                    .background(
                        MaterialTheme.colorScheme.primary
                    )
            ){
                Icon(
                    Icons.Filled.Close,
                    contentDescription = "Close",
                    tint= MaterialTheme.colorScheme.onPrimary,
                    modifier = Modifier
                        .size(50.dp)
                        .alpha(0.8f)
                        .align(Alignment.End)
                        .clickable {
                            navController.navigate(Screens.HelpPage.name) {
                                popUpTo(navController.graph.findStartDestination().id) {
                                    saveState = true
                                }
                                launchSingleTop = true
                                restoreState = true
                            }
                        }
                )
                Text(
                    text= "Programma quando vuoi ricevere gli esercizi",
                    style = MaterialTheme.typography.headlineSmall,
                    textAlign = TextAlign.Center,
                    modifier= Modifier.fillMaxWidth()
                )
                //Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text= "Quanti giorni a settimana?",
                    style = MaterialTheme.typography.bodyMedium,
                    textAlign = TextAlign.Center,
                    modifier= Modifier.fillMaxWidth()
                )
                SelettoreCountGiorni(vm)
                //Spacer(modifier = Modifier.height(6.dp))

                Text(
                    text= "Quali giorni preferisci?",
                    style = MaterialTheme.typography.bodyMedium,
                    textAlign = TextAlign.Center,
                    modifier= Modifier.fillMaxWidth()
                )
                //Spacer(modifier = Modifier.height(8.dp))
                SelettoreGiorni(vm)
                //Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text= "Per quante settimane?",
                    style = MaterialTheme.typography.bodyMedium,
                    textAlign = TextAlign.Center,
                    modifier= Modifier.fillMaxWidth()
                )
                SelettoreCountSettimane(vm)
                //Spacer(modifier = Modifier.height(8.dp))

                Button(
                    shape = MaterialTheme.shapes.large,
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.onPrimary
                    ),
                    modifier = Modifier
                        .width(170.dp),
                    onClick = {
                        Firebase.database.getReference("ModuloEsercizi").child("Inserito").setValue(true);
                        navController.navigate(Screens.TaskListPage.name) {
                            popUpTo(navController.graph.findStartDestination().id) {
                                saveState = true
                            }
                            launchSingleTop = true
                            restoreState = true
                        }
                    }
                ){
                    Text(
                        text= "AVVIA",
                        style = MaterialTheme.typography.headlineLarge,
                        color = MaterialTheme.colorScheme.background
                    )
                }
            }

        }
    }
}

@Composable
fun SelettoreCountGiorni(vm: MusaViewModel){
    var count by remember {
        mutableIntStateOf(0)
    }
    /* val myRef = Firebase.database.getReference("ModuloEsercizi").child("NumeroGiorni")
     myRef.get().addOnSuccessListener {
         Log.d("FORM", "valori ${it.value}");
         count=it.value.toString().toInt();
     }.addOnFailureListener {
         Log.d("FORM", "Error", it);
     }*/

    Firebase.database.getReference("ModuloEsercizi").child("NumeroGiorni").setValue("$count");
    vm.setDaysEx(count)
    Row(modifier = Modifier
        .fillMaxWidth(),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically){
        Button(
            onClick = {
                if(count>0){
                    count--;
                }
            }
        ){
            Text(
                text = "<",
                style = MaterialTheme.typography.headlineLarge,
            )
        }
        Text(
            text = "$count",
            style = MaterialTheme.typography.headlineLarge,
        )
        Button(
            onClick = {
                if(count<7){
                    count++;
                }
            }
        ){
            Text(
                text = ">",
                style = MaterialTheme.typography.headlineLarge,
            )
        }
    }
}

@Composable
fun SelettoreCountSettimane(vm: MusaViewModel){
    var count by remember {
        mutableIntStateOf(0)
    }
    /* val myRef = Firebase.database.getReference("ModuloEsercizi").child("NumeroGiorni")
     myRef.get().addOnSuccessListener {
         Log.d("FORM", "valori ${it.value}");
         count=it.value.toString().toInt();
     }.addOnFailureListener {
         Log.d("FORM", "Error", it);
     }*/

    Firebase.database.getReference("ModuloEsercizi").child("NumeroSettimane").setValue("$count");
    vm.setWeeksEx(count)
    Row(modifier = Modifier
        .fillMaxWidth(),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically){

        Button(
            onClick = {
                if(count>0){
                    count--;
                }
            }
        ){
            Text(
                text = "<",
                style = MaterialTheme.typography.headlineLarge,
            )
        }
        Text(
            text = "$count",
            style = MaterialTheme.typography.headlineLarge,
        )
        Button(
            onClick = {
                count++;
            }
        ){
            Text(
                text = ">",
                style = MaterialTheme.typography.headlineLarge,
            )
        }
    }
}

@Composable
fun SelettoreGiorni(vm:MusaViewModel) {
    val selected = remember {
        mutableStateListOf<Boolean>()
    }
    val days: Array<String> = arrayOf("L", "M", "M", "G", "V", "S", "D")
    for (i in 0..6){
        selected.add(false)
        //Firebase.database.getReference("ModuloEsercizi")
           // .child("GiorniLiberi").child(days[i]).setValue(false);
    }
    vm.setDaysListEx(selected)
    Row(
        modifier = Modifier.fillMaxWidth()

    ) {
        for (i in 0..6) {
            var isCardClicked by remember { mutableStateOf(false) }
            Card(
                modifier = Modifier
                    .clickable {
                        isCardClicked = !isCardClicked
                        if (isCardClicked) {
                            Firebase.database
                                .getReference("ModuloEsercizi")
                                .child("GiorniLiberi")
                                .child(days[i])
                                .setValue(true);
                            selected[i] = true
                        } else {
                            Firebase.database
                                .getReference("ModuloEsercizi")
                                .child("GiorniLiberi")
                                .child(days[i])
                                .setValue(false);
                            selected[i] = false
                        }
                    }
                    .background(if (isCardClicked) Color.Gray else Color.White)
                    .weight(1f),
            ) {
                Text(days[i])
            }
        }
    }


}