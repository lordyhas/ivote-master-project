package org.unh.i_vote.data.database.model


data class Choice(val choice: String, var numberOfVote: Long=0){
    companion object{
        fun fromMap(item: Map<String, Long>): Choice {
            return Choice(item["choice"].toString(), item["numberOfVote"] as Long)
        }

        fun getListFromMap(choiceList: List<Map<String, Long>>): List<Choice> {
            val choices = mutableListOf<Choice>()
            choiceList.forEach{ map ->
                choices.add(Choice.fromMap(map))
            }
            return choices;
        }
    }
}