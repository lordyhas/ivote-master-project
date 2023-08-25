package org.unh.i_vote

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Scaffold
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import org.unh.i_vote.data.model.ItemVoteModel
import org.unh.i_vote.ui.theme.IVoteTheme


class VoteActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //val intent = intent
        //val voteInfo: ItemVoteModel? = intent.getSerializableExtra("voteInfo") as ItemVoteModel?
        //val vote = voteInfo?.vote;
        setContent {
            IVoteTheme {
                // A surface container using the 'background' color from the theme
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
                            Column {

                            }
                        }

                    }
                )
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

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    IVoteTheme {
        Greeting("Android")
    }
}