package it.polito.musaapp.Frontend

import android.graphics.Paint
import android.util.Log
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
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
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
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
fun ProfilePage(navController: NavController, vm:MusaViewModel) {
    val nameProfile= vm.name.observeAsState()

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

    Column (
        modifier= Modifier
            .fillMaxSize()
            .padding(horizontal = 25.dp, vertical = 60.dp)
    ){
        if(vm.registered.value==true){
            val myRef = Firebase.database.getReference("ModuloStart").child("Nome");
            myRef.get().addOnSuccessListener {
                Log.d("FORM", "valori ${it.value}");
                vm.setName(it.value.toString())
            }.addOnFailureListener {
                Log.d("FORM", "Error", it);
            }
        }

        Text(
            text = "Ciao ${nameProfile.value}",
            style = MaterialTheme.typography.displayLarge,
            fontSize = 45.sp
        )

        Spacer(modifier = Modifier.height(10.dp))

        Row(
            modifier = Modifier
                .height(75.dp)
                .fillMaxWidth()
                .clickable {
                    navController.navigate(Screens.ModifyProfile.name) {
                        popUpTo(navController.graph.findStartDestination().id) {
                            saveState = true
                        }
                        launchSingleTop = true
                        restoreState = true
                    }
                },
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        )
        {
            Text(
                text = "Modifica profilo" ,
                style = MaterialTheme.typography.bodyLarge
            )
            Icon(
                painter = painterResource(id = R.drawable.frecciadx),
                contentDescription = "",
                modifier = Modifier.size(30.dp)
            )
        }

        Divider(thickness = 3.dp, color = Color(0x1A001219))

        Row(
            modifier = Modifier
                .height(75.dp)
                .fillMaxWidth()
                .clickable {
                    if (taskInserito){
                        vm.setPreviousScreen(Screens.ProfilePage)
                        navController.navigate(Screens.ModifyPlanExercise.name) {
                            popUpTo(navController.graph.findStartDestination().id) {
                                saveState = true
                            }
                            launchSingleTop = true
                            restoreState = true
                        }
                    }else{
                        navController.navigate(Screens.ModifyExerciseEmpty.name) {
                            popUpTo(navController.graph.findStartDestination().id) {
                                saveState = true
                            }
                            launchSingleTop = true
                            restoreState = true
                        }
                    }

                },
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        )
        {
            Text(
                text = "Modifica piano di esercizi" ,
                style = MaterialTheme.typography.bodyLarge
            )
            Icon(
                painter = painterResource(id = R.drawable.frecciadx),
                contentDescription = "",
                modifier = Modifier.size(30.dp)
            )
        }

        Divider(thickness = 3.dp, color = Color(0x1A001219))

        Row(
            modifier = Modifier
                .height(75.dp)
                .fillMaxWidth()
                .clickable {
                    navController.navigate(Screens.SavedReference.name) {
                        popUpTo(navController.graph.findStartDestination().id) {
                            saveState = true
                        }
                        launchSingleTop = true
                        restoreState = true
                    }
                           },
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        )
        {
            Text(
                text = "Reference salvate" ,
                style = MaterialTheme.typography.bodyLarge
            )
            Icon(
                painter = painterResource(id = R.drawable.frecciadx),
                contentDescription = "",
                modifier = Modifier.size(30.dp)
            )
        }

        Divider(thickness = 3.dp, color = Color(0x1A001219))

        Row(
            modifier = Modifier
                .alpha(0.3f)
                .height(75.dp)
                .fillMaxWidth()
                .clickable {/*TODO()*/ },
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        )
        {
            Text(
                text = "Storico piano di esercizi" ,
                style = MaterialTheme.typography.bodyLarge
            )
            Icon(
                painter = painterResource(id = R.drawable.frecciadx),
                contentDescription = "",
                modifier = Modifier.size(30.dp)
            )
        }

        Divider(thickness = 3.dp, color = Color(0x1A001219))

        Row(
            modifier = Modifier
                .height(75.dp)
                .fillMaxWidth()
                .clickable {
                    vm.setPreviousScreen(Screens.ProfilePage)
                    navController.navigate(Screens.StoricoProgetti.name) {
                        popUpTo(navController.graph.findStartDestination().id) {
                            saveState = true
                        }
                        launchSingleTop = true
                        restoreState = true
                    }
               },
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        )
        {
            Text(
                text = "Storico progetti personali" ,
                style = MaterialTheme.typography.bodyLarge
            )
            Icon(
                painter = painterResource(id = R.drawable.frecciadx),
                contentDescription = "",
                modifier = Modifier.size(30.dp)
            )
        }

        Divider(thickness = 3.dp, color = Color(0x1A001219))

        Row(
            modifier = Modifier
                .height(75.dp)
                .fillMaxWidth()
                .clickable {
                    DeleteProfile(vm)
                    navController.navigate(Screens.FormStart.name) {
                        popUpTo(navController.graph.findStartDestination().id) {
                            saveState = true
                        }
                        launchSingleTop = true
                        restoreState = true
                    }
                },
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        )
        {
            Text(
                text = "Elimina account" ,
                style = MaterialTheme.typography.bodyLarge
            )
            Icon(
                painter = painterResource(id = R.drawable.frecciadx),
                contentDescription = "",
                modifier = Modifier.size(30.dp)
            )
        }
        Spacer(modifier = Modifier.height(12.dp))
        /*
        Button(
            shape = MaterialTheme.shapes.large,
            colors = ButtonDefaults.buttonColors(
                containerColor = MaterialTheme.colorScheme.primary,
            ),
            modifier = Modifier
                .width(140.dp)
                .height(IntrinsicSize.Min)
                .border(5.dp, MaterialTheme.colorScheme.primaryContainer, MaterialTheme.shapes.large)
            ,
            onClick = { /*TODO()*/ }
        ){ Text(text = "Log out", style = MaterialTheme.typography.headlineSmall, textAlign = TextAlign.Center)}
