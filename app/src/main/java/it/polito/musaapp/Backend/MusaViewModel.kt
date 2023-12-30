package it.polito.musaapp.Backend

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class MusaViewModel : ViewModel() {

    private var _popUpHelp = MutableLiveData<Boolean>(false)
    var popUpHelp: LiveData<Boolean> = _popUpHelp
    fun setPopUpHelp(){
        _popUpHelp.value=true
    }
    fun unsetPopUpHelp(){
        _popUpHelp.value=false
    }
}