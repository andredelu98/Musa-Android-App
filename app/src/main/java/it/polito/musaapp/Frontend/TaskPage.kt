package it.polito.musaapp.Frontend

import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.NavGraph.Companion.findStartDestination
import com.google.firebase.Firebase
import com.google.firebase.database.database
import it.polito.musaapp.Backend.GetTask
import it.polito.musaapp.Backend.MusaViewModel
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
    Column {
        Row(){
            //ICONE CALENDARIO E MODIFICA
        }
        Text(
            "Task ${taskCounter}/ ${vm.weeksEx.value!!*vm.daysEx.value!!}"
        )
        Text(vm.taskDueDate.value!!.get(taskCounter!!-1))
        Spacer(modifier = Modifier.height(8.dp))
        Text(nextTask.toString())
        Spacer(modifier = Modifier.height(18.dp))
        Button (
            onClick = {
                navController.navigate(Screens.TaskReference.name) {
                    popUpTo(navController.graph.findStartDestination().id) {
                        saveState = true
                    }
                    launchSingleTop = true
                    restoreState = true
                }
            }
        ){
            Text("Vedi Reference")
        }
        Spacer(modifier = Modifier.height(18.dp))
        if(vm.taskCompleted.value!!>=vm.taskCounter.value!!-1){
            Button(
                onClick={
                    vm.setNextTask(vm.taskCounter.value!!)
                    vm.setTaskCounter(vm.taskCounter.value!!+1)
                    vm.setTaskCompleted(vm.taskCompleted.value!!+1)
                    Log.d("TASK COMPLETED", vm.taskCompleted.value.toString())
                    Firebase.database.getReference("ModuloEsercizi").child("TaskCompletati")
                        .setValue(vm.taskCompleted.value!!);

                    if(taskCounter!!>7||vm.taskCounter.value!!-1>=(vm.weeksEx.value!!*vm.daysEx.value!!)){
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
                Text(text = "TaskCompletato")
            }
        }
        else{
            Button(
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
                Text(text = "Vai al task corrente")
            }
        }

    }
}