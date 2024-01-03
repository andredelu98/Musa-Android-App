package it.polito.musaapp.Backend

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class MusaViewModel : ViewModel() {

    //VARIABILI PROFILO DELLA PERSONA
    private var _category = MutableLiveData<String>("")
    var category: LiveData<String> = _category
    fun setCategory(cat :String){
        _category.value=cat
    }

    private var _level = MutableLiveData<String>("")
    var level: LiveData<String> = _level
    fun setLevel(lev :String){
        _level.value=lev
    }

    private var _name= MutableLiveData<String>("")
    var name: LiveData<String> = _name
    fun setName(s:String){
        _name.value=s
    }

    private var _mail= MutableLiveData<String>("")
    var mail: LiveData<String> = _mail
    fun setMail(s:String){
        _mail.value=s
    }

    private var _professione= MutableLiveData<String>("")
    var professione: LiveData<String> = _professione
    fun setProfessione(s:String){
        _professione.value=s
    }

    //VARIABILI MODULO ESERCIZI CREATIVITA
    private var _daysEx = MutableLiveData<Int>(0)
    var daysEx: LiveData<Int> = _daysEx
    fun setDaysEx(n: Int){
        _daysEx.value=n
    }

    private var _weeksEx = MutableLiveData<Int>(0)
    var weeksEx: LiveData<Int> = _weeksEx
    fun setWeeksEx(n: Int){
        _weeksEx.value=n
    }


    private var _nextTask = MutableLiveData<String>("")
    var nextTask: LiveData<String> = _nextTask
    fun setNextTask(i :Int){
        if(i<=weeksEx.value!!*daysEx.value!!){
            Log.d("NEXTTASK", TaskList.value?.get(i).toString())
            _nextTask.value=TaskList.value?.get(i).toString()

        }

    }

    private var _taskList = MutableLiveData<List<String>>()
    var TaskList: LiveData<List<String>> = _taskList
    fun setTaskList(s: MutableList<String>){
        _taskList.value=s
    }




}