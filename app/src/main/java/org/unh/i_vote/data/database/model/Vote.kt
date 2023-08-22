package org.unh.i_vote.data.database.model


data class Vote(
    val id: Int,
    val authorId: Int,
    val isPrivateResult: Boolean = false,
    val subject: String,
    val choices: List<String>,
) {
    fun toMap(): Map<String, Any> {
        return mapOf(
            "id" to id,
            "authorId" to authorId,
            "isPrivateResult" to isPrivateResult,
            "subject" to subject,
            "choices" to choices,
        );
    }

    companion object {
        fun fromMap(data: Map<String, Any>): Vote = Vote(
            id = data["id"] as Int,
            authorId = data["authorId"] as Int,
            isPrivateResult = data["isPrivateResult"] as Boolean,
            subject = data["subject"] as String,
            choices = data["choices"] as List<String>,
        );
    }
}
