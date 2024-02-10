package it.polito.musaapp.Frontend


import android.util.Log
import android.widget.Toast
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
import androidx.compose.runtime.livedata.observeAsState
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
import androidx.compose.ui.platform.LocalContext
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
import it.polito.musaapp.Backend.RefreshVariablesTask
import it.polito.musaapp.Backend.setRoute
import it.polito.musaapp.R
import it.polito.musaapp.Screens


var dayPerWeek: Int=-1
var weeks: Int = -1
var daysL: MutableList<Boolean> = mutableListOf(false, false, false, false, false, false, false)

@Composable
fun ModifyExercise(navController: NavController, vm: MusaViewModel) {
    val context = LocalContext.current

    val myRefTask = Firebase.database.getReference("ModuloEsercizi")
    var taskInserito by remember {
        mutableStateOf(false)
    }
    myRefTask.child("Inserito").get().addOnSuccessListener {
        taskInserito = it.value.toString().toBoolean()
    }.addOnFailureListener {
        Log.d("FORM", "Error", it);
    }

    dayPerWeek = vm.daysEx.value!!
    weeks = vm.weeksEx.value!!
    daysL = vm.getDaysListEx()

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxSize()
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 22.dp, bottom = 0.dp, start = 22.dp, end = 22.dp)
        ){
            Icon(
                painter = painterResource(id = R.drawable.back_arrow),
                contentDescription = null,
                modifier = Modifier
                    .size(35.dp)
                    .clickable {
                        navController.navigate(Screens.ModifyPlanExercise.name) {
                            popUpTo(navController.graph.findStartDestination().id) {
                                saveState = true
                            }
                            launchSingleTop = true
                            restoreState = true
                        }
                    }
            )
            Box(modifier = Modifier.size(35.dp))

            Box(modifier = Modifier.size(35.dp))
        }
        Text(
            text = "Modifica durata del piano",
            style = MaterialTheme.typography.headlineMedium,
            textAlign = TextAlign.Center,
            modifier = Modifier.fillMaxWidth().padding(bottom = 20.dp)
        )

        Column(
            verticalArrangement = Arrangement.SpaceBetween,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .height(560.dp)
                .padding(top = 40.dp, bottom = 10.dp, start = 50.dp, end = 50.dp)
        )
        {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.height(140.dp)
            ) {
                Text(
                    text = "Quanti giorni a settimana?",
                    style = MaterialTheme.typography.bodyMedium,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.fillMaxWidth()
                )
                SelettoreCountGiorniModify(vm)

                Divider(
                    color = Color(0x1A001219),
                    thickness = 4.dp,
                    modifier = Modifier.width(200.dp)
                )
            }

            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.height(160.dp)
            ) {
                Text(
                    text = "Quali giorni preferisci?",
                    style = MaterialTheme.typography.bodyMedium,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.fillMaxWidth()
                )
                SelettoreGiorniModify(vm)
                Divider(
                    color = Color(0x1A001219),
                    thickness = 4.dp,
                    modifier = Modifier.width(200.dp)
                )
            }

            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.height(150.dp)
            ) {
                Text(
                    text = "Per quante settimane?",
                    style = MaterialTheme.typography.bodyMedium,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.fillMaxWidth()
                )
                SelettoreCountSettimaneModify(vm)
                Spacer(modifier = Modifier.height(20.dp))
            }

        }

            Button(
                shape = MaterialTheme.shapes.large,
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer
                ),
                modifier = Modifier
                    .width(180.dp)
                    .padding(top = 8.dp),
                onClick = {
                    //RefreshVariablesTask()

                    var i = 0
                    for (j in 0..6) {
                        if (daysL.get(j) == true)
                            i++
                    }
                    if (dayPerWeek != i) {
                        Toast.makeText(
                            context,
                            "Inserisci il numero corretto di giorni",
                            Toast.LENGTH_SHORT
                        ).show()
                    } else {
                        SaveChanges(vm)
                        Firebase.database.getReference("ModuloEsercizi").child("Inserito")
                            .setValue(true);
                        Firebase.database.getReference("ModuloEsercizi")
                            .child("TaskCompletati").setValue(0);
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
            ) {
                Text(
                    text = "SALVA",
                    style = MaterialTheme.typography.headlineLarge,
                    color = MaterialTheme.colorScheme.background
                )
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
    dayPerWeek=count
    //  Firebase.database.getReference("ModuloEsercizi").child("NumeroGiorni").setValue("$count");
    // vm.setDaysEx(count)
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
            },
            colors = ButtonDefaults.buttonColors(
                containerColor = MaterialTheme.colorScheme.background
            )
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
                if(count<7){
                    count++;
                    changed =true
                }
            },
            colors = ButtonDefaults.buttonColors(
                containerColor = MaterialTheme.colorScheme.background
            )
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
    //Firebase.database.getReference("ModuloEsercizi").child("NumeroSettimane").setValue("$count");
    //vm.setWeeksEx(count)
    weeks=count
    Row(modifier = Modifier
        .fillMaxWidth(),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically){

        Button(
            onClick = {
               /* if(count>0){
                    count--;
                    changed=true
                }*/
            },
            colors = ButtonDefaults.buttonColors(
                containerColor = MaterialTheme.colorScheme.background
            )
        ){
            Icon(
                painter = painterResource(id = R.drawable.frecciasx),
                contentDescription = "",
                modifier = Modifier.size(28.dp),
                tint= Color(0x1A001219)
            )
        }

        Text(
            text = count.toString(),
            style = MaterialTheme.typography.headlineLarge,
            fontSize = 32.sp
        )

        Button(
            onClick = {
               // count++;
               // changed=true
            },
            colors = ButtonDefaults.buttonColors(
                containerColor = MaterialTheme.colorScheme.background
            )
        ){
            Icon(
                painter = painterResource(id = R.drawable.frecciadx),
                contentDescription = "",
                modifier = Modifier.size(28.dp),
                tint= Color(0x1A001219)
            )
        }
    }
}

