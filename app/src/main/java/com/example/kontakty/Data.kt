package com.example.kontakty

object Data {

    val contacts = listOf(
        Contact(
            "Jan Kowalski",
            "997123456",
            "https://imgproxy.attic.sh/cWSYexkjm0k2VA5E-5nataLFzALhjYVjaDJ82ZA95RE/rs:fit:768:768:1:1/t:1:FF00FF:false:false/pngo:false:true:256/aHR0cHM6Ly9hdHRp/Yy5zaC9vOWtoenhl/NDM3N2FpMDF1MnAy/ZndramR1cGtp.png"

        ),
        Contact(
            "Marek Nowak",
            "2115000222",
            "https://imgproxy.attic.sh/unsafe/rs:fit:768:768:1:1/t:1:FF00FF:false:false/pngo:false:true:256/aHR0cHM6Ly9hdHRp/Yy5zaC80aWlienRi/cXR2NXV4a2luN3E3/a2F0M3A0OXd2.png"
        ),
        Contact(
            "Ada Wysocka",
            "777999111",
            "https://imgproxy.attic.sh/UuW-SSVOou99M3U40RJAv81atbGshoHmg22kxf9_OKM/rs:fit:768:768:1:1/t:1:FF00FF:false:false/pngo:false:true:256/aHR0cHM6Ly9hdHRp/Yy5zaC9wYnJ6bW15/OGdwdWdvM3Fnemhu/eHV0cnM3Z2xj.png"
        )
    )

    fun getGeneratedContacts() = mutableListOf<Contact>().apply {
        for (i in 0..1000) {
            add(
                contacts[i%3].copy(
                    name = "${contacts[i%3].name} $i"
                )
            )
        }
    }
}
