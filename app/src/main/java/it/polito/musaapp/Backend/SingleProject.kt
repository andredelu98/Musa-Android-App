package it.polito.musaapp.Backend

import android.util.Log
import android.view.View
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
    //Log.d("COUNTERPROGETTIDELETE", i.toString())
   // vm.deleteProject(i)
    vm.setStatus(i, "eliminato")
    Firebase.database.getReference("Progetti").child("ListaProgetti")
        .child("Progetto${i}").child("Stato").setValue("eliminato")
    //vm.setCounterProgettiEliminati(vm.counterProgettiEliminati.value!!+1)
    //Firebase.database.getReference("Progetti").child("CounterProgettiEliminati").setValue(vm.counterProgettiEliminati.value)
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
            Log.d("PROJECTCOMPLETED", "valori ${it.value}");
            countCompletati = it.value.toString().toInt();
            vm.setCounterProgettiCompletati(countCompletati)
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
    /*if(count==-1){
        myRef.child("CounterProgettiCompletati").get().addOnSuccessListener {
           /// Log.d("PROGETTODB", "valori ${it.value}");
            vm.setCounterProgettiCompletati(it.value.toString().toInt());
        }.addOnFailureListener {
            Log.d("PROGETTODB", "Error", it);
        }
        myRef.child("CounterProgetti").get().addOnSuccessListener {
            //Log.d("PROGETTODB", "valori ${it.value}");
            count = it.value.toString().toInt();
        }.addOnFailureListener {
            Log.d("PROGETTODB", "Error", it);
        }
        vm.setCounterProgetti(count)
    }

    if(count!=-1) {
        Log.d("PROGETTO DB", "count = $count, prog. compl= ${vm.counterProgettiCompletati.value!!}")
        for (i in 0..(count - vm.counterProgettiCompletati.value!! - 1)) {
            var passed = false
            if (vm.projectList.value.isNullOrEmpty()) {
                // Log.d("PROGETTODB", "Progetto$i")
                myRef = Firebase.database.getReference("Progetti").child("ListaProgetti")
                    .child("Progetto$i")
                myRef.get().addOnSuccessListener {
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


                    /*if(nome!=nomeFirstSaved){
                    nomeFirstSaved=nome
                    val s: SingleProject= SingleProject(nome, categoria, descrizione, status)
                    vm.addNewProject(s)
                }*/
                if (!passed) {
                    if (vm.projectList.value.isNullOrEmpty() && status == "creato") {
                        Log.d("PROGETTODB", "progetto added, list empty")
                        val s: SingleProject = SingleProject(nome, categoria, descrizione, status)
                        nomeFirstSaved = nome
                        vm.addNewProject(s)
                    } else if (vm.projectList.value?.size!! < count-vm.counterProgettiCompletati.value!!-1) {
                        if (nome != nomeFirstSaved && status == "creato") {
                            Log.d("PROGETTODB", "progetto added, list NOT empty")
                            val s: SingleProject =
                                SingleProject(nome, categoria, descrizione, status)
                            vm.addNewProject(s)
                        }

                    }
                    passed = true
                }
                }.addOnFailureListener {
                    Log.d("FORM", "Error", it);
                }
            }
        }
    }*/
}


//@Composable
/*fun GetCompletedProjectsFromDb(vm:MusaViewModel) {
    vm.CleanProjectListCompleted()
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
    var myRef = Firebase.database.getReference("Progetti").child("CounterProgettiCompletati")
    if(count==-1){
        myRef.get().addOnSuccessListener {
           // Log.d("PROGETTODB", "valori ${it.value}");
            count = it.value.toString().toInt();
        }.addOnFailureListener {
            Log.d("PROGETTODB", "Error", it);
        }
        vm.setCounterProgettiCompletati(count)
    }

    for (i in 0..count-1){
       // Log.d("PROGETTODB", "Progetto$i")
        myRef = Firebase.database.getReference("Progetti").child("ListaProgettiCompletati")
            .child("Progetto$i")
        myRef.get().addOnSuccessListener {
            //Log.d("PROGETTODB", "valori ${it.value}");

            for(j in it.children){
                if(j.key!!.equals("Nome"))
                    nome= j.value.toString()
                else if(j.key!!.equals("Categoria"))
                    categoria=j.value.toString()
                else
                    descrizione=j.value.toString()
            }
            if(vm.projectListCompleted.value.isNullOrEmpty()){
                val s: SingleProject= SingleProject(nome, categoria, descrizione)
                vm.addProjectCompleted(s)
            }
            else if(vm.projectListCompleted.value?.size!! <count){
                val s: SingleProject= SingleProject(nome, categoria, descrizione)
                vm.addProjectCompleted(s)
            }

        }.addOnFailureListener {
            Log.d("FORM", "Error", it);
        }


    }

}
*/
fun getCounterProgettiEliminati(vm:MusaViewModel){
    val myRef = Firebase.database.getReference("Progetti").child("CounterProgettiEliminati")
    myRef.get().addOnSuccessListener {
        //Log.d("PROGETTODB", "valori ${it.value}");
        vm.setCounterProgettiEliminati(it.value.toString().toInt())
    }.addOnFailureListener {
        Log.d("FORM", "Error", it);
    }

}

fun ProjectCompleted(sp:SingleProject, vm:MusaViewModel, i: Int){
    Log.d("PROJECTCOMPLETED", "Sono in projectCompleted, $sp, $i, ${vm.counterProgettiCompletati.value!!}")
  //  DeleteSingleProject(vm,i)
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

fun DeleteSingleProjectCompleted(vm:MusaViewModel, i: Int){
    // Log.d("COUNTERPROGETTI", i.toString())
   // vm.deleteProjectCompleted(i)
   // val counterCompleted= vm.counterProgettiCompletati.value!!
  //  vm.setCounterProgettiCompletati(counterCompleted+1)
    Firebase.database.getReference("Progetti").child("ListaProgettiCompletati")
        .child("Progetto${i}").child("Stato").setValue("eliminatoDef")
}
