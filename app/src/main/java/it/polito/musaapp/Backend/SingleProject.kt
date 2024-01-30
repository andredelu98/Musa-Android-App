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
   // Log.d("COUNTERPROGETTI", i.toString())
    val s: SingleProject= SingleProject(name, category, description)
    vm.addNewProject(s)
    vm.setCounterProgetti(i+1)
    Firebase.database.getReference("Progetti").child("CounterProgetti").setValue(i+1)
    Firebase.database.getReference("Progetti").child("ListaProgetti").child("Progetto${i}").child("Nome").setValue(name);
    Firebase.database.getReference("Progetti").child("ListaProgetti").child("Progetto${i}").child("Categoria").setValue(category);
    Firebase.database.getReference("Progetti").child("ListaProgetti").child("Progetto${i}").child("Descrizione").setValue(description);
}


fun GetProjectsFromDb(vm:MusaViewModel) {
    var count=0
    var categoria:String=""
    var descrizione:String=""
    var nome:String=""
    var myRef = Firebase.database.getReference("Progetti").child("CounterProgetti")
    myRef.get().addOnSuccessListener {
        Log.d("PROGETTODB", "valori ${it.value}");
        count = it.value.toString().toInt();
    }.addOnFailureListener {
        Log.d("PROGETTODB", "Error", it);
    }
    vm.setCounterProgetti(count)
    for (i in 0..count){
        myRef = Firebase.database.getReference("Progetti").child("ListaProgetti")
            .child("Progetto$count")
        myRef.get().addOnSuccessListener {
            //Log.d("FORM", "valori ${it.value}");
           for(j in it.children){
               if(j.key!!.equals("Nome"))
                   nome=j.children.toString()
               else if(j.key!!.equals("Categoria"))
                   categoria=j.children.toString()
               else
                   descrizione=j.children.toString()
           }
        }.addOnFailureListener {
            Log.d("FORM", "Error", it);
        }
        val s: SingleProject= SingleProject(nome, categoria, descrizione)
        vm.addNewProject(s)
    }
}
