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
import com.google.firebase.Firebase
import com.google.firebase.database.database
import it.polito.musaapp.Screens

class MusaViewModel : ViewModel() {

    private var _registered= MutableLiveData<Boolean>(false)
    var registered: LiveData<Boolean> = _registered

    fun setRegistered(value: Boolean){
        _registered.value=value
    }

    private var _tutorialActive= MutableLiveData<Boolean>(false)
    var tutorialActive: LiveData<Boolean> = _tutorialActive

    fun setTutorial(value: Boolean){
        _tutorialActive.value=value
    }

    private var _previousScreen = MutableLiveData<Screens>(null)
    var previousScreen: LiveData<Screens> = _previousScreen
    fun setPreviousScreen(value: Screens){
        _previousScreen.value = value
    }

    private var _fromProjectList= MutableLiveData<Boolean>(false)
    var fromProjectList: LiveData<Boolean> = _fromProjectList

    fun setfromProjectList(value: Boolean){
        _fromProjectList.value=value
    }

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

    private var _taskCompleted = MutableLiveData<Int>(0)
    var taskCompleted: LiveData<Int> = _taskCompleted
    fun setTaskCompleted(n: Int){
        _taskCompleted.value=n
    }

    private var _daysListEx = MutableLiveData<MutableList<Boolean>>()
    var daysListEx: LiveData<MutableList<Boolean>> = _daysListEx
    fun setDaysListEx(n: MutableList<Boolean>){
        _daysListEx.value=n
    }

    fun getDaysListEx(): MutableList<Boolean>{
        return _daysListEx.value!!
    }
    private var _nextTask = MutableLiveData<String>("")
    var nextTask: LiveData<String> = _nextTask
    fun setNextTask(i :Int){
        if(i<=weeksEx.value!!*daysEx.value!!&&i<TaskList.value?.count()!!){
            Log.d("NEXTTASK", TaskList.value?.get(i).toString())
            _nextTask.value=TaskList.value?.get(i).toString()
        }
    }

    private var _taskCounter= MutableLiveData<Int>(0)
    var taskCounter: LiveData<Int> = _taskCounter
    fun setTaskCounter(i :Int){
        _taskCounter.value=i
        //Log.d("NEXTTASK",_taskCounter.value.toString())
    }

    private var _taskRefreshed= MutableLiveData<Int>(0)
    var taskRefreshed: LiveData<Int> = _taskRefreshed
    fun setTaskRefreshed(i :Int){
        _taskRefreshed.value=i
        //Log.d("NEXTTASK",_taskCounter.value.toString())
    }

    private var _taskList = MutableLiveData<List<String>>()
    var TaskList: LiveData<List<String>> = _taskList
    fun setTaskList(s: MutableList<String>){
        _taskList.value=s
    }

    fun resetTaskList(){
        _taskList.value= emptyList()
    }


    fun changeTaskListRefresh(i: Int, newTaskInt: Int){
        if(_taskList.value!!.count()>newTaskInt){
            _taskList.value?.get(newTaskInt)?.let { _taskList.value?.toMutableList()?.set(i, it) }
            _taskList.value?.get(newTaskInt)?.let { ModifyDbRefresh(i, it, this) }
        }
        else{
            var j= newTaskInt
            j = j - _taskRefreshed.value!!
            _taskRefreshed.value=0
            _taskList.value?.get(j)?.let { _taskList.value?.toMutableList()?.set(i, it) }
            _taskList.value?.get(j)?.let { ModifyDbRefresh(i, it, this) }
        }
    }

    private var _referenceListUrl = MutableLiveData<List<String>>()
    var referenceListUrl: LiveData<List<String>> = _referenceListUrl
    fun setReferenceListUrl(s: MutableList<String>){
        _referenceListUrl.value=s
    }

    private var _referenceListUrlProject = MutableLiveData<List<String>>()
    var referenceListUrlProject: LiveData<List<String>> = _referenceListUrlProject
    fun setReferenceListUrlProject(s: MutableList<String>){
        _referenceListUrlProject.value=s
    }


    private var _taskDueDate = MutableLiveData<List<String>>()
    var taskDueDate: LiveData<List<String>> = _taskDueDate

