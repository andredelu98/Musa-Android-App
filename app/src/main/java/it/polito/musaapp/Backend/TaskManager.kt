package it.polito.musaapp.Backend

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.lifecycle.Observer
import com.google.firebase.Firebase
import com.google.firebase.database.database
import java.time.LocalDate
import java.time.Period
import java.util.Date
import kotlin.time.days

var WorkingDays: Array<Boolean> = arrayOf(false,false,false,false,false,false,false)
var NumberOfDays: Int = 0
var NumberOfWeeks: Int = 0
var Category: String = ""
var Level: String = ""


fun RefreshVariablesTask(vm: MusaViewModel){
    val myRefStart= Firebase.database.getReference("ModuloStart")
    myRefStart.child("Categoria").get().addOnSuccessListener {
       // Log.d("TASKMANAGER", "Categoria ${it.value}");
        Category= it.value.toString()
        vm.setCategory(Category)
    }.addOnFailureListener {
        Log.d("TASKMANAGER", "Error", it);
    }
    myRefStart.child("Livello").get().addOnSuccessListener {
       // Log.d("TASKMANAGER", "Livello ${it.value}");
        Level= it.value.toString()
        vm.setLevel(Level)
    }.addOnFailureListener {
        Log.d("TASKMANAGER", "Error", it);
    }

    val myRef= Firebase.database.getReference("ModuloEsercizi")
    myRef.child("NumeroGiorni").get().addOnSuccessListener {
      //  Log.d("TASKMANAGER", "Giorni ${it.value}")
        NumberOfDays=it.value.toString().toInt()
        vm.setDaysEx(NumberOfDays)
    }.addOnFailureListener {
        Log.d("TASKMANAGER", "Error", it);
    }
    myRef.child("NumeroSettimane").get().addOnSuccessListener {
        //Log.d("TASKMANAGER", "Settimane ${it.value}");
        NumberOfWeeks=it.value.toString().toInt()
        vm.setWeeksEx(NumberOfWeeks)
    }.addOnFailureListener {
        Log.d("TASKMANAGER", "Error", it);
    }
    myRef.child("GiorniLiberi").get().addOnSuccessListener {
        Log.d("TASKMANAGER", "GiorniLiberi ${it.value}");
        for ((j, i) in it.children.withIndex()) {
            if(it.value==true){
                WorkingDays[j]=true
            }
        }
        vm.setDaysListEx(WorkingDays.toMutableList())
    }.addOnFailureListener {
        Log.d("TASKMANAGER", "Error", it);
    }
    //Log.d("TASKMANAGER", "WorkingDays $WorkingDays");

    val myRefTaskIns= Firebase.database.getReference("ModuloEsercizi")
    myRefTaskIns.child("TaskCompletati").get().addOnSuccessListener {
        //  Log.d("TASKMANAGER", "Giorni ${it.value}")
        vm.setTaskCompleted(it.value.toString().toInt())
    }.addOnFailureListener {
        Log.d("TASKMANAGER", "Error", it);
    }

}

@Composable
fun GetTask(vm: MusaViewModel){
    val categoryObs by vm.category.observeAsState()
    val levelObs by vm.level.observeAsState()
    val list: MutableList<String> = mutableListOf();
    Log.d("TASKMANAGER", "Livello ${vm.level.value} Categoria ${vm.category.value}");

    if(!categoryObs.isNullOrEmpty() &&!levelObs.isNullOrEmpty()){
        val myRef= Firebase.database.getReference("Esercizi").child(vm.category.value!!).child(vm.level.value!!)
        myRef.get().addOnSuccessListener {
            Log.d("TASKMANAGER", "  task list${it.value}")
            for(i in it.children) {
                Log.d("TASKMANAGER", " singoli task ${i.value.toString()}, number task ${list.count()+1}")
                list.add(i.value!!.toString())
            }
            vm.setTaskList(list)
        }.addOnFailureListener {
            Log.d("TASKMANAGER", "Error", it);
        }

        }



}

