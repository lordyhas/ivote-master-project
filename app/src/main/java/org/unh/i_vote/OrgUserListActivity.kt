package org.unh.i_vote

import android.os.Bundle
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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.ListItem
import androidx.compose.material.Scaffold
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Card
import androidx.compose.material.Divider
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material3.CardDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.tasks.await
import org.unh.i_vote.data.database.FirebaseRef
import org.unh.i_vote.data.database.model.Organization
import org.unh.i_vote.data.database.model.User
import org.unh.i_vote.ui.theme.IVoteTheme

class OrgUserListActivity : ComponentActivity() {

    fun loadOrganisation(orgId : String){
        FirebaseRef.orgCollection.document(orgId).get().addOnSuccessListener { doc ->
            if (doc.exists()){
                doc.data;

            }

        }
    }

    @OptIn(ExperimentalMaterialApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MaterialTheme {
                // A surface container using the 'background' color from the theme

                val scrollState = rememberScrollState()

                val orgId : String? = intent.getStringExtra("orgId");
                val userMail : String? = intent.getStringExtra("email");

                val dataLoadedState =  remember { mutableStateOf<Boolean>(false) }
                val orgMutableState =  remember { mutableStateOf<Organization?>(null) }
                val userMutableState =  remember { mutableStateOf<User?>(null) }


                Surface(
                    modifier = Modifier.fillMaxSize(),
                    //color = MaterialTheme.colorScheme.background
                ) {
                    Scaffold(
                        topBar = {
                            TopAppBar(
                                backgroundColor = Color(0xFF6750A4),
                                contentColor = Color.White,
                                title = { Text("Organisation") },
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
                    ){ padding ->
                        Box(modifier = Modifier.padding(padding)){
                            Column(
                            horizontalAlignment = Alignment.CenterHorizontally,
                            modifier = Modifier
                                .fillMaxSize()
                                //.verticalScroll(scrollState)
                                .padding(horizontal = 8.dp, vertical = 8.dp),
                            ){
                                if (!orgId.isNullOrBlank() && !userMail.isNullOrBlank()){
                                    LaunchedEffect(Unit){
                                        val doc  =  FirebaseRef.orgCollection.document(orgId)
                                            .get().await()
                                        val org : Organization = doc.data?.let {
                                            Organization.fromMap(it)
                                        }!!
                                        orgMutableState.value = org;

                                        val docUser = FirebaseRef.userCollection.document(userMail)
                                            .get().await()
                                        val user: User = docUser.data?.let { User.fromMap(it) }!!
                                        userMutableState.value = user;
                                    }

                                    if(
                                        userMutableState.value != null &&
                                        orgMutableState.value != null
                                    ){
                                        val thisUser: User = userMutableState.value!!;
                                        val org : Organization = orgMutableState.value!!;

                                        Spacer(modifier = Modifier.padding(8.dp))
                                        Card(
                                            modifier = Modifier.padding(
                                                horizontal = 8.dp,
                                                vertical = 8.dp,
                                            ),
                                            colors = CardDefaults.cardColors(
                                                containerColor = Color.White,
                                            ),
                                            border = BorderStroke(1.dp, Color.Gray),
                                        ) {
                                            Spacer(modifier = Modifier.padding(4.dp))
                                            Column(
                                                modifier = Modifier.padding(
                                                    horizontal = 8.dp,
                                                    vertical = 8.dp,
                                                ),
                                            ) {
                                                Text(text = "Nom : ${org.name}")
                                                Text(text = "Create by : ${org.creatorName}")
                                                Text(text = "Description : ${org.about}")
                                            }
                                            Spacer(modifier = Modifier.padding(4.dp))

                                        }
                                        Spacer(modifier = Modifier.padding(8.dp))
                                        Divider()
                                        LazyColumn{
                                            itemsIndexed(org.userIdList){ index, user ->
                                                Card(
                                                    modifier = Modifier.padding(
                                                        horizontal = 8.dp,
                                                        vertical = 8.dp
                                                    ),
                                                    colors = CardDefaults.cardColors(
                                                        containerColor = Color.LightGray,
                                                    ),
                                                    border = BorderStroke(1.dp, Color.Black),
                                                ){
                                                    ListItem(
                                                        icon = {
                                                            Icon(
                                                                Icons.Filled.Person,
                                                                contentDescription = null,
                                                                modifier = Modifier.size(42.dp)
                                                            ) },
                                                        text = { Text(
                                                            text = "$index : $user",
                                                            fontSize = 18.sp,
                                                        )},
                                                        trailing = {IconButton(onClick = {

                                                        }) {
                                                            Icon(Icons.Filled.MoreVert, contentDescription = null)
                                                        }},
                                                    )
                                                }
                                            }
                                        }

                                    }else{
                                        Column(
                                            modifier = Modifier.fillMaxSize(),
                                            verticalArrangement = Arrangement.Center,
                                            horizontalAlignment = Alignment.CenterHorizontally
                                        ) {
                                            CircularProgressIndicator()
                                        }
                                    }
                                }
                                else {
                                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                        Text(text = "Error #2 - No user id found")
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun Greeting2(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview2() {
    IVoteTheme {
        Greeting2("Android")
    }
}