    fun createDueDateArray(s: MutableList<String>){
       _taskDueDate.value=s
    }


    //PROGETTI

    private var _projectList= MutableLiveData<List<SingleProject>>()
    var projectList: LiveData<List<SingleProject>> = _projectList
    fun addNewProject(s: SingleProject) {
        var l= mutableListOf<SingleProject>()
        if(_projectList.value.isNullOrEmpty())
            l.add(s)
        else{
            l= _projectList.value as MutableList<SingleProject>
            l.add(s)
        }
        _projectList.value = l
        Log.d("PROGETTODB", "lista progetti added: ${_projectList.value.toString()}")
    }

    private var _projectListCompleted= MutableLiveData<List<SingleProject>>()
    var projectListCompleted: LiveData<List<SingleProject>> = _projectListCompleted
    fun addProjectCompleted(s: SingleProject) {
        var l= mutableListOf<SingleProject>()
        if(_projectListCompleted.value.isNullOrEmpty())
            l.add(s)
        else{
            l= _projectListCompleted.value as MutableList<SingleProject>
            l.add(s)
        }
        _projectListCompleted.value = l
        //Log.d("LISTAPROGETTI", _projectList.value.toString())
    }

    fun CleanProjectList(){
        _projectList.value=null
    }
    fun CleanProjectListCompleted(){
        _projectListCompleted.value=null
    }

    private var _counterProgetti=MutableLiveData<Int>()
    var counterProgetti: LiveData<Int> = _counterProgetti

    fun setCounterProgetti(i : Int){
        _counterProgetti.value= i
    }
    private var _counterProgettiCompletati=MutableLiveData<Int>()
    var counterProgettiCompletati: LiveData<Int> = _counterProgettiCompletati

    fun setCounterProgettiCompletati(i : Int){
        _counterProgettiCompletati.value= i
    }

    private var _counterProgettiEliminati=MutableLiveData<Int>()
    var counterProgettiEliminati: LiveData<Int> = _counterProgettiEliminati

    fun setCounterProgettiEliminati(i : Int){
        _counterProgettiEliminati.value= i
    }

    private var _projectToPrint=MutableLiveData<SingleProject>()
    var projectToPrint: LiveData<SingleProject> = _projectToPrint

    fun setProjectToPrint(s: SingleProject){
        _projectToPrint.value=s
    }

    private var _projectToPrintCounter=MutableLiveData<Int>()
    var projectToPrintCounter: LiveData<Int> = _projectToPrintCounter

    fun setProjectToPrintCounter(i: Int){
        _projectToPrintCounter.value=i
    }

    private var _projectToModify=MutableLiveData<SingleProject>()
    var projectToModify: LiveData<SingleProject> = _projectToModify

    fun setProjectToModify(i:Int){
        _projectToModify.value=_projectList.value!!.get(i)
    }

    private var _projectToModifyCount=MutableLiveData<Int>()
    var projectToModifyCount: LiveData<Int> = _projectToModifyCount

    fun setProjectToModifyCount(i:Int){
        _projectToModifyCount.value=i
    }

    fun setProjectModified(s: SingleProject, i: Int) {
        var l = mutableListOf<SingleProject>()
        l= _projectList.value as MutableList<SingleProject>
        l[i]=s
        _projectList.value=l
    }
    fun deleteProject(i: Int) {
        var l = mutableListOf<SingleProject>()
        l= _projectList.value as MutableList<SingleProject>
        l[i].status="eliminato"
        _projectList.value=l
       // Firebase.database.getReference("Progetti").child("CounterProgetti").setValue(_projectList.value!!.size)
    }

    fun deleteProjectCompleted(i: Int) {
        var l = mutableListOf<SingleProject>()
        l= _projectListCompleted.value as MutableList<SingleProject>
        l[i].status="eliminatodef"
        _projectListCompleted.value=l
        // Firebase.database.getReference("Progetti").child("CounterProgetti").setValue(_projectList.value!!.size)
    }


    private var _projectToDelete=MutableLiveData<Int>(-1)
    var projectToDelete: LiveData<Int> = _projectToDelete

