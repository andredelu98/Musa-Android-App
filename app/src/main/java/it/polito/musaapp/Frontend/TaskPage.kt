package it.polito.musaapp.Frontend

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import it.polito.musaapp.Backend.GetTask
import it.polito.musaapp.Backend.MusaViewModel


@Composable
fun TaskPage(navController: NavController, vm:MusaViewModel){
    var TaskCounter by remember {
        mutableStateOf(0)
    }
    Column {
        Row(){
           //ICONE CALENDARIO E MODIFICA
        }
        Text(
            "Task ${TaskCounter+1}/TASKTOTALI"
        )
        Spacer(modifier = Modifier.height(8.dp))
        GetTask(TaskCounter, vm)
        Text(vm.nextTask.value!!)
        Spacer(modifier = Modifier.height(18.dp))
        Button(
            onClick={
                TaskCounter++;

            }
        )
        {
            Text(text = "TaskCompletato")
        }
    }
}