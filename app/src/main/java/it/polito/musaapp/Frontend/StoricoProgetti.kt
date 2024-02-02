package it.polito.musaapp.Frontend

import android.util.Log
import androidx.compose.foundation.BorderStroke
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.NavGraph.Companion.findStartDestination
import com.google.firebase.Firebase
import com.google.firebase.database.database
import it.polito.musaapp.Backend.DeleteSingleProject
import it.polito.musaapp.Backend.DeleteSingleProjectCompleted
import it.polito.musaapp.Backend.MusaViewModel
import it.polito.musaapp.R
import it.polito.musaapp.Screens

@Composable
fun StoricoProgetti(navController:NavController, vm: MusaViewModel){
    val projectList by vm.projectListCompleted.observeAsState()
    val previousScreen by vm.previousScreen.observeAsState()

    var countCompletati=-1
    val myRef2 = Firebase.database.getReference("Progetti").child("CounterProgettiCompletati")
    if(countCompletati==-1){
        myRef2.get().addOnSuccessListener {
            Log.d("PROJECTCOMPLETED", "valori ${it.value}");
            countCompletati = it.value.toString().toInt();
            vm.setCounterProgettiCompletati(countCompletati)
        }.addOnFailureListener {
            Log.d("PROJECTCOMPLETED", "Error", it);
        }
    }
    val counterProgetti by vm.counterProgettiCompletati.observeAsState()

    Column(
        modifier= Modifier.fillMaxSize()
    ){
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
                modifier = Modifier.size(35.dp).clickable {
                    navController.navigate(previousScreen!!.name) {
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

        Text("I TUOI PROGETTI")

        if(projectList.isNullOrEmpty()|| counterProgetti!! <=0){
            Log.d("LISTAPROGETTI", "Non ci sono progetti")
            Text("Non hai ancora inserito nessun progetto personale")
        }
        else{
            Log.d("LISTAPROGETTI", projectList!!.size.toString())
            //DISPLAY LISTA PROGETTI
            Log.d("LISTAPROGETTI", projectList!!.get(0).name)
            // Log.d("LISTAPROGETTI", projectList!!.get(1).name)

            for(i in 0..vm.counterProgettiCompletati.value!!-1){
                //  Spacer(modifier = Modifier.height(16.dp))
                Log.d("LISTAPROGETTI", projectList!!.get(i).name)
                var openOptions by remember{
                    mutableStateOf(false)
                }
                if(vm.projectList.value!![i].name!="") {

                    Card(
                        shape = RoundedCornerShape(15.dp),
                        colors = CardDefaults.cardColors(
                            containerColor = MaterialTheme.colorScheme.primary,
                            contentColor = MaterialTheme.colorScheme.onPrimary,
                        ),
                        border = BorderStroke(5.dp, MaterialTheme.colorScheme.primaryContainer),
                        modifier = Modifier.clickable {
                            vm.setProjectToPrint(projectList!![i])
                            vm.setProjectToPrintCounter(i)
                            navController.navigate(Screens.SinglePageProject.name) {
                                popUpTo(navController.graph.findStartDestination().id) {
                                    saveState = true
                                }
                                launchSingleTop = true
                                restoreState = true
                            }
                        }
                    ) {
                        Box(
                            modifier = Modifier
                                .padding(horizontal = 12.dp, vertical = 8.dp)
                                .height(90.dp)
                                .fillMaxWidth()
                        ) {
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.SpaceBetween,
                                modifier = Modifier.fillMaxSize()
                            ) {
                                Text(
                                    text = projectList!!.get(i).name,
                                    style = MaterialTheme.typography.headlineMedium,
                                    textAlign = TextAlign.Center,
                                    modifier = Modifier
                                        .fillMaxSize()
                                        .offset(x = 0.dp, y = 3.dp)
                                        .weight(3f)
                                )
                                Icon(
                                    Icons.Filled.MoreVert,
                                    contentDescription = "More",
                                    tint = MaterialTheme.colorScheme.onPrimary,
                                    modifier = Modifier
                                        .size(44.dp)
                                        .clickable {
                                            openOptions = !openOptions
                                        }
                                        .weight(1f)
                                )

                            }
                            if (openOptions) {
                                Spacer(modifier = Modifier.height(100.dp))
                                Box(
                                    modifier = Modifier.align(Alignment.BottomEnd)
                                ) {
                                    Column {
                                        Text(
                                            text = "Restore",
                                            modifier = Modifier.clickable {
                                                //RESTORE SINGLE TASK
                                            }
                                        )
                                        Text(
                                            text = "Elimina",
                                            modifier = Modifier.clickable {
                                                //ELIMINA SINGLE TASK
                                                //Firebase.database.getReference("Progetti").child("CounterProgetti").setValue(vm.projectList.value!!.size-1)
                                                DeleteSingleProjectCompleted(vm, i)
                                                navController.navigate(Screens.StoricoProgetti.name) {
                                                    popUpTo(navController.graph.findStartDestination().id) {
                                                        saveState = true
                                                    }
                                                    launchSingleTop = true
                                                    restoreState = true
                                                }
                                            }
                                        )
                                    }

                                }
                            }

                        }
                    }
                }

            }

        }


    }

}