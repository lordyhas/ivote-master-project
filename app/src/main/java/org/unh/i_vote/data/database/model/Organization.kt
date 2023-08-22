package org.unh.i_vote.data.database.model

data class Organization(
    val id: Int,
    val creatorId: String,
    val name: String,
    val about: String,
    val userIdList: List<String>,
    val adminList : List<String>,
)
