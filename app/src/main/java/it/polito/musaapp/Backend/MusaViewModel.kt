package it.polito.musaapp.Backend

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class MusaViewModel : ViewModel() {

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
    fun setNextTask(s: String){
        _nextTask.value=s
    }
}