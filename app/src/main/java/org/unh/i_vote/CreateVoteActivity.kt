package org.unh.i_vote

import android.content.ContentValues
import android.content.ContentValues.TAG
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Button
import androidx.compose.material.Card
import androidx.compose.material.Divider
import androidx.compose.material.DropdownMenu
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedButton
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Scaffold
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.Done
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.primarySurface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.compose.rememberNavController
import org.unh.i_vote.data.database.FirebaseManager
import org.unh.i_vote.data.database.FirebaseRef
import org.unh.i_vote.data.database.model.Choice
import org.unh.i_vote.data.database.model.User
import org.unh.i_vote.data.database.model.Vote
import org.unh.i_vote.ui.theme.IVoteTheme
import java.io.Serializable
import java.util.Date

/*
 *
 * @author lordyhas (Hassan K.)
 *
 */

class CreateVoteActivity : ComponentActivity() {

    private val typeOfVote = listOf("Choix binaire", "Choix multiple")


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            MaterialTheme {
                val textList = remember{
                    mutableStateListOf<TextFieldValue>()
                }

                val choicesStateList = remember { mutableStateListOf<String>() }
                val voteMutableState =  remember { mutableStateOf<Vote>(Vote.empty) }
                //val count = remember { mutableStateOf(1) }

                val selectedTypeOfVote = remember { mutableStateOf<String>(typeOfVote[0]) }

                val textValues = remember { mutableStateListOf(*List(1) {""}.toTypedArray()) }

                var title by  remember { mutableStateOf("") }
                var subject by  remember { mutableStateOf("") }

                val navController = rememberNavController()

                val scrollState = rememberScrollState()
                // A surface container using the 'background' color from the theme
                Scaffold(
                    topBar = {
                        //AndroidView(factory = { toolbar })
                        TopAppBar(
                            backgroundColor = Color(0xFF6750A4),
                            contentColor = Color.White,
                            title = { Text("Créer un vote") },
                            //windowInsets = AppBarDefaults.topAppBarWindowInsets,
                            navigationIcon = {
                                IconButton(onClick = { navController.popBackStack()}) {
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
                    /*floatingActionButtonPosition = FabPosition.End,
                    floatingActionButton = {
                        FloatingActionButton(
                            onClick = {}
                        ) {
                            Icon(imageVector = Icons.Default.Add, contentDescription = "fab icon")
                        }
                    },*/

                    content = { padding ->
                        Box(modifier = Modifier.padding(padding)){
                            Column(
                                horizontalAlignment = Alignment.CenterHorizontally,
                                modifier = Modifier
                                    .fillMaxSize()
                                    .verticalScroll(scrollState)
                                    .padding(horizontal = 8.dp, vertical = 8.dp),
                            ) {
                                //InputEmail()
                                //Spacer(modifier = Modifier.padding(8.dp))

                                /*rememberSaveable(stateSaver = TextFieldValue.Saver) {
                                    mutableStateOf(TextFieldValue("", TextRange(0, 256)))
                                }*/

                                Column(
                                    horizontalAlignment = Alignment.CenterHorizontally,
                                    modifier = Modifier.fillMaxSize(),
                                ) {

                                    TextField(
                                        value = title,
                                        onValueChange = { title = it },
                                        label = { Text("Titre") },
                                        placeholder = { Text("Entrer le titre du vote") },
                                        modifier = Modifier.size(height = 50.dp, width = 320.dp),
                                    )
                                    Spacer(modifier = Modifier.padding(8.dp))
                                    OutlinedTextField(
                                        value = subject,
                                        onValueChange = { subject = it },
                                        label = { Text("Entrer le sujet du vote") },
                                        placeholder = { Text("Sujet") },
                                        maxLines = 5,
                                        modifier= Modifier.size(height = 100.dp, width = 320.dp),
                                        //singleLine = false,
                                    )
                                }


                                Spacer(modifier = Modifier.padding(8.dp))

                                /*var number by rememberSaveable(stateSaver = TextFieldValue.Saver) {
                                    mutableStateOf(TextFieldValue("", TextRange(0, 7)))
                                }*/



                                // A dropdown textfield with a label and options
                                DropdownTextField(
                                    modifier = Modifier.size(height = 50.dp, width = 320.dp),
                                    label = "Type de vote",
                                    options = typeOfVote,
                                    selectedOption = selectedTypeOfVote.value,
                                    onOptionSelected = { selectedTypeOfVote.value = it }
                                )

                                Spacer(modifier = Modifier.padding(8.dp))

                                ///----------------------------------------------------
                                if(selectedTypeOfVote.value == typeOfVote[1]){
                                    Column (horizontalAlignment = Alignment.CenterHorizontally) {
                                        Row{
                                            Spacer(modifier = Modifier.padding(horizontal = 4.dp))
                                            OutlinedButton(
                                                onClick = { if(textValues.size >= 1){
                                                    //count.value--;
                                                    textValues.removeLast()

                                                }
                                                    //choicesStateList.removeLast()
                                                          }, // Increment the count on click
                                                border = BorderStroke(
                                                    1.dp, MaterialTheme.colors.primarySurface), // Set the border width and color
                                                shape = RoundedCornerShape(8.dp) // Set the corner radius
                                            ) {
                                                Icon(
                                                    Icons.Filled.KeyboardArrowDown,
                                                    contentDescription = null,
                                                )
                                                Text(text = "Supprimer choix") // Display the text and count
                                            }
                                            Spacer(modifier = Modifier.padding(horizontal = 8.dp))
                                            OutlinedButton(
                                                onClick = {
                                                    if(textValues.size <= 5){
                                                        //count.value++;
                                                        textValues.add("")
                                                    } //choicesStateList.add("__")
                                                          }, // Increment the count on click
                                                border = BorderStroke(
                                                    1.dp, MaterialTheme.colors.primarySurface), // Set the border width and color
                                                shape = RoundedCornerShape(8.dp) // Set the corner radius
                                            ) {
                                                Text(text = "Ajouter un choix")
                                                Icon(
                                                    Icons.Filled.KeyboardArrowUp,
                                                    contentDescription = null,
                                                )
                                            }
                                            Spacer(modifier = Modifier.padding(horizontal = 4.dp))
                                        }
                                        Text(text = "Nombre de choix ${choicesStateList.size}/5")
                                    }
                                }
                                ///----------------------------------------------------

                                Spacer(modifier = Modifier.padding(8.dp))
                                Divider()
                                Spacer(modifier = Modifier.padding(8.dp))
                                Column(horizontalAlignment = Alignment.CenterHorizontally){
                                    Card(
                                        modifier = Modifier
                                            .padding(horizontal = 8.dp, vertical = 8.dp),
                                        border = BorderStroke(1.dp, Color.Gray),
                                    ){
                                        Column(
                                            modifier = Modifier.padding(8.dp),
                                            horizontalAlignment = Alignment.CenterHorizontally
                                        ){
                                            //if (number.isNotEmpty())
                                            //count.value = number.toInt();
                                            Box(
                                                modifier = Modifier
                                                    .size(width = 300.dp, height = 10.dp),
                                            )
                                            Text(
                                                text = "Les choix du vote",
                                                fontSize = 22.sp,
                                                fontWeight = FontWeight.Bold,
                                            )
                                            Divider(modifier = Modifier.padding(8.dp))
                                            if(isUniqueChoice(selectedTypeOfVote.value)){
                                                Text("Oui   |    Non", fontSize = 18.sp)
                                            }else{
                                                textValues.forEachIndexed { index, value ->
                                                    TextField(
                                                        value = value,
                                                        onValueChange = { textValues[index] = it },
                                                        label = { Text("Entrer un choix du vote") },
                                                        placeholder = { Text("Choix") },
                                                        leadingIcon = { Icon(Icons.Filled.Done, contentDescription = null) }
                                                    )
                                                    Spacer(modifier = Modifier.padding(8.dp))
                                                }
                                            }
                                        }
                                    }
                                }

                                Spacer(modifier = Modifier.padding(16.dp))

                                Row{
                                    OutlinedButton(onClick = { finish()}) {
                                        Text(text = "Quitter")
                                    }
                                    Spacer(modifier = Modifier.padding(16.dp))
                                    Button(onClick = {
                                        val intent = Intent(
                                            this@CreateVoteActivity,
                                            MainActivity::class.java
                                        )
                                        //intent.putExtra("vote", voteMutableState.value.toMap() as Serializable);
                                        //intent.put

                                        val ans = mutableSetOf<Choice>()
                                        if(isUniqueChoice(selectedTypeOfVote.value)){
                                            ans.add(Choice("OUI",0))
                                            ans.add(Choice("NON",0))
                                        }
                                        else{
                                            textValues.forEach { value -> ans.add(Choice(value,0)) }
                                        }

                                        val vote = Vote(
                                            id = System.currentTimeMillis().toString(),
                                            authorId= "",
                                            authorName = "",
                                            isPrivateResult = false,
                                            isUniqueChoice = isUniqueChoice(selectedTypeOfVote.value),
                                            title = title,
                                            subject = subject,
                                            choices = ans.toList(),
                                            endDate = Date()
                                        )


                                        voteMutableState.value = vote

                                        if(voteMutableState.value.id.isNotBlank() &&
                                            voteMutableState.value.title.isNotBlank() &&
                                            voteMutableState.value.subject.isNotBlank()
                                        ){
                                            createVote(voteMutableState.value);
                                            Toast.makeText(
                                                applicationContext,
                                                "vote : ${voteMutableState.value.title} a été créé",
                                                Toast.LENGTH_LONG,
                                            ).show()
                                            //navController.popBackStack()
                                        }else{
                                            Toast.makeText(
                                                applicationContext,
                                                "Veuillez completer le vote, avant de créer",
                                                Toast.LENGTH_LONG,
                                            ).show() // in Activity
                                        }
                                        //startActivity(intent)
                                        //finish()
                                    }) {
                                        Text(text = "Créer le vote")
                                    }
                                }


                            }
                        }
                    },
                )
            }
        }
    }

    private fun isUniqueChoice(value : String) : Boolean{
        return value == typeOfVote.first()
    }

    private fun createVote(vote : Vote){
        lateinit var user: User
        val intent = intent // Get the intent that started this activity

        val email = intent.getStringExtra("email") // Get the string with the key "message" from the intent
        if(email.isNullOrBlank()) return;
        //FirebaseManager.Users


        FirebaseRef.userCollection.document(email).get().addOnSuccessListener{ doc ->
            if (doc.exists()) {
                //user = doc.data?.let { User.fromMap(it) }!!

                user = doc.data?.let { User.fromMap(it) }!!
                Log.d(ContentValues.TAG, "User [$user] found")

                vote.authorId = user.email
                vote.authorName = user.name

                FirebaseRef.publicVoteCollection.document(vote.id).set(vote.toMap())
                    .addOnSuccessListener {
                        Log.d(TAG, "vote successfully written!")
                        //finish();
                    }
                    .addOnFailureListener { e -> Log.w(TAG, "Error writing [vote]", e) }
            } else {
                Log.d(ContentValues.TAG, "User [$email] does not exist")
            }
        }.addOnFailureListener { e ->
            // Une erreur s'est produite
            Log.w(ContentValues.TAG, "Error on getting document", e)
        }


        //Toast.makeText(this, email, Toast.LENGTH_SHORT).show() // Display a toast with the
        //val fbm = FirebaseManager(user)
    }

    @Preview(showBackground = true)
    @Composable
    fun InputEmail(){
        var text by rememberSaveable(stateSaver = TextFieldValue.Saver) {
            mutableStateOf(TextFieldValue("", TextRange(0, 32)))
        }
        TextField(
            value = text,
            onValueChange = { text = it },
            label = { Text("Email") },
            placeholder = { Text("example@gmail.com") }
        )
    }

    @Composable
    fun TextFieldList(n: Int) {
        val textValues = remember { mutableStateListOf(*List(n) { "" }.toTypedArray()) }
        // Afficher une liste de TextField à partir des indices de 0 à n-1
        LazyColumn {
            itemsIndexed(textValues) { index, value ->
                // Créer un TextField avec la valeur et l'indice actuels
                TextField(
                    value = value,
                    label = { Text("Champ $index") },
                    // Mettre à jour la valeur dans la liste d'état lorsque le texte change
                    onValueChange = { textValues[index] = it }
                )
            }
        }
    }

    //@Preview(showBackground = true)
    @Composable
    fun InputChoice(
        value: TextFieldValue,
        onValueChange: (TextFieldValue) -> Unit,
    ){

        var text by rememberSaveable(stateSaver = TextFieldValue.Saver) {
            mutableStateOf(TextFieldValue("", TextRange(0, 128)))
        }
        TextField(
            value = value,
            onValueChange = {
                if (value != it) {
                    onValueChange(it)
                } },
            label = { Text("Entrer un choix du vote") },
            placeholder = { Text("Choix") },
            leadingIcon = {
                Icon(Icons.Filled.Done, contentDescription = "Localized description")
            },

        )
    }

    @Composable
    fun Greeting(name: String, modifier: Modifier = Modifier) {
        Text(
            text = "Hello $name!",
            modifier = modifier
        )
    }

    @Preview(showBackground = true)
    @Composable
    fun GreetingPreview() {
        IVoteTheme {
            Greeting("Android")
        }
    }



    @Composable
    fun DropdownTextField(
        modifier: Modifier = Modifier,
        label: String,
        options: List<String>,
        selectedOption: String,
        onOptionSelected: (String) -> Unit
    ) {
        // A state variable to track the expanded state of the menu
        var expanded by remember { mutableStateOf(false) }
        // A box to wrap the text field and the menu
        Box {
            // A text field with a label and a trailing icon
            TextField(
                modifier = modifier,
                value = selectedOption,
                onValueChange = { },
                label = { Text(label) },
                trailingIcon = {
                    // An icon that changes based on the expanded state
                    val icon = if (expanded) Icons.Filled.ArrowDropDown else Icons.Filled.ArrowDropDown
                    IconButton(onClick = { expanded = !expanded }) {
                        Icon(icon, "contentDescription")
                    }
                }
            )
            // A dropdown menu that shows the options
            DropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false }
            ) {
                options.forEach { option ->
                    DropdownMenuItem(onClick = {
                        onOptionSelected(option)
                        expanded = false
                    }) {
                        Text(text = option)
                    }
                }
            }
        }
    }

}