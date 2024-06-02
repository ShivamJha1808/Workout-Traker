package com.shivamjsr18.workouttraker.services

import com.shivamjsr18.workouttraker.model.Exercise
import com.shivamjsr18.workouttraker.model.IndividualSet
import kotlinx.coroutines.flow.Flow
import java.util.Date

interface StorageService {
    val exercises : Flow<List<Exercise>>

    suspend fun getExercise(exerciseId: String): Exercise?
    suspend fun saveExercise(exercise: Exercise): String
    suspend fun updateExercise(exercise: Exercise)
    suspend fun deleteExercise(exercise: Exercise)

    suspend fun getIndividualSetsOfExercise(exercise: Exercise): Flow<List<IndividualSet>>
    suspend fun getLastIndividualSetOfExercise(exercise: Exercise): IndividualSet?
    suspend fun saveIndividualSetOfExercise(exercise: Exercise,individualSet: IndividualSet) : String
    suspend fun updateIndividualSetOfExercise(exercise: Exercise,individualSet: IndividualSet)
    suspend fun deleteIndividualSetOfExercise(exercise: Exercise,individualSet: IndividualSet)
}