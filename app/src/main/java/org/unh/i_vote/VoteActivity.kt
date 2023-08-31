package org.unh.i_vote

import android.annotation.SuppressLint
import android.content.ContentValues.TAG
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Button
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Divider
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.RadioButton
import androidx.compose.material.Scaffold
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await
import org.unh.i_vote.data.database.FirebaseRef
import org.unh.i_vote.data.database.model.Choice
import org.unh.i_vote.data.database.model.User
import org.unh.i_vote.data.database.model.User.Companion.fromMap
import org.unh.i_vote.data.database.model.Vote
import org.unh.i_vote.ui.theme.IVoteTheme
import java.util.Objects


class VoteActivity : ComponentActivity() {
    //lateinit var vote: Vote;
    suspend fun getUser(email: String): User {
        val user: User
        val doc = FirebaseRef.userCollection.document(email).get().await()
        user = fromMap(
            Objects.requireNonNull<Map<String, Any>?>(doc.data)
        )
        /*col.addOnSuccessListener { doc: DocumentSnapshot ->
                if (doc.exists()) {
                    //user = doc.toObject(User.class);

                    Log.d(ContentValues.TAG, "User : "+ doc.id + " => " + doc.data)
                } else {
                    Log.w(ContentValues.TAG, "User don't exist ")
                }
            }.addOnFailureListener { e: Exception? ->
                Log.e(
                    ContentValues.TAG,
                    "Error getting document: ",
                    e
                )
            }*/

        return user;
    }
    @SuppressLint("UnrememberedMutableState")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val intent = intent
        val voteId = intent.getStringExtra("voteId")
        val orgName = intent.getStringExtra("orgName")
        val userMail = intent.getStringExtra("email")

