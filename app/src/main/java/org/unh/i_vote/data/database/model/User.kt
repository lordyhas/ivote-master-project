package org.unh.i_vote.data.database.model

data class User(
    val name: String,
    val email: String,
    val orgList: List<String>
){
    fun toMap() : Map<String, Any> {
        return mapOf(
            "name" to name,
            "email" to email,
            "orgList" to orgList,
        )
    }
    //fun toJson() = toMap();
    companion object {
        fun fromMap(data: Map<String, Any>) : User = User(
            data["name"] as String,
            data["email"] as String,
            data["orgList"] as List<String>,
        )
    }
}

