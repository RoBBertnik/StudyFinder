package com.example.studyfinder.model

class User(var email: String,
           var password: String? = null,
           var firstName: String,
           var lastName: String,
           var username: String) {

    var courses = mutableListOf<String>()


}
val testPerson = User(email = "Milanlittfin@Email.de", password = "123456", firstName = "Milan", lastName = "Littfin", username = "Millif")