@Composable
fun SelettoreGiorniModify(vm: MusaViewModel) {
    val selected = remember {
        mutableStateListOf<Boolean>()
    }
    val daysSel by vm.daysListEx.observeAsState()

    var changed by remember {
        mutableStateOf(false)
    }
    val days: Array<String> = arrayOf("L", "M", "M", "G", "V", "S", "D")

    for (i in 0..6){
        selected.add(false)
        //Firebase.database.getReference("ModuloEsercizi")
        // .child("GiorniLiberi").child(days[i]).setValue(false);
    }
    /*if(!changed){
        selected= vm.getDaysListEx() as SnapshotStateList<Boolean>
        changed=true
    }*/
    //vm.setDaysListEx(selected)

    var isDayClicked : MutableList<Boolean> = mutableListOf()
    for (i in 0..6) {
        //Log.d("ISDAY", daysSel!!.get(i).toString())

        if (daysSel!!.get(i)) {
            isDayClicked.add(true)
            selected[i] = true
            daysL[i] = true
        } else {
            isDayClicked.add(false)
            selected[i] = false
            daysL[i] = false
        }
    }
   // Log.d("MODIFICAPIANOESERCIZI", "$daysSel")

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
            //Log.d("ISDAY", daysSel!!.get(i).toString())

            //Log.d("ISDAY", isDayClicked.toString())
            Text(
                text = days[i],
                style = MaterialTheme.typography.headlineLarge,
                fontSize = 30.sp,
                modifier = Modifier
                    .clickable {
                        isDayClicked[i] = !isDayClicked[i]
                        if (isDayClicked[i]) {
                            daysL[i]=true
                            //Firebase.database.getReference("ModuloEsercizi")
                            //  .child("GiorniLiberi").child(daysDb[i]).setValue(true);
                            selected[i]=true
                        } else {
                            daysL[i]=false
                            // Firebase.database.getReference("ModuloEsercizi")
                            //  .child("GiorniLiberi").child(daysDb[i]).setValue(false);
                            selected[i]=false
                        }
                    },
                color = if (isDayClicked[i]) MaterialTheme.colorScheme.background else Color(0x80EE9B00)
            )
        }
    }


}

fun SaveChanges(vm: MusaViewModel){

    //GIORNI DELLA SETTIMANA
    val daysDb: Array<String> = arrayOf("Lun", "Mar", "Mer", "Gio", "Ven", "Sab", "Dom")
    for(i in 0..6){
        Firebase.database.getReference("ModuloEsercizi")
            .child("GiorniLiberi").child(daysDb[i]).setValue(daysL[i]);
    }
    vm.setDaysListEx(daysL)

    //NUMERO GIORNI
    Firebase.database.getReference("ModuloEsercizi").child("NumeroGiorni").setValue(dayPerWeek);
    vm.setDaysEx(dayPerWeek)

    //NUMERO SETTIMANE
    Firebase.database.getReference("ModuloEsercizi").child("NumeroSettimane").setValue(weeks);
    vm.setWeeksEx(weeks)

}