package it.polito.musaapp.Frontend


import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
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
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.NavGraph.Companion.findStartDestination
import com.google.firebase.Firebase
import com.google.firebase.database.database
import it.polito.musaapp.Backend.MusaViewModel
import it.polito.musaapp.Backend.RefreshVariablesTask
import it.polito.musaapp.Screens

@Composable
fun ModifyExercise(navController: NavController, vm: MusaViewModel){
    Box(
        modifier= Modifier
            .fillMaxSize()
            .padding(horizontal = 10.dp, vertical = 10.dp)
    ){
        Column(
            modifier= Modifier.fillMaxSize().padding(horizontal = 10.dp, vertical = 20.dp).background(
                Color.LightGray)
        ){
            Icon(
                Icons.Filled.Close,
                contentDescription = "Close",
                tint= Color.Black,
                modifier = Modifier
                    .size(70.dp)
                    .padding(12.dp)
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
                text= "Quanti giorni a settimana vuoi lavorare?",
                modifier= Modifier.fillMaxWidth()
            )
            SelettoreCountGiorniModify(vm)
            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text= "Quali giorni preferisci lavorare?",
                modifier= Modifier.fillMaxWidth()
            )
            SelettoreGiorniModify(vm)
            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text= "Per quante settimane vuoi avere degli esercizi per la tua creatività?",
                modifier= Modifier.fillMaxWidth()
            )
            SelettoreCountSettimaneModify(vm)
            Spacer(modifier = Modifier.height(8.dp))

            Button(
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
                Text("Avvia")
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
    Row(){

        Button(
            onClick = {
                if(count>0){
                    count--;
                    changed=true
                }
            }
        ){
            Text("-")
        }

        Text("${vm.daysEx.value}")

        Button(
            onClick = {
                if(count<7){
                    count++;
                    changed =true
                }
            }
        ){
            Text("+")
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

    Row(){

        Button(
            onClick = {
                if(count>0){
                    count--;
                    changed=true
                }
            }
        ){
            Text("-")
        }

        Text(count.toString())

        Button(
            onClick = {
                count++;
                changed=true
            }
        ){
            Text("+")
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
    val days: Array<String> = arrayOf("Lun", "Mar", "Mer", "Gio", "Ven", "Sab", "Dom")
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
        modifier = Modifier.fillMaxWidth()

    ) {
        for (i in 0..6) {
            var isCardClicked by remember { mutableStateOf(vm.daysListEx.value?.get(i)!!) }
            Card(
                modifier = Modifier
                    .clickable {
                        isCardClicked = !isCardClicked
                        if (isCardClicked) {
                            Firebase.database.getReference("ModuloEsercizi")
                                .child("GiorniLiberi").child(days[i]).setValue(true);
                            selected[i]=true
                        } else {
                            Firebase.database.getReference("ModuloEsercizi")
                                .child("GiorniLiberi").child(days[i]).setValue(false);
                            selected[i]=false
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