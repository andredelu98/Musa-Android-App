package it.polito.musaapp.Backend

import android.util.Log
import com.google.firebase.Firebase
import com.google.firebase.database.database
import it.polito.musaapp.Screens
import java.util.Vector

var WorkingDays: Array<Int> = arrayOf(0,0,0,0,0,0,0)
var NumberOfDays: Int = 0
var NumberOfWeeks: Int = 0
var Category: String = ""
var Level: String = ""


fun RefreshVariablesTask(){
    val myRef= Firebase.database.getReference("ModuloEsercizi")
    myRef.child("NumeroGiorni").get().addOnSuccessListener {
        Log.d("TASKMANAGER", "Giorni ${it.value}");
        NumberOfDays=it.value.toString().toInt()
    }.addOnFailureListener {
        Log.d("TASKMANAGER", "Error", it);
    }
    myRef.child("NumeroSettimane").get().addOnSuccessListener {
        Log.d("TASKMANAGER", "Settimane ${it.value}");
        NumberOfWeeks=it.value.toString().toInt()
    }.addOnFailureListener {
        Log.d("TASKMANAGER", "Error", it);
    }
    myRef.child("GiorniLiberi").get().addOnSuccessListener {
        Log.d("TASKMANAGER", "GiorniLiberi ${it.value}");
        for ((j, i) in it.children.withIndex()) {
            if(it.value==true){
                WorkingDays[j]=1
            }
        }
    }.addOnFailureListener {
        Log.d("TASKMANAGER", "Error", it);
    }
    Log.d("TASKMANAGER", "WorkingDays $WorkingDays");
    val myRefStart= Firebase.database.getReference("ModuloStart")
    myRefStart.child("Categoria").get().addOnSuccessListener {
        Log.d("TASKMANAGER", "Categoria ${it.value}");
        Category= it.value.toString()
    }.addOnFailureListener {
        Log.d("TASKMANAGER", "Error", it);
    }
    myRefStart.child("Livello").get().addOnSuccessListener {
        Log.d("TASKMANAGER", "Livello ${it.value}");
        Level= it.value.toString()
    }.addOnFailureListener {
        Log.d("TASKMANAGER", "Error", it);
    }
}
fun GetTask(number: Int, vm: MusaViewModel){
    Log.d("TASKMANAGER", "Livello ${vm.level.value} Categoria ${vm.category.value} numeroTask $number TASK Task${number+1}");
    val myRef= Firebase.database.getReference("Esercizi")
    myRef.child(vm.category.value!!).child(vm.level.value!!).child("Task${number+1}").get().addOnSuccessListener {
        vm.setNextTask(it.value.toString())
    }.addOnFailureListener {
        Log.d("TASKMANAGER", "Error", it);
    }
}