*/
    }
}

fun DeleteProfile(vm: MusaViewModel){
    Firebase.database.getReference("UtenteGiaRegistrato").setValue(false)
    Firebase.database.getReference("ModuloStart").child("Nome").setValue("")
    Firebase.database.getReference("ModuloStart").child("Mail").setValue("")
    Firebase.database.getReference("ModuloStart").child("Categoria").setValue("Disegno")
    Firebase.database.getReference("ModuloStart").child("Professione").setValue("Studio")
    Firebase.database.getReference("ModuloStart").child("Livello").setValue("Principiante")
    Firebase.database.getReference("ModuloEsercizi").child("NumeroGiorni").setValue(0)
    Firebase.database.getReference("ModuloEsercizi").child("NumeroSettimane").setValue(0)
    Firebase.database.getReference("Progetti").child("CounterProgetti").setValue(0)
    Firebase.database.getReference("Progetti").child("ListaProgetti").removeValue()
    Firebase.database.getReference("Progetti").child("CounterProgettiCompletati").setValue(0)
    Firebase.database.getReference("Progetti").child("ListaProgettiCompletati").removeValue()
    Firebase.database.getReference("ReferenceSalvate").removeValue()
    Firebase.database.getReference("Progetti").child("CounterProgettiEliminati").setValue(0)
    vm.setCounterProgettiEliminati(0)
    vm.setReferenceCounter(0)
    vm.deleteAllReference()
    vm.setCounterProgetti(0)
    vm.CleanProjectListCompleted()
    vm.CleanProjectList()
    val days: Array<String> = arrayOf("Lun", "Mar", "Mer", "Gio", "Ven", "Sab", "Dom")
    for (i in 0..6){
        Firebase.database.getReference("ModuloEsercizi")
            .child("GiorniLiberi").child(days[i]).setValue(false)
    }
}