    fun setProjectToDelete(i : Int){
        _projectToDelete.value= i
    }
    fun setStatus(i:Int, s:String){
        var l = mutableListOf<SingleProject>()
        l= _projectList.value as MutableList<SingleProject>
        l[i].status=s
        _projectList.value=l
    }












    //SAVED REFERENCE
    private var _savedRef= MutableLiveData<List<String>>()
    var savedRef: LiveData<List<String>> = _savedRef
    fun addRefToSave(s: String) {
        //_savedRef.value?.toString()?.let { Log.d("SALVATI", it) }
        var l= mutableListOf<String>()
        if(_savedRef.value.isNullOrEmpty())
            l.add(s)
        else{
            l= _savedRef.value as MutableList<String>
            l.add(s)
        }
        _savedRef.value = l
       // _savedRef.value?.toString()?.let { Log.d("SALVATIFINEADD", it) }
        //Log.d("LISTAPROGETTI", _projectList.value.toString())
    }
    fun removeRefToSave(s: String) {
        var l = mutableListOf<String>()
       // _savedRef.value?.toString()?.let { Log.d("SALVATIREMOVE", it) }
        l= _savedRef.value as MutableList<String>
        for(i in 0.. _counterProgetti.value!!-1){
            if(l[i].equals(s)){
               l.set(i, "RIMOSSO")
            }
        }
        _savedRef.value=l

        var l2 = mutableListOf<SingleReference>()
        l2=_savedRefDB.value as MutableList<SingleReference>
        val c=getRefToRemove(s)
        if(c!=-1){
            l2?.get(c)?.url="RIMOSSO"
        }
        _savedRefDB.value=l2
       // _savedRef.value?.toString()?.let { Log.d("SALVATIREMOVEFINE", it) }
    }

    fun getRefToRemove(s: String): Int{
        var l = mutableListOf<SingleReference>()
        var valToRemove=-1
        var valToReturn=-1
        l=_savedRefDB.value as MutableList<SingleReference>
        //Log.d("SALVATIREMOVE", "${_savedRefDB.value!!.count()}")
        for(i in 0.. _savedRefDB.value!!.count()-1){
            if(_savedRefDB.value!![i].url.equals(s)){
               // Log.d("SALVATIREMOVE", "${_savedRefDB.value!![i].key} da rimuovere")
                valToReturn=_savedRefDB.value!![i].key
                return valToReturn
            }
        }
        return valToReturn
    }

    fun deleteAllReference(){
        val l = mutableListOf<String>()
        val l2= mutableListOf<SingleReference>()
        _savedRef.value=l
        _savedRefDB.value=l2
    }

    private var _referenceCounter=MutableLiveData<Int>()
    var referenceCounter: LiveData<Int> = _referenceCounter

    fun setReferenceCounter(i:Int){
        _referenceCounter.value=i
    }

    private var _savedRefDB= MutableLiveData<List<SingleReference>>()
    var savedRefDB: LiveData<List<SingleReference>> = _savedRefDB
    fun addRefToSaveDB(key: Int, s: String) {
        //_savedRef.value?.toString()?.let { Log.d("SALVATI", it) }
        var l= mutableListOf<SingleReference>()
        if(_savedRefDB.value.isNullOrEmpty())
            l.add(SingleReference(key,s))
        else{
            l= _savedRefDB.value as MutableList<SingleReference>
            l.add(SingleReference(key,s))
        }
        _savedRefDB.value = l
        // _savedRef.value?.toString()?.let { Log.d("SALVATIFINEADD", it) }
        //Log.d("LISTAPROGETTI", _projectList.value.toString())
    }

    private var _referenceDBCounter=MutableLiveData<Int>()
    var referenceDBCounter: LiveData<Int> = _referenceDBCounter

    fun setReferenceDBCounter(i:Int){
        _referenceDBCounter.value=i
    }


    private var _popUpOpened= MutableLiveData<Boolean>()
    var popUpOpened: LiveData<Boolean> = _popUpOpened

    fun setPopUpOpened(b: Boolean){
        _popUpOpened.value=b
    }
}


