package org.unh.i_vote.data.model

import android.util.Log

import androidx.lifecycle.ViewModel

import org.unh.i_vote.data.database.FirebaseRef
import org.unh.i_vote.data.database.model.User


import androidx.lifecycle.liveData

import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import java.util.Objects

class UserViewModel : ViewModel() {
    // A LiveData that holds the current value of the counter
    val user = MutableStateFlow<User>(User.empty)

    fun setUser(user: User) {
        this.user.value = user
    }
    suspend fun loadUser(email: String) {
        val doc = FirebaseRef.userCollection.document(email).get().await()
        user.value = User.fromMap(
            Objects.requireNonNull<Map<String, Any>?>(doc.data)
        )
    }
}
