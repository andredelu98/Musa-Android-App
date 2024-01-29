package it.polito.musaapp.Backend

import android.util.Log
import android.view.View
import com.google.firebase.Firebase
import com.google.firebase.database.database


data class SingleProject(
    val name: String,
    val category: String,
    val description: String,
)
fun CreateNewProject(name: String, category: String, description: String, vm:MusaViewModel, i: Int){
    Log.d("COUNTERPROGETTI", i.toString())
    val s: SingleProject= SingleProject(name, category, description)
    vm.addNewProject(s)
    Firebase.database.getReference("Progetti").child("CounterProgetti").setValue(i+1)
    Firebase.database.getReference("Progetti").child("ListaProgetti").child("Progetto${i}").child("Nome").setValue(name);
    Firebase.database.getReference("Progetti").child("ListaProgetti").child("Progetto${i}").child("Categoria").setValue(category);
    Firebase.database.getReference("Progetti").child("ListaProgetti").child("Progetto${i}").child("Descrizione").setValue(description);

}