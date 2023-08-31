package org.unh.i_vote.data.database

import android.content.ContentValues.TAG
import android.util.Log
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.FirebaseFirestoreSettings
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import org.unh.i_vote.data.database.model.Organization
import org.unh.i_vote.data.database.model.User

private fun getFireStore(): FirebaseFirestore {
    // Using a builder pattern
    val settings = FirebaseFirestoreSettings.Builder()
        .setPersistenceEnabled(true)
        .build()

    val firestore = Firebase.firestore

    firestore.firestoreSettings = settings

    return firestore;
}

class FirebaseRef {
    companion object{
        private val store = getFireStore()
            .collection("iVoteApp")
            .document("general_data")

        @JvmField
        val userCollection =  store.collection("USERS");

        @JvmField
        val orgCollection = store.collection("ORGS");

        @JvmField
        val publicVoteCollection = store.collection("PUBLIC_VOTES");
    }
}

class FirebaseManager(user: User) {
    fun getOrganizations() : List<Organization> {

        return emptyList()
    }
    companion object Users{

        fun createUser(user: User){
            FirebaseRef.userCollection.document(user.email).get().addOnSuccessListener { doc ->
                if (doc.exists()) {
                    FirebaseRef.userCollection.document(user.email)
                        .set(user)
                        .addOnSuccessListener {
                            Log.d(TAG, "User create [$user]")
                        }
                        .addOnFailureListener { e ->
                            Log.w(TAG, "Error adding document", e)
                        }
                } else {
                    Log.d(TAG, "User [$user] exist")
                }
            }.addOnFailureListener { e ->
                // Une erreur s'est produite
                Log.w(TAG, "Error on getting document", e)
            }
        }

        fun updateUser(email : String){

        }
        fun deleteUser(email : String){}
    }

    class Org{
        fun addUser(orgId: String, email : String){

        }
        fun removeUser(email : String){}
        fun makeAdmin(email : String){}
    }

}