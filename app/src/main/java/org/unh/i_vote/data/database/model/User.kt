package org.unh.i_vote.data.database.model

data class User(
    val name: String,
    val email: String,
    val password: String,
    val orgList: List<String>,
    val votedList: List<String>
){
    fun isEmpty(): Boolean{
        return this == User.empty
    }
    fun isNotEmpty(): Boolean{
        return this != User.empty
    }
    fun toMap() : Map<String, Any> {
        return mapOf(
            "name" to name,
            "email" to email,
            "password" to password,
            "orgList" to orgList,
            "votedList" to votedList,
        )
    }
    //fun toJson() = toMap();
    companion object {
        val empty = User("","","", emptyList(), emptyList())

        fun fromMap(data: Map<String, Any>) : User = User(
            data["name"] as String,
            data["email"] as String,
            data["password"] as String,
            data["orgList"] as List<String>,
            data["votedList"] as List<String>,
        )
    }
}

