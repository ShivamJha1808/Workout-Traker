package com.shivamjsr18.workouttraker.services.implimentation

import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.dataObjects
import com.google.firebase.firestore.toObject
import com.shivamjsr18.workouttraker.model.Exercise
import com.shivamjsr18.workouttraker.model.IndividualSet
import com.shivamjsr18.workouttraker.services.AccountService
import com.shivamjsr18.workouttraker.services.StorageService
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.tasks.await
import java.util.Date
import javax.inject.Inject

class StorageServiceImpl @Inject constructor(
    private val firestore: FirebaseFirestore,
    private val accountService: AccountService
): StorageService {

    @OptIn(ExperimentalCoroutinesApi::class)
    override val exercises: Flow<List<Exercise>>
        get() = accountService.currentUser.flatMapLatest {user->
            firestore.collection(EXERCISE_COLLECTION)
                .whereEqualTo(USER_ID_FIELD,user.id)
                .orderBy(NAME_FIELD, Query.Direction.DESCENDING)
                .dataObjects()
        }

    override suspend fun getExercise(exerciseId: String): Exercise? {
        return firestore.collection(EXERCISE_COLLECTION)
            .document(exerciseId)
            .get()
            .await()
            .toObject()
    }

    override suspend fun saveExercise(exercise: Exercise): String {
        val exerciseWithId = exercise.copy(userId = accountService.currentUserId)
        return firestore.collection(EXERCISE_COLLECTION)
            .add(exerciseWithId)
            .await()
            .id
    }

    override suspend fun updateExercise(exercise: Exercise) {
        firestore.collection(EXERCISE_COLLECTION)
            .document(exercise.id)
            .set(exercise)
            .await()
    }

    override suspend fun deleteExercise(exercise: Exercise) {
        val lst = firestore.collection(EXERCISE_COLLECTION)
            .document(exercise.id)
            .collection(INDIVIDUAL_SET_COLLECTION)
            .get()
            .await()
        lst.forEach {individualSet->
            deleteIndividualSetOfExercise(exercise,individualSet.toObject())
        }
        firestore.collection(EXERCISE_COLLECTION)
            .document(exercise.id)
            .delete()
            .await()
    }

    override suspend fun saveIndividualSetOfExercise(exercise: Exercise, individualSet: IndividualSet): String {
        val newIndividualSet = individualSet.copy(exId = exercise.id)
        return firestore.collection(EXERCISE_COLLECTION)
            .document(exercise.id)
            .collection(INDIVIDUAL_SET_COLLECTION)
            .add(newIndividualSet)
            .await()
            .id
    }

    override suspend fun updateIndividualSetOfExercise(exercise: Exercise, individualSet: IndividualSet) {
        firestore.collection(EXERCISE_COLLECTION)
            .document(exercise.id)
            .collection(INDIVIDUAL_SET_COLLECTION)
            .document(individualSet.id)
            .set(individualSet)
            .await()
    }

    override suspend fun getIndividualSetsOfExercise(exercise: Exercise): Flow<List<IndividualSet>> {
        return firestore.collection(EXERCISE_COLLECTION)
            .document(exercise.id)
            .collection(INDIVIDUAL_SET_COLLECTION)
            .orderBy(CREATED_AT_FIELD,Query.Direction.DESCENDING)
            .dataObjects()
    }

    override suspend fun getLastIndividualSetOfExercise(exercise: Exercise): IndividualSet? {
        val lst = firestore.collection(EXERCISE_COLLECTION)
            .document(exercise.id)
            .collection(INDIVIDUAL_SET_COLLECTION)
            .orderBy(CREATED_AT_FIELD,Query.Direction.DESCENDING)
            .limit(1)
            .get()
            .await()
        return if(lst.isEmpty) null
        else lst.first().toObject()
    }

    override suspend fun deleteIndividualSetOfExercise(exercise: Exercise, individualSet: IndividualSet) {
        firestore.collection(CREATED_AT_FIELD)
            .document(exercise.id)
            .collection(INDIVIDUAL_SET_COLLECTION)
            .document(individualSet.id)
            .delete()
            .await()
    }

    companion object{
        private const val EXERCISE_COLLECTION = "ExerciseCollection"
        private const val INDIVIDUAL_SET_COLLECTION = "IndividualSetCollection"
        private const val NAME_FIELD = "name"
        private const val USER_ID_FIELD = "userId"
        private const val CREATED_AT_FIELD = "createdAt"
    }
}