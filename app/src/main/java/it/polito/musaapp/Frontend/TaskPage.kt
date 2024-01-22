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
    //Log.d ("TASKPAGE", " $taskCounter, next $nextTask" )
    Column {
        Row(){
            //ICONE CALENDARIO E MODIFICA
        }
        Text(
            "Task ${vm.taskCounter.value!!}/ ${vm.weeksEx.value!!*vm.daysEx.value!!}"
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(vm.nextTask.value!!)
        Spacer(modifier = Modifier.height(18.dp))
        Button(
            onClick={
                if(vm.taskCounter.value!!>=7){
                    navController.navigate(Screens.TaskFinished.name) {
                        popUpTo(navController.graph.findStartDestination().id) {
                            saveState = true
                        }
                        launchSingleTop = true
                        restoreState = true
                    }
                }
                if(vm.taskCounter.value!!>=(vm.weeksEx.value!!*vm.daysEx.value!!-1)) {
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
}