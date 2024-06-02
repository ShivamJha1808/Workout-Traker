package com.shivamjsr18.workouttraker.services.implimentation

import com.google.firebase.auth.EmailAuthProvider
import com.google.firebase.auth.FirebaseAuth
import com.shivamjsr18.workouttraker.model.User
import com.shivamjsr18.workouttraker.services.AccountService
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class AccountServiceImpl @Inject constructor(private val firebaseAuth: FirebaseAuth) : AccountService {
    override val currentUserId: String
        get() = firebaseAuth.currentUser?.uid.orEmpty()

    override val hasUser: Boolean
        get() = firebaseAuth.currentUser!=null

    override val currentUser: Flow<User>
        get() = callbackFlow {
            val listener = FirebaseAuth.AuthStateListener {auth->
                this.trySend(auth.currentUser?.let{currentUser->
                    User(currentUser.uid,currentUser.isAnonymous)
                }?:User())
            }
            firebaseAuth.addAuthStateListener(listener)
            awaitClose{ firebaseAuth.removeAuthStateListener(listener) }
        }

    override suspend fun createAnonymousAccount() {
        firebaseAuth.signInAnonymously().await()
    }

    override suspend fun authenticate(email: String, password: String) {
        firebaseAuth.signInWithEmailAndPassword(email,password).await()
    }

    override suspend fun sendRecoveryEmail(email: String) {
        firebaseAuth.sendPasswordResetEmail(email)
    }

    override suspend fun linkAccount(email: String, password: String) {
        val credentials = EmailAuthProvider.getCredential(email,password)
        firebaseAuth.currentUser!!.linkWithCredential(credentials).await()
    }

    override suspend fun signOut() {
        if(firebaseAuth.currentUser!!.isAnonymous){
            firebaseAuth.currentUser!!.delete()
        }
        firebaseAuth.signOut()
        firebaseAuth.signInAnonymously().await()
    }

    override suspend fun deleteAccount() {
        firebaseAuth.currentUser!!.delete().await()
    }
}