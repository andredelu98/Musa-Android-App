package it.polito.musaapp.Frontend

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
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
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.NavGraph.Companion.findStartDestination
import com.google.firebase.Firebase
import com.google.firebase.database.database
import it.polito.musaapp.Backend.GetTask
import it.polito.musaapp.Backend.MusaViewModel
import it.polito.musaapp.R
import it.polito.musaapp.Screens

/*TASK PAGE BACKUP
@Composable
fun TaskPage(navController: NavController, vm:MusaViewModel){
    var TaskCounter by remember {
        mutableStateOf(0)
    }
    var TaskCounterToPrint by remember {
        mutableStateOf(0)
    }
    GetTask(TaskCounter, vm)
    Column {
        Row(){
           //ICONE CALENDARIO E MODIFICA
        }
        Text(
            "Task ${TaskCounterToPrint+1}/ ${vm.weeksEx.value!!*vm.daysEx.value!!}"
        )
        Spacer(modifier = Modifier.height(8.dp))
        vm.setNextTask(TaskCounter)
        Text(vm.nextTask.value!!)
        Spacer(modifier = Modifier.height(18.dp))
        Button(
            onClick={

                TaskCounter++;
                TaskCounterToPrint++;
                if(TaskCounterToPrint>=6){
                    navController.navigate(Screens.TaskFinished.name) {
                        popUpTo(navController.graph.findStartDestination().id) {
                            saveState = true
                        }
                        launchSingleTop = true
                        restoreState = true
                    }
                }
                if(TaskCounter>=vm.TaskList.value!!.size){
                    TaskCounter=0
                }
                vm.setNextTask(TaskCounter)
                if(TaskCounterToPrint>=(vm.weeksEx.value!!*vm.daysEx.value!!)) {
                    navController.navigate(Screens.HelpPage.name) {
                        popUpTo(navController.graph.findStartDestination().id) {
                            saveState = true
                        }
                        launchSingleTop = true
                        restoreState = true
                    }
                }
            }
        )
        {
            Text(text = "TaskCompletato")
        }
    }
}*/

