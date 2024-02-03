package it.polito.musaapp.Backend

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import com.google.firebase.Firebase
import com.google.firebase.database.database


data class SingleProject(
    var name: String,
    val category: String,
    val description: String,
    var status: String,
)


fun CreateNewProject(name: String, category: String, description: String, vm:MusaViewModel, i: Int){
    Log.d("COUNTERPROGETTI", i.toString())
    val s: SingleProject= SingleProject(name, category, description, "creato")
    vm.addNewProject(s)
    vm.setCounterProgetti(i+1)
    Firebase.database.getReference("Progetti").child("CounterProgetti").setValue(i+1)
    Firebase.database.getReference("Progetti").child("ListaProgetti").child("Progetto${i}").child("Nome").setValue(name);
    Firebase.database.getReference("Progetti").child("ListaProgetti").child("Progetto${i}").child("Categoria").setValue(category);
    Firebase.database.getReference("Progetti").child("ListaProgetti").child("Progetto${i}").child("Descrizione").setValue(description);
    Firebase.database.getReference("Progetti").child("ListaProgetti").child("Progetto${i}").child("Stato").setValue("creato");
}

fun ModifySingleProject(name: String, category: String, description: String, vm:MusaViewModel, i: Int){
    Log.d("COUNTERPROGETTI", i.toString())
    val s: SingleProject= SingleProject(name, category, description, "creato")
    vm.setProjectModified(s, i)
    Firebase.database.getReference("Progetti").child("ListaProgetti").child("Progetto${i}").child("Nome").setValue(name);
    Firebase.database.getReference("Progetti").child("ListaProgetti").child("Progetto${i}").child("Categoria").setValue(category);
    Firebase.database.getReference("Progetti").child("ListaProgetti").child("Progetto${i}").child("Descrizione").setValue(description);

}

fun DeleteSingleProject(vm:MusaViewModel, i: Int){
    vm.setStatus(i, "eliminato")
    Firebase.database.getReference("Progetti").child("ListaProgetti")
        .child("Progetto${i}").child("Stato").setValue("eliminato")
}


@Composable
fun GetProjectsFromDb(vm:MusaViewModel) {

    var count by remember {
        mutableIntStateOf(-1)
    }
    var categoria by remember {
        mutableStateOf("")
    }
    var descrizione by remember {
        mutableStateOf("")
    }
    var status by remember {
        mutableStateOf("")
    }
    var nome by remember {
        mutableStateOf("")
    }

    var addingFinished by remember {
        mutableStateOf(false)
    }
    val list = mutableListOf<String>("")

    val myRef = Firebase.database.getReference("Progetti")
    myRef.child("CounterProgetti").get().addOnSuccessListener {
        //Log.d("PROGETTODB", "valori ${it.value}");
        vm.setCounterProgetti(it.value.toString().toInt())
    }.addOnFailureListener {
        Log.d("FORM", "Error", it);
    }
    val countProgetti by vm.counterProgetti.observeAsState()

    var countCompletati=-1
    val myRef2 = Firebase.database.getReference("Progetti").child("CounterProgettiCompletati")
    if(countCompletati==-1){
        myRef2.get().addOnSuccessListener {
            //Log.d("PROJECTCOMPLETED", "valori ${it.value}");
            countCompletati = it.value.toString().toInt();
            vm.setCounterProgettiCompletati(countCompletati)
        }.addOnFailureListener {
            Log.d("PROJECTCOMPLETED", "Error", it);
        }
    }

    var countEliminati=-1
    val myRef3 = Firebase.database.getReference("Progetti").child("CounterProgettiEliminati")
    if(countEliminati==-1){
        myRef3.get().addOnSuccessListener {
            Log.d("PROJECTELIMINATI", "valori ${it.value}");
            countEliminati= it.value.toString().toInt();
            vm.setCounterProgettiEliminati(countEliminati)
        }.addOnFailureListener {
            Log.d("PROJECTCOMPLETED", "Error", it);
        }
    }

    if(countProgetti!=null&&!addingFinished){
        vm.CleanProjectList()
        for(i in 0..countProgetti!!-1){
            myRef.child("ListaProgetti").child("Progetto$i").get().addOnSuccessListener {
                //Log.d("PROGETTODB", "valori ${it.value}");
                for (j in it.children) {
                    if (j.key!!.equals("Nome"))
                        nome = j.value.toString()
                    else if (j.key!!.equals("Categoria"))
                        categoria = j.value.toString()
                    else if (j.key!!.equals("Descrizione"))
                        descrizione = j.value.toString()
                    else
                        status = j.value.toString()
                }

                if(!list.contains(nome)&& !addingFinished){
                    list.add(nome)
                    vm.addNewProject(SingleProject(nome, categoria, descrizione, status))
                    Log.d("LISTA GETDB", "lista ${list}, numero di progetti aggiunti ${vm.projectList.value!!.count()!!}")
                    if(vm.projectList.value!!.count() == countProgetti!!){
                       addingFinished=true
                       Log.d("LISTA GETDB FINALE", "listafin ${vm.projectList.value}, numero di progetti aggiunti ${vm.projectList.value!!.count()!!}")
                    }
                }
            }.addOnFailureListener {
                Log.d("FORM", "Error", it);
            }
        }
    }

}

fun ProjectCompleted(sp:SingleProject, vm:MusaViewModel, i: Int){
    Log.d("PROJECTCOMPLETED", "Sono in projectCompleted, $sp, $i, ${vm.counterProgettiCompletati.value!!}")
   //DeleteSingleProject(vm,i)
   // vm.addProjectCompleted(s)
    val counterCompleted= vm.counterProgettiCompletati.value!!
    vm.setCounterProgettiCompletati(counterCompleted+1)
    val compl: String="completato"
    vm.setStatus(i, compl)
    Firebase.database.getReference("Progetti").child("CounterProgettiCompletati")
        .setValue(counterCompleted+1)
   /* Firebase.database.getReference("Progetti").child("ListaProgettiCompletati")
        .child("Progetto${i}").child("Nome").setValue(s.name);
    Firebase.database.getReference("Progetti").child("ListaProgettiCompletati")
        .child("Progetto${i}").child("Categoria").setValue(s.category);
    Firebase.database.getReference("Progetti").child("ListaProgettiCompletati")
        .child("Progetto${i}").child("Descrizione").setValue(s.description);*/


    Firebase.database.getReference("Progetti").child("ListaProgetti")
        .child("Progetto${i}").child("Stato").setValue("completato")
}

