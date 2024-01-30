package it.polito.musaapp.Backend

import android.util.Log
import android.view.View
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import com.google.firebase.Firebase
import com.google.firebase.database.database


data class SingleProject(
    val name: String,
    val category: String,
    val description: String,
)
fun CreateNewProject(name: String, category: String, description: String, vm:MusaViewModel, i: Int){
   // Log.d("COUNTERPROGETTI", i.toString())
    val s: SingleProject= SingleProject(name, category, description)
    vm.addNewProject(s)
    vm.setCounterProgetti(i+1)
    Firebase.database.getReference("Progetti").child("CounterProgetti").setValue(i+1)
    Firebase.database.getReference("Progetti").child("ListaProgetti").child("Progetto${i}").child("Nome").setValue(name);
    Firebase.database.getReference("Progetti").child("ListaProgetti").child("Progetto${i}").child("Categoria").setValue(category);
    Firebase.database.getReference("Progetti").child("ListaProgetti").child("Progetto${i}").child("Descrizione").setValue(description);
    Firebase.database.getReference("Progetti").child("ListaProgetti").child("Progetto${i}").child("Stato").setValue("Creato");
}

fun ModifySingleProject(name: String, category: String, description: String, vm:MusaViewModel, i: Int){
    // Log.d("COUNTERPROGETTI", i.toString())
    val s: SingleProject= SingleProject(name, category, description)
    vm.setProjectModified(s, i)
    Firebase.database.getReference("Progetti").child("ListaProgetti").child("Progetto${i}").child("Nome").setValue(name);
    Firebase.database.getReference("Progetti").child("ListaProgetti").child("Progetto${i}").child("Categoria").setValue(category);
    Firebase.database.getReference("Progetti").child("ListaProgetti").child("Progetto${i}").child("Descrizione").setValue(description);
}

fun DeleteSingleProject( vm:MusaViewModel, i: Int){
    // Log.d("COUNTERPROGETTI", i.toString())
    vm.deleteProject(i)
    Firebase.database.getReference("Progetti").child("ListaProgetti").child("Progetto${i}").child("Stato").setValue("Eliminato");
}

@Composable
fun GetProjectsFromDb(vm:MusaViewModel) {
    vm.CleanProjectList()
    var count by remember {
        mutableIntStateOf(-1)
    }
    var categoria by remember {
        mutableStateOf("")
    }
    var descrizione by remember {
        mutableStateOf("")
    }
    var nome by remember {
        mutableStateOf("")
    }
    var myRef = Firebase.database.getReference("Progetti").child("CounterProgetti")
    if(count==-1){
        myRef.get().addOnSuccessListener {
            Log.d("PROGETTODB", "valori ${it.value}");
            count = it.value.toString().toInt();
        }.addOnFailureListener {
            Log.d("PROGETTODB", "Error", it);
        }
        vm.setCounterProgetti(count)
    }

    for (i in 0..count-1){
        Log.d("PROGETTODB", "Progetto$i")
        myRef = Firebase.database.getReference("Progetti").child("ListaProgetti")
            .child("Progetto$i")
        myRef.get().addOnSuccessListener {
            Log.d("PROGETTODB", "valori ${it.value}");

           for(j in it.children){
               if(j.key!!.equals("Nome"))
                   nome= j.value.toString()
               else if(j.key!!.equals("Categoria"))
                   categoria=j.value.toString()
               else
                   descrizione=j.value.toString()
           }
            if(vm.projectList.value.isNullOrEmpty()){
                val s: SingleProject= SingleProject(nome, categoria, descrizione)
                vm.addNewProject(s)
            }
            else if(vm.projectList.value?.size!! <count){
                val s: SingleProject= SingleProject(nome, categoria, descrizione)
                vm.addNewProject(s)
            }

        }.addOnFailureListener {
            Log.d("FORM", "Error", it);
        }


    }

}
