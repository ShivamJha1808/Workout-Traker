package com.shivamjsr18.workouttraker.model

import com.google.firebase.firestore.DocumentId
import com.google.firebase.firestore.ServerTimestamp
import java.util.Date

data class IndividualSet(
    @DocumentId val id: String="",
    val exId: String="",
    @ServerTimestamp val createdAt:Date=Date(),
    val set1: Double=0.0,
    val set2: Double=0.0,
    val set3: Double=0.0,
    val set4: Double=0.0,
    val set5: Double=0.0,
    val remarks: String="",
)
