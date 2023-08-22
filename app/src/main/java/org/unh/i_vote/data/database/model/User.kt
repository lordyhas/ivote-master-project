package org.unh.i_vote.data.database.model

data class User(
    val id : Int,
    val name: String,
    val email: String,
){
    fun toMap() : Map<String, Any> {
        return mapOf(
            "id" to  id,
            "name" to name,
            "email" to email,
        )
    }
    //fun toJson() = toMap();
    companion object {
        fun fromMap(data: Map<String, Any>) : User = User(
            data["id"] as Int,
            data["name"] as String,
            data["email"] as String,
        )
    }
}