        setContent {
            IVoteTheme {

                val coroutineScope = rememberCoroutineScope()

                val userMutableState =  remember { mutableStateOf<User>(User.empty) }
                val voteMutableState =  remember { mutableStateOf<Vote>(Vote.empty) }



                /*if(!userMail.isNullOrBlank() && !voteId.isNullOrBlank()){
                    FirebaseRef.userCollection.document(userMail).get()
                        .addOnSuccessListener { docUser ->
                            if (docUser.exists()) {
                                //user = doc.data?.let { User.fromMap(it) }!!
                                val user: User = docUser.data?.let { User.fromMap(it) }!!
                                Log.v(TAG, "User [$user] found")

                                FirebaseRef.publicVoteCollection.document(voteId).get()
                                    .addOnSuccessListener { docVote ->
                                        if (docVote.exists()) {
                                            val vote: Vote = docVote.data?.let { Vote.fromMap(it) }!!
                                            Log.v(TAG, "Vote [$vote] found")
                                            userMutableState.value = user;
                                            voteMutableState.value = vote;

                                        }

                                    }.addOnFailureListener { e ->
                                        Log.e(ContentValues.TAG, "Error on getting vote document", e)

                                    }
                            } else {
                                Log.w(TAG, "User [$userMail] does not exist")
                            }
                        }.addOnFailureListener { e ->
                            Log.e(TAG, "Error on getting user document", e)
                        }
                }*/

                //Text(result)

                Scaffold(
                    topBar = {
                        //AndroidView(factory = { toolbar })
                        TopAppBar(
                            backgroundColor = Color(0xFF6750A4),
                            contentColor = Color.White,
                            title = { androidx.compose.material.Text("Participer au vote") },
                            //windowInsets = AppBarDefaults.topAppBarWindowInsets,
                            navigationIcon = {
                                IconButton(onClick = { /* doSomething() */ }) {
                                    Icon(Icons.Filled.ArrowBack, contentDescription = null)
                                }
                            },
                            actions = {
                                IconButton(onClick = { /* doSomething() */ }) {
                                    Icon(Icons.Filled.MoreVert, contentDescription = null)
                                }
                            }
                        )
                    },
                    content = { padding ->
                        Box(modifier = Modifier.padding(padding)) {
                            LaunchedEffect(Unit) {
                                if(!userMail.isNullOrBlank()){
                                    val docUser = FirebaseRef.userCollection.document(userMail).get().await()
                                    val user: User = docUser.data?.let { User.fromMap(it) }!!
                                    userMutableState.value = user;


                                }

                            }
                            LaunchedEffect(Unit){
                                if(!voteId.isNullOrBlank()){
                                    val docVote = FirebaseRef.publicVoteCollection.document(voteId).get().await()
                                    val vote: Vote = docVote.data?.let {Vote.fromMap(it) }!!
                                    voteMutableState.value = vote;
                                    Log.w(
                                        TAG,
                                        "=== Vote found : " + voteMutableState.value
                                    )

                                }
                            }

                           if(userMutableState.value.isNotEmpty() && voteMutableState.value.isNotEmpty()){
                               Column {
                                   ChoiceListView(vote = voteMutableState.value, user = userMutableState.value)
                               }
                           }
                           else{
                               Column(
                                   modifier = Modifier.fillMaxSize(),
                                   verticalArrangement = Arrangement.Center,
                                   horizontalAlignment = Alignment.CenterHorizontally
                               ) {
                                    CircularProgressIndicator()
                                }
                            }

                        }

                    }
                )
            }
        }
    }

    //@Preview(showBackground = true)
    @Composable
    fun ChoiceListView(vote: Vote, user: User) {
        val scrollState = rememberScrollState()
        val radioOptions : List<Choice> = vote.choices
        val (selectedOption, onOptionSelected) = remember { mutableStateOf(radioOptions[0]) }


        val alreadyVote = remember{ mutableStateOf<Boolean>(false) }
        val voteState =  remember { mutableStateOf<Vote>(vote) }
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(scrollState)
                .padding(horizontal = 8.dp, vertical = 8.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            if(vote.id in user.votedList){
                alreadyVote.value = true;
                Spacer(modifier = Modifier.padding(8.dp))
                Text(
                    text = user.name + " - Vous avez déjà voté",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.Red
                )
                Spacer(modifier = Modifier.padding(8.dp))
            }

            Text(text = "${vote.title} ", fontSize = 16.sp)
            Card(
                modifier = Modifier
                    .padding(8.dp),
                border = BorderStroke(1.dp, Color.Gray),
                colors = CardDefaults.cardColors (
                    containerColor = Color.White,
                ),

            ){
                Box(
                    modifier = Modifier
                        .size(width = 300.dp, height = 10.dp),
                )
                //Text(text = "Sujet : ",fontSize = 16.sp,)
                Column(
                    modifier = Modifier.padding(8.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {

                    Text(text = "${vote.subject} ")
                    Box(
                        modifier = Modifier
                            .size(width = 300.dp, height = 10.dp),
                    )
                }

            }
            Column(
                //modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                Text(
                    text = "Les choix du vote",
                    fontSize = 22.sp,
                    fontWeight = FontWeight.Bold,
                )
                Divider(modifier = Modifier.padding(8.dp))
                Column {
                    // below line is use to set data to
                    // each radio button in columns.
                    radioOptions.forEach { choice ->
                        Card(
                            modifier = Modifier
                                .size(width = 300.dp, height = 50.dp),
                        ) {

                            Row(
                                modifier = Modifier
                                    .selectable(
                                        selected = (choice == selectedOption),
                                        onClick = { onOptionSelected(choice) }
                                    )
                                    .padding(horizontal = 16.dp),

                                ) {
                                RadioButton(
                                    // inside this method we are
                                    // adding selected with a option.
                                    selected = (choice == selectedOption),
                                    modifier = Modifier.padding(
                                        all = Dp(value = 0F),
                                    ),
                                    onClick = {
                                        // inside on click method we are setting a
                                        // selected option of our radio buttons.
                                        onOptionSelected(choice)

                                        // after clicking a radio button
                                        // we are displaying a toast message.

                                    }
                                )
                                // below line is use to add
                                // text to our radio buttons.
                                Text(
                                    text = choice.choice,
                                    fontSize = 18.sp,
                                    modifier = Modifier.padding(start = 16.dp, top = 12.dp)
                                )
                            }
                        }
                        if (alreadyVote.value){
                            Text(
                                text = "Nombre de vote : " + choice.numberOfVote,
                                color = Color.Blue,
                                fontSize = 12.sp,
                            )
                        }
                        Spacer(modifier = Modifier.padding(8.dp))
                    }
                    Spacer(modifier = Modifier.padding(8.dp))
                    if (!alreadyVote.value){
                        Button(onClick = {
                            Toast.makeText(
                                applicationContext,
                                "selectedOption :$selectedOption",
                                Toast.LENGTH_LONG,
                            ).show()

                            if(vote.id in user.votedList){
                                Toast.makeText(
                                    applicationContext,
                                    "Déjà voté",
                                    Toast.LENGTH_LONG,
                                ).show()
                            }
                            else{
                                val docVote =  FirebaseRef.publicVoteCollection.document(vote.id)
                                val docUser =  FirebaseRef.userCollection.document(user.email)

                                selectedOption.numberOfVote++;
                                val mapChoice = mutableListOf<Map<String, Any>>()

                                radioOptions.forEach{choice ->
                                    val newChoice = if(choice == selectedOption) selectedOption else choice
                                    mapChoice.add(mapOf(
                                        "choice" to newChoice.choice,
                                        "numberOfVote" to newChoice.numberOfVote
                                    ))
                                }
                                Log.w(TAG, "mapChoice => $mapChoice")

                                docVote.update(mapOf(
                                    "choices" to mapChoice
                                )).addOnSuccessListener {
                                        Log.d(TAG, "Vote choice successfully updated!")
                                        alreadyVote.value = true
                                    }
                                    .addOnFailureListener { e -> Log.w(TAG, "Error updating vote choice", e)}


                                val batchUser = FirebaseFirestore.getInstance().batch()

                                batchUser.update(docUser, "votedList",
                                    FieldValue.arrayUnion(vote.id)
                                ).commit().addOnSuccessListener {
                                    // Update successful
                                    Log.v(TAG,"User vote updated successfully [batch] \n")
                                }.addOnFailureListener {e ->
                                    Log.e(TAG,"Erreur : Update user failed [batch] \n",e)
                                }

                            }


                        }) {
                            Text(text = "Enregistrer mon vote")
                        }
                    }

                }
            }
        }

    }

}



@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}


@Composable
fun GreetingPreview() {
    IVoteTheme {
        Greeting("Android")
    }
}