package it.polito.musaapp.Frontend

import android.util.Log
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.NavGraph.Companion.findStartDestination
import it.polito.musaapp.Backend.MusaViewModel
import it.polito.musaapp.Screens

@Composable
fun ProjectPage(navController: NavController, vm:MusaViewModel){
    val projectList by vm.projectList.observeAsState()
    Column(
        modifier=Modifier.fillMaxSize()
    ){
        Text("I TUOI PROGETTI")

        if(projectList.isNullOrEmpty()){
            Log.d("LISTAPROGETTI", "Non ci sono progetti")
            Text("Non hai ancora inserito nessun progetto personale")
        }
        else{
            Log.d("LISTAPROGETTI", projectList!!.size.toString())
            //DISPLAY LISTA PROGETTI
            Log.d("LISTAPROGETTI", projectList!!.get(0).name)
           // Log.d("LISTAPROGETTI", projectList!!.get(1).name)

            for(i in 0..projectList!!.size-1){
              //  Spacer(modifier = Modifier.height(16.dp))
                Card(
                    shape = RoundedCornerShape(15.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = MaterialTheme.colorScheme.primary,
                        contentColor= MaterialTheme.colorScheme.onPrimary,
                    ),
                    border = BorderStroke(5.dp, MaterialTheme.colorScheme.primaryContainer),
                    modifier = Modifier.clickable {
                        vm.setProjectToPrint(projectList!![i])
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
                    ){
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceBetween,
                            modifier = Modifier.fillMaxSize()
                        ){
                                Text(
                                    text = projectList!!.get(i).name,
                                    style = MaterialTheme.typography.headlineMedium,
                                    textAlign = TextAlign.Center,
                                    modifier = Modifier
                                        .fillMaxSize()
                                        .offset(x = 0.dp, y = 3.dp)
                                )
                            }

                        }


                }

            }

        }

        Icon(
            Icons.Filled.Add,
            contentDescription = "Add",
            tint= MaterialTheme.colorScheme.onPrimary,
            modifier = Modifier
                .size(44.dp)
                .align(Alignment.CenterHorizontally)
                .clickable {
                    navController.navigate(Screens.NewProject.name) {
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