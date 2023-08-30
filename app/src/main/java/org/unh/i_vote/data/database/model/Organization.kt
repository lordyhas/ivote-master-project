package org.unh.i_vote.data.database.model

data class Organization(
    val id: String,
    val creatorId: String,
    val creatorName: String,
    val name: String,
    val about: String,
    val userIdList: List<String>,
    val adminIdList : List<String>,
){
    fun toMap() : Map<String,Any>{
        return  mapOf(
            "id" to id,
            "creatorId" to creatorId,
            "creatorName" to creatorName,
            "name" to name,
            "about" to about,
            "userIdList" to userIdList,
            "adminList" to adminIdList,
        )
    }
    companion object{
        fun fromMap(map: Map<String,Any>): Organization{
            return Organization(
                id = map["id"] as String,
                creatorId = map["creatorId"] as String,
                creatorName = map["creatorName"] as String,
                name = map["name"] as String,
                about = map["about"] as String,
                userIdList = map.getOrDefault("userIdList", emptyList<String>()) as List<String>,
                adminIdList = map.getOrDefault("adminList", emptyList<String>()) as List<String>,
            );
        }
    }
}
