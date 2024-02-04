package it.polito.musaapp.Backend

import android.util.Log
import com.google.firebase.Firebase
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.database


data class SingleReference(
    val key: Int,
    var url: String
)

fun RefreshSavedReference(vm: MusaViewModel){
    vm.deleteAllReference()
    vm.setReferenceCounter(0)
    vm.setReferenceDBCounter(0)
    val myRef= FirebaseDatabase.getInstance().getReference("ReferenceSalvate")
  //  Log.d("REFERENCE", "Task${vm.taskCounter.value}")
    myRef.get().addOnSuccessListener {
      //  Log.d("REFERENCE", "${it.value}")
        for(i in it.children){
          //  Log.d("Single REFERENCE", "${i.value!!}")
            if(vm.savedRef.value?.contains(i.value.toString())==false){
                vm.setReferenceCounter(vm.referenceCounter.value!!+1)
                vm.setReferenceDBCounter(vm.referenceDBCounter.value!!+1)
                vm.addRefToSave(i.value.toString())
                vm.addRefToSaveDB(i.key.toString().toInt(), i.value.toString())
            }
        }
        Log.d("REFERENCE", "counter ${vm.referenceCounter.value}, reference salvate dal refresh${vm.savedRef.value}")

    }.addOnFailureListener {
        Log.d("REFERENCE", "Error", it);
    }
}

fun InsertImageInSaved(s: String, vm: MusaViewModel){
    if(vm.savedRef.value.isNullOrEmpty()){
        vm.setReferenceCounter(0)
        vm.addRefToSave(s)
        vm.setReferenceDBCounter(0)
       // Log.d("REFERENCESALVATI", "${vm.savedRef.value!!.size}, $s" )
        vm.addRefToSaveDB(vm.counterProgetti.value!!, s)
        Firebase.database.getReference("ReferenceSalvate").child("${vm.referenceCounter.value}")
            .setValue(s)
        vm.setReferenceCounter(vm.referenceCounter.value!!+1)
        vm.setReferenceDBCounter(vm.referenceDBCounter.value!!+1)
    }
    else if(!vm.savedRef.value!!.contains(s)){
        //Log.d("REFERENCESALVATI", "${vm.savedRef.value!!.size}, $s" )
        vm.addRefToSave(s)
        vm.addRefToSaveDB(vm.counterProgetti.value!!, s)
        Firebase.database.getReference("ReferenceSalvate").child("${vm.referenceCounter.value}")
            .setValue(s)
        vm.setReferenceCounter(vm.referenceCounter.value!!+1)
        vm.setReferenceDBCounter(vm.referenceDBCounter.value!!+1)
    }

}

fun RemoveImageInSaved(s: String, vm: MusaViewModel){
    Log.d("SALVATIREM", "${vm.getRefToRemove(s)}, $s" )
    val rem= vm.getRefToRemove(s)
    if(rem!=-1){
        Log.d("SALVATIREM", "da rimuovere il count $rem, $s" )
        Firebase.database.getReference("ReferenceSalvate").child("$rem").setValue("RIMOSSO")
        vm.removeRefToSave(s)
    }

}
