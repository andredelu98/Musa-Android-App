package it.polito.musaapp.Frontend

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.NavGraph.Companion.findStartDestination
import it.polito.musaapp.Backend.MusaViewModel
import it.polito.musaapp.Screens

@Composable
fun ProjectPage(navController: NavController, vm:MusaViewModel){
    Column(
        modifier=Modifier.fillMaxSize()
    ){
        Text("I TUOI PROGETTI")

        if(vm.projectList.value.isNullOrEmpty()){
            Text("Non hai ancora inserito nessun progetto personale")
        }
        else{
            //DISPLAY LISTA PROGETTI
            for(i in 0..vm.projectList.value!!.size){
                Text(vm.projectList.value!!.get(i).name)
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