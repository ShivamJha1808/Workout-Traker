package com.shivamjsr18.workouttraker.model

import com.google.firebase.firestore.DocumentId
import com.google.firebase.firestore.ServerTimestamp
import java.util.Date

data class Exercise(
    @DocumentId val id: String ="",
    @ServerTimestamp val createdAt: Date = Date(),
    val name: String="",
    val muscleGroup: MuscleGroup = MuscleGroup.Biceps,
    val userId: String=""
)