fun DeletePlanExercise(vm: MusaViewModel) {
    Firebase.database.getReference("ModuloEsercizi").child("NumeroGiorni").setValue(0)
    Firebase.database.getReference("ModuloEsercizi").child("Inserito").setValue(false)
    Firebase.database.getReference("ModuloEsercizi").child("TaskCompletati").setValue(0)
    Firebase.database.getReference("ModuloEsercizi").child("NumeroSettimane").setValue(0)
    val days: Array<String> = arrayOf("Lun", "Mar", "Mer", "Gio", "Ven", "Sab", "Dom")
    for (i in 0..6) {
        Firebase.database.getReference("ModuloEsercizi")
            .child("GiorniLiberi").child(days[i]).setValue(false);
    }
    vm.setWeeksEx(0)
    vm.setWeeksEx(0)
    vm.setTaskCompleted(0)
    vm.resetTaskList()
    vm.setDaysListEx(mutableListOf(false, false, false, false, false, false, false))
}


@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun CalculateDueDates(vm: MusaViewModel){
    val today= LocalDate.now()
    val daysEx by vm.daysEx.observeAsState()
    val weeksEx by vm.weeksEx.observeAsState()
    val daysList by vm.daysListEx.observeAsState()
    val weekdays: Array<String> = arrayOf("MONDAY", "TUESDAY", "WEDNESDAY", "THURSDAY", "FRIDAY", "SATURDAY", "SUNDAY")
    var todayWeekday: Int= -1
    var used=0


    val monthDays=31

    for(i in 0..daysEx!!)
    {
        var taskWeekday: Int = -1
        var dayDistance: Int = -1
        var newDate: LocalDate= today
        Log.d("DATATROVATA", "Sono nel for")
        Log.d("DATATROVATA", "$daysEx + ${daysList!!.size}")
       /* //CI SONO MENO ESERCIZI RISPETTO AI GIORNI DELLA SETTIMANA LIBERI
        if(daysEx!!<daysList!!.size){
            Log.d("DATA", today.dayOfWeek.toString())
           // InsertData(vm)
        }*/

        //CI SONO ESERCIZI == GIORNI DELLA SETTIMANA LIBERI
        var counter=0
        for(j in 0..6){
            if(daysList!!.get(j)==true)
                counter++;
            if(today.dayOfWeek.toString().equals(weekdays[j]))
                todayWeekday=j
        }

         var c=0
         if(daysEx!!.equals(counter)){
             Log.d("DATATROVATA", "Sono nell'if, c $c, used $used, todayweekday $todayWeekday")

            for(j in todayWeekday..6){

                if(daysList!!.get(j)==true){
                    if(c>=used){
                        taskWeekday=j
                        dayDistance=taskWeekday-todayWeekday
                    }
                  c++
                }
            }
            if(taskWeekday== -1){
                c=0
                for(j in 0..todayWeekday){
                    if(daysList!!.get(j)==true && taskWeekday==-1){
                        Log.d("DATATROVATA", "il giorno è  $j")
                        if(c>=used) {
                            taskWeekday = j
                            dayDistance = 7 - todayWeekday + taskWeekday
                        }
                        c++;
                    }
                }

            }
             if(taskWeekday!=-1){
                 Log.d("DATATROVATA", "TASK WEEKDAY = ${weekdays[taskWeekday]}, mancano $dayDistance alla fine del task")
                 used++;

             }

             if(monthDays>today.dayOfMonth+dayDistance){
                //SIAMO ANCORA A FEBBRAIO
                newDate=LocalDate.of(today.year, today.month, today.dayOfMonth+dayDistance)
            }
            else {
                //MESE SUCCESSIVO
                newDate= LocalDate.of(today.year, today.month+1, dayDistance-(monthDays-today.dayOfMonth))
            }
             InsertDate(vm, newDate, i)
        }
   /*     //CI SONO PIU' ESERCIZI RISPETTO AI GIORNI DELLA SETTIMANA LIBERI
        else {

            //InsertData(vm)
        }*/

    }

}

fun InsertDate(vm: MusaViewModel, d: LocalDate, task: Int){

    Log.d("DATATROVATA", "$d +  $task}")
}