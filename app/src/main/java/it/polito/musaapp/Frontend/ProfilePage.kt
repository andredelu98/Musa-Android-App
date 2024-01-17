package it.polito.musaapp.Frontend

import android.graphics.Paint
import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.NavGraph.Companion.findStartDestination
import com.google.firebase.Firebase
import com.google.firebase.database.database
import it.polito.musaapp.Backend.MusaViewModel
import it.polito.musaapp.Screens

@Composable
fun ProfilePage(navController: NavController, vm:MusaViewModel) {
    val nameProfile= vm.name.observeAsState()
    Column (
        modifier= Modifier
            .fillMaxWidth()
            .fillMaxHeight()
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
            "Ciao ${nameProfile.value}",
            modifier = Modifier.weight(1f)
        )

        Button(
            onClick={
              navController.navigate(Screens.ModifyProfile.name) {
                  popUpTo(navController.graph.findStartDestination().id) {
                      saveState = true
                  }
                  launchSingleTop = true
                  restoreState = true }
          },
            modifier= Modifier
                .weight(2f)
                .fillMaxWidth()
        ){
            Text("Modifica Profilo")
        }
        Button(
            onClick={
                navController.navigate(Screens.ModifyExercise.name) {
                    popUpTo(navController.graph.findStartDestination().id) {
                        saveState = true
                    }
                    launchSingleTop = true
                    restoreState = true }
            },
            modifier= Modifier
                .weight(2f)
                .fillMaxWidth()
        ){
            Text("Modifica piano di esercizi")
        }
        Button(
            onClick={
                navController.navigate(Screens.ModifyProfile.name) {
                    popUpTo(navController.graph.findStartDestination().id) {
                        saveState = true
                    }
                    launchSingleTop = true
                    restoreState = true }
            },
            modifier= Modifier
                .weight(2f)
                .fillMaxWidth()
        ){
            Text("Reference salvate")
        }
        Button(
            onClick={
                navController.navigate(Screens.ModifyProfile.name) {
                    popUpTo(navController.graph.findStartDestination().id) {
                        saveState = true
                    }
                    launchSingleTop = true
                    restoreState = true }
            },
            modifier= Modifier
                .weight(2f)
                .fillMaxWidth()
        ){
            Text("Storico progetti")
        }
        Button(
            onClick={
                DeleteProfile()
                navController.navigate(Screens.FormStart.name) {
                    popUpTo(navController.graph.findStartDestination().id) {
                        saveState = true
                    }
                    launchSingleTop = true
                    restoreState = true }
            },
            modifier= Modifier
                .weight(2f)
                .fillMaxWidth()
        ){
            Text("Elimina account")
        }
    }
}

fun DeleteProfile(){
    Firebase.database.getReference("UtenteGiaRegistrato").setValue(false)
    Firebase.database.getReference("ModuloStart").child("Nome").setValue("")
    Firebase.database.getReference("ModuloStart").child("Mail").setValue("")
    Firebase.database.getReference("ModuloStart").child("Categoria").setValue("Disegno")
    Firebase.database.getReference("ModuloStart").child("Professione").setValue("Studio")
    Firebase.database.getReference("ModuloStart").child("Livello").setValue("Principiante")
    Firebase.database.getReference("ModuloEsercizi").child("NumeroGiorni").setValue(0)
    Firebase.database.getReference("ModuloEsercizi").child("NumeroSettimane").setValue(0)
    val days: Array<String> = arrayOf("Lun", "Mar", "Mer", "Gio", "Ven", "Sab", "Dom")
    for (i in 0..6){
        Firebase.database.getReference("ModuloEsercizi")
            .child("GiorniLiberi").child(days[i]).setValue(false);
    }
}