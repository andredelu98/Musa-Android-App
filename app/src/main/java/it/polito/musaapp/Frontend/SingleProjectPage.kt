package it.polito.musaapp.Frontend

import android.widget.Space
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.NavGraph.Companion.findStartDestination
import it.polito.musaapp.Backend.MusaViewModel
import it.polito.musaapp.Backend.ProjectCompleted
import it.polito.musaapp.Screens

@Composable
fun SingleProjectPage(navController: NavController, vm: MusaViewModel){
    val project by vm.projectToPrint.observeAsState()
    var completed=false
    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        Text(project!!.name)
        Spacer(modifier=Modifier.height(20.dp))
        Text(project!!.category)
        Spacer(modifier=Modifier.height(20.dp))
        Text(project!!.description)

        Spacer(modifier=Modifier.height(20.dp))
        Button(
            onClick = {
                //VEDI REFERENCE
            }
        ){
            Text("Vedi Reference")
        }
        Button(
            onClick = {
                //COMPLETATO
                if(!completed){
                    completed=true
                    ProjectCompleted(vm.projectToPrint.value!!, vm, vm.projectToPrintCounter.value!!)
                    navController.navigate(Screens.ProjectPage.name) {
                        popUpTo(navController.graph.findStartDestination().id) {
                            saveState = true
                        }
                        launchSingleTop = true
                        restoreState = true
                    }
                }

            }
        ){
            Text("COMPLETATO")
        }
    }

}