package it.polito.musaapp.Frontend

import android.annotation.SuppressLint
import android.os.Build
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.scrollable
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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.NavGraph.Companion.findStartDestination
import com.google.firebase.Firebase
import com.google.firebase.database.database
import it.polito.musaapp.Backend.CalculateDueDates
import it.polito.musaapp.Backend.MusaViewModel
import it.polito.musaapp.Backend.PopUpCheckIntentions
import it.polito.musaapp.Backend.setRoute
import it.polito.musaapp.R
import it.polito.musaapp.Screens


@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun FormExercise(navController: NavController, vm: MusaViewModel){
    val context = LocalContext.current
    vm.setFormOpened(true)
    val popUpOpened by vm.popUpOpened.observeAsState()


    //Log.d("POPUP", popUpOpened.toString())
    if(popUpOpened == true){
        // Log.d("POPUPCHECKCALLED", popUpOpened.toString())
        PopUpCheckIntentions(
            question = "Sei sicuro di voler uscire?",
            paragraph = "Se procedi i dati inseriti\nverranno persi",
            buttonConfirm = "SI",
            buttonCancel = "NO",
            navigationConfirm = Screens.HelpPage,
            navigationCancel = Screens.FormExercise,
            navController = navController,
            vm= vm,
            numberToDelete= 0
        )
    }
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier= Modifier
            .fillMaxSize()
    ){
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 22.dp, vertical = 16.dp)
        ){
            Box(modifier = Modifier.size(35.dp))

            Image(
                painter = painterResource(id = R.drawable.loghetto),
                contentDescription = null,
                modifier = Modifier
                    .size(85.dp)
                    .alpha(0.3f)
            )

            Icon(
                painter = painterResource(id = R.drawable.info),
                contentDescription = null,
                modifier = Modifier
                    .size(40.dp)
                    .alpha(0.3f)
            )
        }
        Box( //box effettivo
            modifier= Modifier
                .fillMaxSize()
                .alpha(if (popUpOpened == true) 0.3f else 1f)
                .padding(top = 20.dp, bottom = 30.dp, start = 30.dp, end = 30.dp)
                .background(
                    color = MaterialTheme.colorScheme.primary,
                    shape = RoundedCornerShape(20.dp)
                )
                .border(
                    width = 10.dp,
                    color = MaterialTheme.colorScheme.primaryContainer,
                    shape = RoundedCornerShape(20.dp)
                )
        ){
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier= Modifier
                    .fillMaxSize()
                    .padding(top = 20.dp, bottom = 10.dp, start = 20.dp, end = 20.dp)
                    .background(
                        MaterialTheme.colorScheme.primary
                    )
            ){
                Spacer(modifier = Modifier.height(8.dp))
                Icon(
                    Icons.Filled.Close,
                    contentDescription = "Close",
                    tint= MaterialTheme.colorScheme.onPrimary,
                    modifier = Modifier
                        .size(44.dp)
                        .align(Alignment.End)
                        .clickable {
                            vm.setPopUpOpened(true)
                            /* navController.navigate(Screens.HelpPage.name) {
                                popUpTo(navController.graph.findStartDestination().id) {
                                    saveState = true
                                }
                                launchSingleTop = true
                                restoreState = true
                            }*/
                        }
                )
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier.verticalScroll(rememberScrollState()))
                {
                    Text(
                        text= "Programma quando vuoi ricevere gli esercizi",
                        style = MaterialTheme.typography.headlineSmall,
                        textAlign = TextAlign.Center,
                        modifier= Modifier.fillMaxWidth()
                    )
                    Spacer(modifier = Modifier.height(18.dp))
                    Divider(
                        color = Color(0XFFD68D02),
                        thickness = 4.dp,
                        modifier = Modifier.width(200.dp)
                    )
                    Spacer(modifier = Modifier.height(14.dp))
                    Text(
                        text= "Quanti giorni a settimana?",
                        style = MaterialTheme.typography.bodyMedium,
                        textAlign = TextAlign.Center,
                        modifier= Modifier.fillMaxWidth()
                    )
                    SelettoreCountGiorni(vm)
                    Spacer(modifier = Modifier.height(12.dp))
                    Divider(
                        color = Color(0XFFD68D02),
                        thickness = 4.dp,
                        modifier = Modifier.width(200.dp)
                    )
                    Spacer(modifier = Modifier.height(14.dp))
                    Text(
                        text= "Quali giorni preferisci?",
                        style = MaterialTheme.typography.bodyMedium,
                        textAlign = TextAlign.Center,
                        modifier= Modifier.fillMaxWidth()
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    SelettoreGiorni(vm)
                    Spacer(modifier = Modifier.height(24.dp))
                    Divider(
                        color = Color(0XFFD68D02),
                        thickness = 4.dp,
                        modifier = Modifier.width(200.dp)
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    Text(
                        text= "Per quante settimane?",
                        style = MaterialTheme.typography.bodyMedium,
                        textAlign = TextAlign.Center,
                        modifier= Modifier.fillMaxWidth()
                    )
                    SelettoreCountSettimane(vm)
                    Spacer(modifier = Modifier.height(14.dp))

                    Button(
                        shape = MaterialTheme.shapes.large,
                        colors = ButtonDefaults.buttonColors(
                            containerColor = MaterialTheme.colorScheme.onPrimary
                        ),
                        modifier = Modifier
                            .width(160.dp),
                        onClick = {
                            if(popUpOpened==false){
                                var i =0
                                for(j in 0..6){
                                    if(vm.daysListEx.value!!.get(j)==true)
                                        i++
                                }
                                if(vm.daysEx.value!=i){
                                    Toast.makeText(context, "Inserisci il numero corretto di giorni", Toast.LENGTH_SHORT).show()
                                }
                                else{
                                    Firebase.database.getReference("ModuloEsercizi").child("Inserito").setValue(true);
                                    Firebase.database.getReference("ModuloEsercizi").child("TaskCompletati").setValue(0);
                                    navController.navigate(Screens.TaskListPage.name) {
                                        popUpTo(navController.graph.findStartDestination().id) {
                                            saveState = true
                                        }
                                        launchSingleTop = true
                                        restoreState = true
                                        setRoute(Screens.TaskListPage.name)

                                    }
                                }
                            }
                        }
                    ){
                        Text(
                            text= "AVVIA",
                            style = MaterialTheme.typography.headlineLarge,
                            color = MaterialTheme.colorScheme.background
                        )
                    }
                    Spacer(modifier = Modifier.height(8.dp))
                }

            }

        }
    }
}

