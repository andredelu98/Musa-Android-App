package it.polito.musaapp.Frontend

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.google.firebase.Firebase
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.storage
import it.polito.musaapp.Backend.MusaViewModel


fun GetReferenceTask(vm: MusaViewModel){
    val listUrl: MutableList<String> = mutableListOf()
    val myRef= FirebaseDatabase.getInstance().getReference("Reference").child(vm.category.value!!)
        .child(vm.level.value!!).child("Task${vm.taskCounter.value}")
    Log.d("REFERENCE", "Task${vm.taskCounter.value}")
    myRef.get().addOnSuccessListener {
        Log.d("REFERENCE", "${it.value}")
        for(i in it.children){
            Log.d("Single REFERENCE", "${i.value!!}")
            listUrl.add(i.value!!.toString())
        }
        Log.d("REFERENCE1", "$listUrl")
        vm.setReferenceListUrl(listUrl)
    }.addOnFailureListener {
        Log.d("TASKMANAGER", "Error", it);
    }


}
@Composable
fun TaskReference(navController: NavController, vm:MusaViewModel) {
    //val storageRef = FirebaseStorage.getInstance().getReference("ReferenceTask1Arte")
    val list by vm.referenceListUrl.observeAsState()
    Column(){
        for(i in list!!){
            AsyncImage(
                model = i,
                contentDescription = null,
            )
        }
    }




}