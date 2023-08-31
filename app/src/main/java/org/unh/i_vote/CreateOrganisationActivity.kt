package org.unh.i_vote

import android.content.ContentValues.TAG
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Button
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Divider
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedButton
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.ui.AppBarConfiguration
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import org.unh.i_vote.data.controller.VoteItemAdapter
import org.unh.i_vote.data.database.FirebaseRef
import org.unh.i_vote.data.database.model.Organization
import org.unh.i_vote.data.database.model.User
import org.unh.i_vote.databinding.ActivityVoteListBinding
import java.util.Date

class CreateOrganisationActivity : AppCompatActivity() {
    private val appBarConfiguration: AppBarConfiguration? = null
    private var binding: ActivityVoteListBinding? = null
    private val recyclerView: RecyclerView? = null
    private val layoutManager: RecyclerView.LayoutManager? = null
    private val voteItemAdapter: VoteItemAdapter? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityVoteListBinding.inflate(layoutInflater)
        setContentView(binding!!.root)
        setSupportActionBar(binding!!.toolbar)

        //NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_vote_list);
        //appBarConfiguration = new AppBarConfiguration.Builder(navController.getGraph()).build();
        //NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        binding!!.fab.setOnClickListener { view ->
            Snackbar.make(view, "Clicked", Snackbar.LENGTH_LONG)
                .setAnchorView(R.id.fab)
                .setAction("Action", null).show()
        }

        val userEmail = intent.getStringExtra("email")
        val userName = intent.getStringExtra("name")

        Log.d(TAG, "=== userName : $userName")
        Log.d(TAG, "=== userEmail : $userEmail")

        setContent {
            MaterialTheme {

                var title by  remember { mutableStateOf("") }
                var about by remember { mutableStateOf("") }

                val savingMutableState =  remember { mutableStateOf<Boolean>(false) }


                val scrollState = rememberScrollState()

                Scaffold(
                    topBar = {
                        //AndroidView(factory = { toolbar })
                        TopAppBar(
                            backgroundColor = Color(0xFF6750A4),
                            contentColor = Color.White,
                            title = { Text("Créer une organisation") },
                            //windowInsets = AppBarDefaults.topAppBarWindowInsets,
                            navigationIcon = {
                                IconButton(onClick = {}) {
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
                ) { padding ->
                        Box(modifier = Modifier.padding(padding)){
                            Column(
                                horizontalAlignment = Alignment.CenterHorizontally,
                                modifier = Modifier
                                    .fillMaxSize()
                                    .verticalScroll(scrollState)
                                    .padding(horizontal = 8.dp, vertical = 8.dp),
                            ) {

                                Spacer(modifier = Modifier.padding(8.dp))
                                Column(
                                    modifier = Modifier.padding(16.dp),
                                    horizontalAlignment = Alignment.CenterHorizontally
                                ){
                                    Box(
                                        modifier = Modifier
                                            .size(width = 280.dp, height = 10.dp),
                                    )
                                    Text(
                                        text = "L'organisation est un moyen de créer des votes restreints, seulement les membres de l'organisation peuvent paticipant aux vote",
                                        fontSize = 14.sp,
                                        fontWeight = FontWeight.Bold,
                                    )
                                }

                                Divider()
                                Spacer(modifier = Modifier.padding(16.dp))

                                Column(
                                    horizontalAlignment = Alignment.CenterHorizontally,
                                    modifier = Modifier.fillMaxSize(),
                                ) {

                                    TextField(
                                        value = title,
                                        onValueChange = { title = it },
                                        label = { Text("Titre") },
                                        placeholder = { Text("Entrer le titre de l'organisation") },
                                        modifier = Modifier.size(height = 50.dp, width = 320.dp),
                                    )
                                    Spacer(modifier = Modifier.padding(8.dp))
                                    OutlinedTextField(
                                        value = about,
                                        onValueChange = { about = it },
                                        label = { Text("Description") },
                                        placeholder = { Text("Entrer la description de l'organisation") },
                                        maxLines = 5,
                                        modifier= Modifier.size(height = 100.dp, width = 320.dp),
                                    )
                                }

                                Spacer(modifier = Modifier.padding(8.dp))



                                Spacer(modifier = Modifier.fillMaxSize())

                                ///----------------------------------------------------



                                ///----------------------------------------------------

                                Spacer(modifier = Modifier.padding(16.dp))

                                Row{
                                    OutlinedButton(onClick = { finish() }) {
                                        Text(text = if(savingMutableState.value) "Quitter" else "Annuler")
                                    }
                                    Spacer(modifier = Modifier.padding(16.dp))
                                    Button(onClick = {
                                        savingMutableState.value = true
                                        if(title.isNotBlank() && about.isNotBlank()){
                                            if(!userEmail.isNullOrBlank() && !userName.isNullOrBlank()){
                                                val org = Organization(
                                                    id ="OG" + Date().time,
                                                    creatorId = userEmail,
                                                    creatorName = userName,
                                                    name =  title,
                                                    about =  about,
                                                    userIdList = listOf(userEmail),
                                                    adminIdList = listOf(userEmail)
                                                )
                                                FirebaseRef.orgCollection.document(org.id).set(org.toMap())
                                                    .addOnSuccessListener {
                                                        Log.d(TAG, "Organisation successfully written!")

                                                        val docUser =  FirebaseRef.userCollection.document(userEmail)

                                                        val batchUser = FirebaseFirestore.getInstance().batch()

                                                        batchUser.update(docUser, "orgList",
                                                            FieldValue.arrayUnion(org.id)
                                                        ).commit().addOnSuccessListener {
                                                            // Update successful
                                                            Log.v(TAG,"User org-list updated successfully [batch] \n")
                                                            Toast.makeText(
                                                                applicationContext,
                                                                "Organisation : ${org.name} a été créée",
                                                                Toast.LENGTH_LONG,
                                                            ).show()
                                                            finish()
                                                            savingMutableState.value = false
                                                        }.addOnFailureListener {e ->
                                                            Log.e(TAG,"Erreur : Update user org-list failed [batch] \n",e)
                                                        }
                                                    }
                                                    .addOnFailureListener { e ->
                                                        Log.e(TAG, "Error writing [Organization]", e)
                                                    }
                                            }else{
                                                Log.w(TAG, "Problem on getting user.id => to create [Organization]"+
                                                        "\nuserEmail: "+userEmail+" | userName: "+userName)
                                            }
                                        }else{
                                            Toast.makeText(
                                                applicationContext,
                                                "Veuillez completer le formulaire, avant de créer",
                                                Toast.LENGTH_LONG,
                                            ).show()
                                        }
                                    }) {
                                        Text(text = "Créer l'organisation")
                                    }
                                }
                                Spacer(modifier = Modifier.padding(32.dp))
                                if(savingMutableState.value){
                                    CircularProgressIndicator()
                                }
                            }
                    }
                }
            }
        }
    }
}