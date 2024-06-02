package com.shivamjsr18.workouttraker.screens.exercise

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.shivamjsr18.workouttraker.model.Exercise
import com.shivamjsr18.workouttraker.model.IndividualSet
import com.shivamjsr18.workouttraker.model.MuscleGroup
import com.shivamjsr18.workouttraker.services.StorageService
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.util.Date
import javax.inject.Inject

@HiltViewModel
class ExerciseViewModel @Inject constructor(
    private val storageService: StorageService
): ViewModel() {
    var isShowingAddDialogue by mutableStateOf(false)
        private set
    var newExerciseName by mutableStateOf("")
        private set

    var isShowingAddSetDialogue by mutableStateOf(false)
        private set
    var newSet by mutableStateOf(IndividualSet())
        private set
    var newSetState by mutableStateOf(IndividualSetState())
        private set
    var exerciseBeingAddedTo by mutableStateOf(Exercise())
        private set

    var listExercises by mutableStateOf(listOf(Exercise()))
        private set

    init {
        storageService.exercises.onEach {
            listExercises= it
        }.launchIn(viewModelScope)
    }




    fun showAddDialogue(){
        newExerciseName=""
        isShowingAddDialogue=true
    }
    fun dismissAddDialogue(){
        isShowingAddDialogue=false
    }
    fun onNewExerciseNameChange(newName: String){
        newExerciseName=newName
    }

    fun onAddExercise(muscleGroup: MuscleGroup) {

        viewModelScope.launch{
            if(newExerciseName.isEmpty()){
                dismissAddDialogue()
            }
            else{
                val newExercise = Exercise(name=newExerciseName, muscleGroup = muscleGroup)
                storageService.saveExercise(newExercise)
                storageService.exercises.onEach {
                    listExercises= it
                }.launchIn(viewModelScope)
            }
            dismissAddDialogue()
        }

    }

    fun showAddSetDialogue(exercise: Exercise){
        var lastSet:IndividualSet?
        val simpleDateFormat = SimpleDateFormat.getDateInstance()
        val currentDate = simpleDateFormat.format(Date())

        viewModelScope.launch(Dispatchers.IO) {
            lastSet = storageService.getLastIndividualSetOfExercise(exercise)
            newSet = if(lastSet==null){
                IndividualSet()
                }
                else{
                    val setDate = simpleDateFormat.format(lastSet!!.createdAt)
                    if(currentDate==setDate) lastSet!!
                    else IndividualSet()
                }
            newSetState= IndividualSetState(
                set1=newSet.set1.toString(),
                set2=newSet.set2.toString(),
                set3=newSet.set3.toString(),
                set4=newSet.set4.toString(),
                set5=newSet.set5.toString(),
                remarks=newSet.remarks)
            exerciseBeingAddedTo= exercise
            isShowingAddSetDialogue=true
        }
    }
    fun dismissAddSetDialogue(){
        isShowingAddSetDialogue=false
    }
    fun onSet1Change(newValue:String) {
        newSetState=newSetState.copy(set1=newValue)
    }
    fun onSet2Change(newValue:String) {
        newSetState=newSetState.copy(set2=newValue)
    }
    fun onSet3Change(newValue:String) {
        newSetState=newSetState.copy(set3=newValue)
    }
    fun onSet4Change(newValue:String) {
        newSetState=newSetState.copy(set4=newValue)
    }
    fun onSet5Change(newValue:String) {
        newSetState=newSetState.copy(set5=newValue)
    }
    fun onRemarkChange(newValue:String) {
        newSetState=newSetState.copy(remarks = newValue)
    }
    fun onAddNewSet(exercise: Exercise){
        newSet=newSet.copy(
            set1=newSetState.set1.toDoubleOrNull()?:0.0,
            set2=newSetState.set2.toDoubleOrNull()?:0.0,
            set3=newSetState.set3.toDoubleOrNull()?:0.0,
            set4=newSetState.set4.toDoubleOrNull()?:0.0,
            set5=newSetState.set5.toDoubleOrNull()?:0.0,
            remarks=newSetState.remarks

        )
        viewModelScope.launch(Dispatchers.IO) {
            if(newSet.id==""){
                storageService.saveIndividualSetOfExercise(exercise,newSet)
            }
            else {
                storageService.updateIndividualSetOfExercise(exercise, newSet)
            }
        }
        dismissAddSetDialogue()
    }

}