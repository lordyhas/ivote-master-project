package org.unh.i_vote.data.database

import android.content.ContentValues.TAG
import android.util.Log
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import org.unh.i_vote.data.database.model.Organization
import org.unh.i_vote.data.database.model.User


private val store = Firebase.firestore.collection("iVoteApp").document("general_data")
private val userRef =  store.collection("USERS");
private val orgRef = store.collection("ORGS");
private val orgVoteRef = store.collection("ORGS/private/votes");
private val publicVoteRef = store.collection("PUBLIC_VOTES");

class FirebaseManager(user: User) {
    fun getOrganizations() : List<Organization> {

        return emptyList()
    }
    companion object Users{

        fun createUser(user: User){
            userRef.document(user.email).get().addOnSuccessListener { doc ->
                if (doc.exists()) {
                    userRef.document(user.email)
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