@Composable
fun TaskPage(navController: NavController, vm: MusaViewModel){
    GetReferenceTask(vm)
    val taskCounter by vm.taskCounter.observeAsState()
    val taskCompleted by vm.taskCompleted.observeAsState()
    val nextTask by vm.nextTask.observeAsState()
    Log.d("TASK COMPLETED", vm.taskCompleted.value.toString())
    //Log.d ("TASKPAGE", " $taskCounter, next $nextTask" )
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 22.dp, vertical = 16.dp)
        ){
            Icon(
                painter = painterResource(id = R.drawable.back_arrow),
                contentDescription = null,
                modifier = Modifier.size(35.dp).clickable {
                    navController.navigate(Screens.TaskListPage.name) {
                        popUpTo(navController.graph.findStartDestination().id) {
                            saveState = true
                        }
                        launchSingleTop = true
                        restoreState = true
                    }
                }
            )

            Image(
                painter = painterResource(id = R.drawable.loghetto),
                contentDescription = null,
                modifier = Modifier.size(85.dp)
            )

            Icon(
                painter = painterResource(id = R.drawable.pencil),
                contentDescription = null,
                modifier = Modifier.size(40.dp).clickable {
                    vm.setPreviousScreen(Screens.TaskPage)
                    navController.navigate(Screens.ModifyPlanExercise.name) {
                        popUpTo(navController.graph.findStartDestination().id) {
                            saveState = true
                        }
                        launchSingleTop = true
                        restoreState = true
                    }
                }
            )
        }
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxSize()
                .padding(8.dp),
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ){
                Icon(
                    painter = painterResource(id = R.drawable.frecciasx),
                    contentDescription = null,
                    modifier = Modifier.size(26.dp).clickable {
                        if(taskCounter!=1&&taskCounter!!>taskCompleted!!+1){
                            vm.setTaskCounter(taskCounter!!-1)
                            vm.setNextTask(taskCounter!!-1)
                        }
                    }
                )
                Spacer(modifier = Modifier.width(5.dp))
                Text(
                    text = "ESERCIZIO ${taskCounter}/ ${vm.weeksEx.value!!*vm.daysEx.value!!}",
                    style = MaterialTheme.typography.headlineLarge
                )
                Spacer(modifier = Modifier.width(5.dp))
                Icon(
                    painter = painterResource(id = R.drawable.frecciadx),
                    contentDescription = null,
                    modifier = Modifier.size(26.dp).clickable {
                        //RIVEDERE CONDIZIONI FINE
                        if(taskCounter!!< 6 && taskCounter!! < vm.weeksEx.value!!*vm.daysEx.value!!){
                            vm.setNextTask(taskCounter!!)
                            vm.setTaskCounter(taskCounter!!+1)
                        }
                    }
                )
            }
            Text(
                text = vm.taskDueDate.value!!.get(taskCounter!!-1),
                style = MaterialTheme.typography.bodySmall,
                textAlign = TextAlign.Center
            )
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxSize().padding(vertical = 40.dp)
            ) {
                Text(
                    text = nextTask.toString(),
                    style = MaterialTheme.typography.bodyLarge,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.padding(horizontal = 35.dp)
                )
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier.fillMaxWidth().height(230.dp)
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.refresh),
                        contentDescription = null,
                        modifier = Modifier.size(32.dp)
                    )
                    Button (
                        shape = MaterialTheme.shapes.large,
                        colors = ButtonDefaults.buttonColors(
                            containerColor = MaterialTheme.colorScheme.primary,
                        ),
                        modifier = Modifier
                            //.width(140.dp)
                            .height(IntrinsicSize.Min)
                            .border(5.dp, MaterialTheme.colorScheme.primaryContainer, MaterialTheme.shapes.large)
                        ,
                        onClick = {
                            navController.navigate(Screens.TaskReference.name) {
                                popUpTo(navController.graph.findStartDestination().id) {
                                    saveState = true
                                }
                                launchSingleTop = true
                                restoreState = true
                            }
                        }
                    )
                    {
                        Text(text = "Vedi Reference",
                            style = MaterialTheme.typography.headlineSmall,
                            modifier = Modifier.offset(x = 0.dp, y = (-2).dp)
                            )
                    }

                    if(vm.taskCompleted.value!!>=vm.taskCounter.value!!-1){
                        Button(
                            shape = MaterialTheme.shapes.large,
                            colors = ButtonDefaults.buttonColors(
                                containerColor = MaterialTheme.colorScheme.onPrimary
                            ),
                            modifier = Modifier.width(280.dp),
                            onClick={
                                vm.setNextTask(vm.taskCounter.value!!)
                                vm.setTaskCounter(vm.taskCounter.value!!+1)
                                vm.setTaskCompleted(vm.taskCompleted.value!!+1)
                                Log.d("TASK COMPLETED", vm.taskCompleted.value.toString())
                                Firebase.database.getReference("ModuloEsercizi").child("TaskCompletati")
                                    .setValue(vm.taskCompleted.value!!);

                                if(taskCounter!!>7 || vm.taskCounter.value!!-1>=(vm.weeksEx.value!!*vm.daysEx.value!!)){
                                    navController.navigate(Screens.TaskFinished.name) {
                                        popUpTo(navController.graph.findStartDestination().id) {
                                            saveState = true
                                        }
                                        launchSingleTop = true
                                        restoreState = true
                                    }
                                }
                                /* else if() {
                                     navController.navigate(Screens.TaskFinished.name) {
                                         popUpTo(navController.graph.findStartDestination().id) {
                                             saveState = true
                                         }
                                         launchSingleTop = true
                                         restoreState = true
                                     }
                                 }*/
                            }
                        )
                        {
                            Text(
                                text = "COMPLETATO",
                                style = MaterialTheme.typography.headlineLarge,
                                color = MaterialTheme.colorScheme.background,
                                //modifier = Modifier.offset(x = 0.dp, y = (-2).dp)
                            )
                        }
                    }
                    else{
                        Text(
                            text = "Torna all'esercizio corrente",
                            style = MaterialTheme.typography.headlineSmall,
                            textDecoration = TextDecoration.Underline,
                            textAlign = TextAlign.Center,
                            modifier = Modifier
                                //.padding(horizontal = 50.dp)
                                .clickable{
                                    vm.setNextTask(vm.taskCompleted.value!!)
                                    vm.setTaskCounter(vm.taskCompleted.value!!+1)
                                }
                        )
                        /*Button(
                            shape = MaterialTheme.shapes.large,
                            colors = ButtonDefaults.buttonColors(
                                containerColor = MaterialTheme.colorScheme.onPrimary
                            ),
                            //modifier = Modifier
                                //.width(300.dp),
                            onClick={
                                vm.setNextTask(vm.taskCompleted.value!!)
                                vm.setTaskCounter(vm.taskCompleted.value!!+1)
                            }
                            /* else if() {
                                 navController.navigate(Screens.TaskFinished.name) {
                                     popUpTo(navController.graph.findStartDestination().id) {
                                         saveState = true
                                     }
                                     launchSingleTop = true
                                     restoreState = true
                                 }
                             }*/
                        )
                        {
                            Text(
                                text = "ESERCIZIO CORRENTE",
                                style = MaterialTheme.typography.headlineLarge,
                                color = MaterialTheme.colorScheme.background
                                )
                        }
                         */

                    }
                }
            }
        }

    }
}