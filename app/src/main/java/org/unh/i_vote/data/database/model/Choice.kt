package org.unh.i_vote.data.database.model


data class Choice(val choice: String, val numberOfVote:Int=0){
    companion object{
        fun fromMap(item: Map<String, Int>): Choice {
            return Choice(item["choice"] as String, item["numberOfVote"] as Int)
        }

        fun getListFromMap(choiceList: List<Map<String, Int>>): List<Choice> {
            var choices = mutableListOf<Choice>()
            choiceList.forEach{ map ->
                choices.add(Choice.fromMap(map))
            }
            return choices;
        }
    }
}