@Composable
fun SelettoreCountGiorni(vm: MusaViewModel){
    var count by remember {
        mutableIntStateOf(1)
    }
    val popUpOpened by vm.popUpOpened.observeAsState()
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
                if(count>1&&popUpOpened==false){
                    count--;
                }
            }
        ){
            Icon(
                painter = painterResource(id = R.drawable.frecciasx),
                contentDescription = "",
                modifier = Modifier.size(28.dp),
                tint = if(count>1&&popUpOpened==false){ MaterialTheme.colorScheme.onPrimary} else Color(0x26000000)
            )
        }
        Text(
            text = "$count",
            style = MaterialTheme.typography.headlineLarge,
            fontSize = 32.sp
        )
        Button(
            onClick = {
                if(count<7&&popUpOpened==false){
                    count++;
                }
            }
        ){
            Icon(
                painter = painterResource(id = R.drawable.frecciadx),
                contentDescription = "",
                modifier = Modifier.size(28.dp),
                tint=   if(count<7&&popUpOpened==false){ MaterialTheme.colorScheme.onPrimary} else Color(0x26000000)
            )
        }
    }
}

@Composable
fun SelettoreCountSettimane(vm: MusaViewModel){
    val count by remember {
        mutableIntStateOf(1)
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
                /*if(count>0){
                    count--;
                }*/
            }
        ){
            Icon(
                painter = painterResource(id = R.drawable.frecciasx),
                contentDescription = "",
                modifier = Modifier.size(28.dp),
                tint= Color(0x26000000)
            )
        }
        Text(
            text = "$count",
            style = MaterialTheme.typography.headlineLarge,
            fontSize = 32.sp
        )
        Button(
            onClick = {
            //count++;
            }
        ){
            Icon(
                painter = painterResource(id = R.drawable.frecciadx),
                contentDescription = "",
                modifier = Modifier.size(28.dp),
                tint= Color(0x26000000)
            )
        }
    }
}

@SuppressLint("SuspiciousIndentation")
@Composable
fun SelettoreGiorni(vm:MusaViewModel) {
    val selected = remember {
        mutableStateListOf<Boolean>()
    }
    val popUpOpened by vm.popUpOpened.observeAsState()
    val days: Array<String> = arrayOf("L", "M", "M", "G", "V", "S", "D")
    val daysDb: Array<String> = arrayOf("Lun", "Mar", "Mer", "Gio", "Ven", "Sab", "Dom")
    val daysList by vm.daysListEx.observeAsState()
    for (i in 0..6){
        selected.add(false)
        //Firebase.database.getReference("ModuloEsercizi")
           // .child("GiorniLiberi").child(days[i]).setValue(false);
    }
    vm.setDaysListEx(selected)
    Row(
        horizontalArrangement = Arrangement.SpaceEvenly,
        modifier = Modifier
            .fillMaxWidth()
            .background(
                color = MaterialTheme.colorScheme.onPrimary,
                shape = MaterialTheme.shapes.large
            )
    ) {
        for (i in 0..6) {
            /*var isCardClicked by remember { mutableStateOf(false) }
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
            }*/
            var isDayClicked by remember{ mutableStateOf(false) }
         

            Text(
                text = days[i],
                style = MaterialTheme.typography.headlineLarge,
                fontSize = 30.sp,
                modifier = Modifier
                    .clickable {
                        if(popUpOpened==false){
                            isDayClicked = !isDayClicked
                            if (isDayClicked) {
                                Firebase.database
                                    .getReference("ModuloEsercizi")
                                    .child("GiorniLiberi")
                                    .child(daysDb[i])
                                    .setValue(true);
                                selected[i] = true
                            } else {
                                Firebase.database
                                    .getReference("ModuloEsercizi")
                                    .child("GiorniLiberi")
                                    .child(daysDb[i])
                                    .setValue(false);
                                selected[i] = false
                            }
                        }
                },
                color = if (isDayClicked) MaterialTheme.colorScheme.background else Color(0x80EE9B00)
            )
        }
    }


}