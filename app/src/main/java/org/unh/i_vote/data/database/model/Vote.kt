package org.unh.i_vote.data.database.model


import java.util.Date

data class Vote(
    val id: String,
    var authorId: String,
    var authorName: String,
    val isPrivateResult: Boolean = false,
    val isUniqueChoice: Boolean = false,
    val title: String,
    val subject: String,
    val choices: List<Choice>,
    val endDate: Date,
    //val orgName: String,
    //val orgId: String,
) {

    fun isEmpty(): Boolean{
        return this == Vote.empty
    }
    fun isNotEmpty(): Boolean{
        return this != Vote.empty
    }

    fun toMap(): Map<String, Any> {
        return mapOf(
            "id" to id,
            "authorId" to authorId,
            "authorName" to authorName,
            "isPrivateResult" to isPrivateResult,
            "subject" to subject,
            "choices" to choices,
            "title" to title,
            //"orgName" to orgName,
            //"orgId" to orgId,
            "endDate" to endDate.time,
            "isUniqueChoice" to isUniqueChoice,
        );
    }

    companion object {
        val empty = Vote(
            id = "",
            authorId= "",
            authorName ="",
            isPrivateResult = false,
            isUniqueChoice = false,
            title = "",
            subject = "",
            choices = emptyList(),
            endDate = Date()
        )
        fun fromMap(data: Map<String, Any>): Vote = Vote(
            id = data["id"] as String,
            authorId = data["authorId"] as String,
            authorName = data["authorName"] as String,
            isPrivateResult = data["isPrivateResult"] as Boolean,
            title = data["title"] as String,
            subject = data["subject"] as String,
            choices = Choice.getListFromMap( data["choices"] as List<Map<String, Long>>),
            endDate = Date(data["endDate"] as Long),
            isUniqueChoice = data["isUniqueChoice"] as Boolean,
            //orgId = data["orgId"] as String,
            //orgName = data["orgName"] as String,
        );
    }
}

