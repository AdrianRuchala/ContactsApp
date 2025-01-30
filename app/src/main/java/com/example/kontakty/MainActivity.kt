package com.example.kontakty

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import com.example.kontakty.ui.theme.KontaktyTheme
import com.skydoves.landscapist.components.rememberImageComponent
import com.skydoves.landscapist.glide.GlideImage
import com.skydoves.landscapist.placeholder.placeholder.PlaceholderPlugin
import androidx.navigation.compose.rememberNavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {

            val navController = rememberNavController()

            KontaktyTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(16.dp),
                    color = MaterialTheme.colorScheme.background
                ) {
                    ContactsNavHost(navController = navController, modifier = Modifier)
                }

            }
        }
    }
}

@Composable
fun ContactPlate(navController: NavController, con: Contact, modifier: Modifier = Modifier)
{
    Row(
        modifier = modifier
            .fillMaxWidth()
            .border(2.dp, MaterialTheme.colorScheme.primary, MaterialTheme.shapes.small)
            .background(
                color = MaterialTheme.colorScheme.surfaceVariant,
                shape = MaterialTheme.shapes.small
            )
            .padding(horizontal = 4.dp, vertical = 4.dp)
            .clickable { navController.navigateSingleTopTo("singleContact/${con.name}") },
        verticalAlignment = Alignment.CenterVertically,
    ){
        GlideImage(
            imageModel = {
                con.url
            },
            modifier = modifier
                .size(40.dp)
                .clip(CircleShape)
                .border(2.dp, MaterialTheme.colorScheme.secondary, shape = CircleShape),
            component = rememberImageComponent {
                +PlaceholderPlugin.Failure(painterResource(id = R.drawable.ic_launcher_foreground))
            }
        )
        Column(
            modifier = modifier.padding(horizontal = 5.dp)
        ){
            Text(
                text = con.name,
                modifier = modifier,
                style = MaterialTheme.typography.titleMedium
            )
            Text(
                text = con.number,
                modifier = modifier
            )
        }
    }
}


@Composable
fun ContactList(contacts: List<Contact>, navController: NavController){
    LazyColumn(verticalArrangement = Arrangement.spacedBy(8.dp)) {
        items(contacts){ contact ->
            ContactPlate(navController, contact)
        }
    }
}

@Composable
fun SingleContact(con: Contact, modifier: Modifier = Modifier, navController: NavController){
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.Start
    ) {
        Icon(
            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
            contentDescription = null,
            modifier = modifier.clickable { navController.navigateUp() }
        )
    }
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        GlideImage(
            imageModel = {
                con.url
            },
            modifier = modifier
                .size(120.dp)
                .clip(CircleShape)
                .border(2.dp, MaterialTheme.colorScheme.secondary, shape = CircleShape),
            component = rememberImageComponent {
                +PlaceholderPlugin.Failure(painterResource(id = R.drawable.ic_launcher_foreground))
            }
        )
        Spacer(modifier = modifier.padding(2.dp))
        Text(
            text = con.name,
            modifier = modifier,
            style = MaterialTheme.typography.titleLarge
        )
        Text(
            text = con.number,
            modifier = modifier,
            style = MaterialTheme.typography.titleMedium
        )
    }
}

@Composable
fun ContactsNavHost(
    navController: NavHostController,
    modifier: Modifier
){
    NavHost(
        navController = navController,
        startDestination = ContactList.route,
        modifier = modifier
    ) {
        composable(ContactList.route) {
            ContactList(Data.getGeneratedContacts(), navController)
        }
        composable(
            route = SingleContact.routeWithArgs,
            arguments = SingleContact.arguments
        ) { navBackStackEntry ->
            val contactType =
                navBackStackEntry.arguments?.getString(SingleContact.contactTypeArg)
            val contact = Data.getGeneratedContacts().firstOrNull { it.name == contactType }
            contact?.let { SingleContact(contact, modifier, navController) }
        }
    }
}

fun NavController.navigateSingleTopTo(route: String) =
    this.navigate(route) {
        popUpTo(
            this@navigateSingleTopTo.graph.findStartDestination().id
        ) {
            saveState = true
        }
        launchSingleTop = true
        restoreState = true
    }

/*
@Preview(showBackground = true)
@Composable
fun ContactPlatePreview() {
    KontaktyTheme {
        ContactPlate(Contact("Malik", "997", "https://images.genius.com/dfa9b22886ee34bab41e434cbe801ad8.513x513x1.jpg"))
    }
}


@Preview(showBackground = true)
@Composable
fun ContactListPreview(){
    KontaktyTheme {
        ContactList(Data.contacts)
    }
}


@Preview(showBackground = true)
@Composable
fun SingleContactPreview(){
    KontaktyTheme {
        SingleContact(Contact("Malik","997", "https://images.genius.com/dfa9b22886ee34bab41e434cbe801ad8.513x513x1.jpg"))
    }
}

 */
