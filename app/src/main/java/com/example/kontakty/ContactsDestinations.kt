package com.example.kontakty

import androidx.navigation.NavType
import androidx.navigation.navArgument

interface ContactsDestination {
    val route: String
}

object ContactList : ContactsDestination{
    override val route = "contactList"
}

object SingleContact: ContactsDestination {

    override val route = "singleContact"
    //"singleContact/${con.name}"
    const val contactTypeArg = "con.name"

    val routeWithArgs = "$route/{$contactTypeArg}"

    val arguments = listOf(
        navArgument(contactTypeArg) { type = NavType.StringType }
    )
}