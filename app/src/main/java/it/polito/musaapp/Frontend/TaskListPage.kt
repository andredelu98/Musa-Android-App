package it.polito.musaapp.Frontend

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.NavGraph.Companion.findStartDestination
import it.polito.musaapp.Backend.CalculateDueDates
import it.polito.musaapp.Backend.GetTask
import it.polito.musaapp.Backend.MusaViewModel
import it.polito.musaapp.Backend.RefreshVariablesTask
import it.polito.musaapp.Screens
import java.time.LocalDate

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun TaskListPage(navController: NavController, vm:MusaViewModel){

    GetTask(vm)

    val taskList by vm.TaskList.observeAsState()
    var taskToDisplay: Int =0
    Log.d("TASKLIST", taskList.toString())
    if(taskList!=null) {
        CalculateDueDates(vm)

        Column() {
            if(taskList!!.count()>(vm.weeksEx.value!!*vm.daysEx.value!!)){
                taskToDisplay=(vm.weeksEx.value!!*vm.daysEx.value!!)
            }
            else{
                taskToDisplay=taskList!!.count()
            }
            for (i in vm.taskCompleted.value!! until taskToDisplay) {
                //vm.setNextTask(i)
                //vm.setTaskCounter(i)
                Card(
                    modifier = Modifier.clickable {
                       // Log.d("I_TASK", "${i+1}.toString()")
                        vm.setTaskCounter(i+1)
                        vm.setNextTask(i)
                       // Log.d("I_TASK", "next ${vm.nextTask.value}")
                        navController.navigate(Screens.TaskPage.name) {
                            popUpTo(navController.graph.findStartDestination().id) {
                                saveState = true
                            }
                            launchSingleTop = true
                            restoreState = true
                        }
                    }
                ) {
                    Text(vm.TaskList.value!!.get(i))
                }
            }
        }

    }
}