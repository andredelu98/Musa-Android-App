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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.Divider
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
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.NavGraph.Companion.findStartDestination
import com.google.firebase.Firebase
import com.google.firebase.database.database
import it.polito.musaapp.Backend.MusaViewModel
import it.polito.musaapp.Backend.RefreshVariablesTask
import it.polito.musaapp.R
import it.polito.musaapp.Screens

@Composable
fun ModifyExercise(navController: NavController, vm: MusaViewModel){
    Box(
        modifier= Modifier
            .fillMaxSize()
            .padding(top = 120.dp, bottom = 20.dp, start = 20.dp, end = 20.dp)
    ){
        Box( //box effettivo
            modifier= Modifier
                .fillMaxSize()
                .padding(horizontal = 10.dp, vertical = 10.dp)
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
                    .padding(top = 15.dp, bottom = 10.dp, start = 20.dp, end = 20.dp)
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
                Spacer(modifier = Modifier.height(14.dp))
                Divider(
                    color = Color(0XFFD68D02),
                    thickness = 4.dp,
                    modifier = Modifier.width(200.dp)
                )
                Spacer(modifier = Modifier.height(10.dp))
                Text(
                    text= "Quanti giorni a settimana?",
                    style = MaterialTheme.typography.bodyMedium,
                    textAlign = TextAlign.Center,
                    modifier= Modifier.fillMaxWidth()
                )
                SelettoreCountGiorniModify(vm)
                Spacer(modifier = Modifier.height(8.dp))
                Divider(
                    color = Color(0XFFD68D02),
                    thickness = 4.dp,
                    modifier = Modifier.width(200.dp)
                )
                Spacer(modifier = Modifier.height(10.dp))
                Text(
                    text= "Quali giorni preferisci?",
                    style = MaterialTheme.typography.bodyMedium,
                    textAlign = TextAlign.Center,
                    modifier= Modifier.fillMaxWidth()
                )
                Spacer(modifier = Modifier.height(12.dp))
                SelettoreGiorniModify(vm)
                Spacer(modifier = Modifier.height(20.dp))
                Divider(
                    color = Color(0XFFD68D02),
                    thickness = 4.dp,
                    modifier = Modifier.width(200.dp)
                )
                Spacer(modifier = Modifier.height(12.dp))
                Text(
                    text= "Per quante settimane?",
                    style = MaterialTheme.typography.bodyMedium,
                    textAlign = TextAlign.Center,
                    modifier= Modifier.fillMaxWidth()
                )
                SelettoreCountSettimaneModify(vm)
                Spacer(modifier = Modifier.height(12.dp))

                Button(
                    shape = MaterialTheme.shapes.large,
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.onPrimary
                    ),
                    modifier = Modifier
                        .width(160.dp),
                    onClick = {
                        //RefreshVariablesTask()
                        navController.navigate(Screens.TaskPage.name) {
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
fun SelettoreCountGiorniModify(vm: MusaViewModel){
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
    var changed by remember {
        mutableStateOf(false)
    }
    /* val myRef = Firebase.database.getReference("ModuloEsercizi").child("NumeroGiorni")
     myRef.get().addOnSuccessListener {
         Log.d("FORM", "valori ${it.value}");
         count=it.value.toString().toInt();
     }.addOnFailureListener {
         Log.d("FORM", "Error", it);
     }*/
    if(!changed){
        count=vm.daysEx.value!!
    }
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
                    changed=true
                }
            }
        ){
            Icon(
                painter = painterResource(id = R.drawable.frecciasx),
                contentDescription = "",
                modifier = Modifier.size(28.dp)
            )
        }

        Text(
            text = "${vm.daysEx.value}",
            style = MaterialTheme.typography.headlineLarge,
            fontSize = 32.sp
        )

        Button(
            onClick = {
                if(count<7){
                    count++;
                    changed =true
                }
            }
        ){
            Icon(
                painter = painterResource(id = R.drawable.frecciadx),
                contentDescription = "",
                modifier = Modifier.size(28.dp),
            )
        }
    }
}

@Composable
fun SelettoreCountSettimaneModify(vm: MusaViewModel){
    var count by remember {
        mutableIntStateOf(0)
    }
    var changed by remember {
        mutableStateOf(false)
    }
    /* val myRef = Firebase.database.getReference("ModuloEsercizi").child("NumeroGiorni")
     myRef.get().addOnSuccessListener {
         Log.d("FORM", "valori ${it.value}");
         count=it.value.toString().toInt();
     }.addOnFailureListener {
         Log.d("FORM", "Error", it);
     }*/
    if(!changed){
        count=vm.weeksEx.value!!
    }
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
                    changed=true
                }
            }
        ){
            Icon(
                painter = painterResource(id = R.drawable.frecciasx),
                contentDescription = "",
                modifier = Modifier.size(28.dp)
            )
        }

        Text(
            text = count.toString(),
            style = MaterialTheme.typography.headlineLarge,
            fontSize = 32.sp
            )

        Button(
            onClick = {
                count++;
                changed=true
            }
        ){
            Icon(
                painter = painterResource(id = R.drawable.frecciadx),
                contentDescription = "",
                modifier = Modifier.size(28.dp)
            )
        }
    }
}

@Composable
fun SelettoreGiorniModify(vm: MusaViewModel) {
    var selected = remember {
        mutableStateListOf<Boolean>()
    }
    var changed by remember {
        mutableStateOf(false)
    }
    val days: Array<String> = arrayOf("L", "M", "M", "G", "V", "S", "D")
    for (i in 0..6){
        selected.add(false)
        //Firebase.database.getReference("ModuloEsercizi")
        // .child("GiorniLiberi").child(days[i]).setValue(false);
    }
    if(!changed){
        selected= vm.getDaysListEx() as SnapshotStateList<Boolean>
        changed=true
    }
    vm.setDaysListEx(selected)
    Row(
        horizontalArrangement = Arrangement.SpaceEvenly,
        modifier = Modifier
            .fillMaxWidth()
            .background(
                color = MaterialTheme.colorScheme.onPrimary,
                shape = MaterialTheme.shapes.large)
    ) {
        for (i in 0..6) {
            var isDayClicked by remember { mutableStateOf(vm.daysListEx.value?.get(i)!!) }
            Text(
                text = days[i],
                style = MaterialTheme.typography.headlineLarge,
                fontSize = 30.sp,
                modifier = Modifier
                    .clickable {
                        isDayClicked = !isDayClicked
                        if (isDayClicked) {
                            Firebase.database.getReference("ModuloEsercizi")
                                .child("GiorniLiberi").child(days[i]).setValue(true);
                            selected[i]=true
                        } else {
                            Firebase.database.getReference("ModuloEsercizi")
                                .child("GiorniLiberi").child(days[i]).setValue(false);
                            selected[i]=false
                        }
                    },
                color = if (isDayClicked) MaterialTheme.colorScheme.background else Color(0x80EE9B00)
            )
        }
    }


}