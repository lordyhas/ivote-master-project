package org.unh.i_vote.data.model

import android.util.Log

import androidx.lifecycle.ViewModel

import org.unh.i_vote.data.database.FirebaseRef
import org.unh.i_vote.data.database.model.User


import androidx.lifecycle.liveData

import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

/*
class UserViewModel : ViewModel() {
    // A LiveData that holds the current value of the counter
    val user = MutableStateFlow<User>(User.empty)



    fun setUser(user: User) {

    }
    suspend fun loadUser(email: String) {
        val doc = FirebaseRef.userCollection.document(email).get().await()
        user.value = User.fromMap(
            Objects.requireNonNull<Map<String, Any>?>(doc.data)
        )
    }
}*/

// A ViewModel class that holds and manages the data from Firestore
class UserViewModel : ViewModel() {

    // A LiveData that holds a list of users from Firestore
    /*val users = liveData {
        // Get a reference to the Firestore collection
        val collectionRef = FirebaseRef.userCollection

        // Observe the changes in the collection and emit the list of users
        collectionRef.addSnapshotListener { snapshot, error ->
            if (error != null) {
                Log.e("UserViewModel", "Error getting users", error)
                return@addSnapshotListener
            }
            if (snapshot != null) {
                val userList = snapshot.documents.map { doc ->
                    // Convert each document snapshot into a User object
                   doc.data?.let { User.fromMap(it) } ?: User.empty
                }
                emit(userList)
            }
        }
    }.asLiveData()*/

    /*val user = liveData {
        // Get a reference to the Firestore collection
        val docRef = FirebaseRef.userCollection.document()

        // Observe the changes in the collection and emit the list of users
        docRef.addSnapshotListener { snapshot, error ->
            if (error != null) {
                Log.e("UserViewModel", "Error getting users", error)
                return@addSnapshotListener
            }
            if (snapshot != null) {

                val user : User = snapshot.data?.let { User.fromMap(it) } ?: User.empty
                viewModelScope.launch{
                    emit(user)
                }
            }
        }


    }.asLiveData()*/
}
