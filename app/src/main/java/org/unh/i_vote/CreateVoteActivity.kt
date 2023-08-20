package org.unh.i_vote

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.appcompat.view.ContextThemeWrapper
import androidx.appcompat.widget.Toolbar
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Card
import androidx.compose.material.Divider
import androidx.compose.material.DropdownMenu
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedButton
import androidx.compose.material.Scaffold
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.Done
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
import androidx.compose.ui.viewinterop.AndroidView
import org.unh.i_vote.ui.theme.IVoteTheme


class CreateVoteActivity : ComponentActivity() {

    fun createVote(){
        val intent = intent // Get the intent that started this activity

        val email = intent.getStringExtra("email") // Get the string with the key "message" from the intent

        Toast.makeText(this, email, Toast.LENGTH_SHORT).show() // Display a toast with the
        //val fbm = FirebaseManager()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //val toolbar = Toolbar(this).apply { title = "My App" }

        // Set the toolbar as the support action bar

        //setActionBar(toolbar)

        setContent {
            MaterialTheme {
                val choices = remember { mutableStateListOf<String>() }
                //val count = remember { mutableStateOf(0) }

                val typeOfVote = listOf("Choix binaire", "Choix mutiple")
                val selectedTypeOfVote = remember { mutableStateOf<String>(typeOfVote[0]) }

                val scrollState = rememberScrollState()
                // A surface container using the 'background' color from the theme
                Scaffold(
                    topBar = {
                        //AndroidView(factory = { toolbar })
                        TopAppBar(
                            backgroundColor = Color(0xFF6750A4),
                            title = { Text("Creer un vote") },
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
                                modifier = Modifier
                                    .fillMaxSize()
                                    .verticalScroll(scrollState)
                                    .padding(horizontal = 8.dp, vertical = 8.dp),
                            ) {
                                //InputEmail()
                                //Spacer(modifier = Modifier.padding(8.dp))
                                var subject by rememberSaveable(stateSaver = TextFieldValue.Saver) {
                                    mutableStateOf(TextFieldValue("", TextRange(0, 256)))
                                }

                                TextField(
                                    value = subject,
                                    onValueChange = { subject = it },
                                    label = { Text("Entrer le sujet du vote") },
                                    placeholder = { Text("Sujet") },
                                    maxLines = 5,
                                    singleLine = false,
                                )
                                Spacer(modifier = Modifier.padding(8.dp))

                                /*var number by rememberSaveable(stateSaver = TextFieldValue.Saver) {
                                    mutableStateOf(TextFieldValue("", TextRange(0, 7)))
                                }*/


                                Box(modifier = Modifier.size(height = 50.dp, width = 160.dp)){

                                    // A dropdown textfield with a label and options
                                    DropdownTextField(
                                        label = "Type de vote",
                                        options = typeOfVote,
                                        selectedOption = selectedTypeOfVote.value,
                                        onOptionSelected = { selectedTypeOfVote.value = it }
                                    )
                                }
                                Spacer(modifier = Modifier.padding(8.dp))

                                ///----------------------------------------------------
                                if(selectedTypeOfVote.value == typeOfVote[1]){
                                    Column (horizontalAlignment = Alignment.CenterHorizontally) {
                                        Row{
                                            Spacer(modifier = Modifier.padding(horizontal = 8.dp))
                                            OutlinedButton(
                                                onClick = { choices.removeAt(0) }, // Increment the count on click
                                                border = BorderStroke(
                                                    1.dp, MaterialTheme.colors.primarySurface), // Set the border width and color
                                                shape = RoundedCornerShape(8.dp) // Set the corner radius
                                            ) {
                                                Text(text = "Remove choice") // Display the text and count
                                            }
                                            Spacer(modifier = Modifier.padding(horizontal = 16.dp))
                                            OutlinedButton(
                                                onClick = { choices.add("__") }, // Increment the count on click
                                                border = BorderStroke(
                                                    1.dp, MaterialTheme.colors.primarySurface), // Set the border width and color
                                                shape = RoundedCornerShape(8.dp) // Set the corner radius
                                            ) {
                                                Text(text = "Add choice") // Display the text and count
                                            }
                                            Spacer(modifier = Modifier.padding(horizontal = 8.dp))
                                        }
                                        Text(text = "Number of choice ${choices.size}/5")
                                    }
                                }
                                ///----------------------------------------------------

                                Spacer(modifier = Modifier.padding(8.dp))
                                Divider()
                                Spacer(modifier = Modifier.padding(8.dp))
                                Column(horizontalAlignment = Alignment.CenterHorizontally){
                                    Card(
                                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 8.dp),
                                        border = BorderStroke(1.dp, Color.Gray),
                                    ){
                                        Column(
                                            modifier = Modifier.padding(8.dp),
                                            horizontalAlignment = Alignment.CenterHorizontally
                                        ){
                                            //if (number.isNotEmpty())
                                            //count.value = number.toInt();
                                            Box(modifier = Modifier.size(width = 300.dp, height = 10.dp))
                                            Text(text = "Les choix du vote", fontSize = 22.sp, fontWeight = FontWeight.Bold)
                                            Divider(modifier = Modifier.padding(8.dp))
                                            if(selectedTypeOfVote.value == typeOfVote[0]){
                                                Text("Oui   |    Non", fontSize = 18.sp)
                                            }else{
                                                choices.forEach { text ->
                                                    InputChoice(0)
                                                    //Text(text = text)
                                                    Spacer(modifier = Modifier.padding(8.dp)) // Add some space between texts
                                                }
                                            }

                                        }
                                    }
                                }


                            }
                        }
                    },
                )
            }
        }
    }

    @Composable
    fun Padding(content: @Composable() ()-> Unit){
        Surface(modifier = Modifier.padding(horizontal = 8.dp, vertical = 16.dp)) {
            content
        }
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

    //@Preview(showBackground = true)
    @Composable
    fun InputChoice(index: Int){

        var text by rememberSaveable(stateSaver = TextFieldValue.Saver) {
            mutableStateOf(TextFieldValue("", TextRange(0, 128)))
        }
        TextField(
            value = text,
            onValueChange = { text = it },
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