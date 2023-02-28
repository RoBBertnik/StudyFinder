package com.example.studyfinder.viewModel

import android.content.Context
import android.content.Intent
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.studyfinder.model.Course
import com.example.studyfinder.model.auth
import com.example.studyfinder.model.User
import com.example.studyfinder.model.retrofit.userAPI
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.joinAll
import kotlinx.coroutines.tasks.await

class UserViewModel : ViewModel(){

    private var _email: String = ""
    val email get() = _email

    fun setEmail(email: String) {
        _email = email
    }

    private var _username: String = ""
    val username get() = _username

    fun setUsername(username: String) {
        _username = username
    }

    private var _password: String = ""
    val password get() = _password

    fun setPassword(password: String) {
        _password = password
    }

    private var _confirmedPassword: String = ""
    val confirmedPassword get() = _confirmedPassword

    fun setConfirmedPassword(password: String){
        _confirmedPassword = password
    }

    private var _firstName: String = ""
    val firstName get() = _firstName

    fun setFirstName(firstName: String) {
        _firstName = firstName
    }

    private var _lastName: String = ""
    val lastName get() = _lastName

    fun setLastName(lastName: String) {
        _lastName = lastName
    }


    suspend fun addCourse(course: Course){
        currentUser!!.courses.add(course.name)
        userAPI.addCourseToUser(currentUser!!.email, course.name)
    }

    suspend fun removeCourse(course: Course){
        currentUser!!.courses.remove(course.name)
        userAPI.deleteCourseFromUser(currentUser!!.email, course.name)
    }

    private var _currentUser: User? = null
    val currentUser get() = _currentUser

    fun setCurrentUser(user: User){
        _currentUser = user
    }

    private var _otherUser: User? = null
    val otherUser get() = _otherUser

    fun setOtherUser(user: User){
        _otherUser = user
    }

    fun computeCurrentUser(){
        _currentUser = User(email, null, firstName, lastName, username)
    }

    fun computeParameters(){
        this._email = currentUser!!.email
        this._username = currentUser!!.username
        this._firstName = currentUser!!.firstName
        this._lastName = currentUser!!.lastName

    }



    suspend fun checkValid(): Boolean{
        var result = false
        val task = auth.signInWithEmailAndPassword(email, password).addOnSuccessListener(){
            result = true
        }.addOnFailureListener(){
            Log.e("SignInUserActivity", it.toString())

        }
        task.await()
        val task2 = this.viewModelScope.async {
            _currentUser = userAPI.getUserByEmail(email).body()!!
        }
        task2.await()
        Log.e("ResultCheck", currentUser!!.firstName)
        return result
    }

    suspend fun signUp(): Boolean{
        var result = false
        if(email.equals("") || password.equals("") || confirmedPassword.equals("") || firstName.equals("") || lastName.equals("") || username.equals("")){
            return result
        }
       if(!password.equals(confirmedPassword)){
            return result
        }

        val task = auth.createUserWithEmailAndPassword(email, password).addOnSuccessListener {
            result = true
        }.addOnFailureListener(){
            Log.e("SignUpUserActivity", it.toString())
        }
        task.await()
        val task2 = this.viewModelScope.async {
            val user = User(email, password, firstName, lastName, username)
            _currentUser = userAPI.saveUser(user).body()
        }
        task2.await()
        Log.e("ResultCheck", result.toString())
        return result

    }

    fun goToMainMenuActivity(context: Context,  intent: Intent){
        Log.e("GoToMainMenuActivity",  currentUser!!.firstName)
        val coursesList: ArrayList<String> = ArrayList<String>()
        coursesList.addAll(currentUser!!.courses)
        intent.putExtra("Email", currentUser!!.email)
        intent.putExtra("FirstName", currentUser!!.firstName)
        intent.putExtra("LastName", currentUser!!.lastName)
        intent.putExtra("Username", currentUser!!.username)
        intent.putStringArrayListExtra("Courses", coursesList)
        context.startActivity(intent)
    }

    fun partOfCourse(course: Course): Boolean{
        currentUser!!.courses.forEach{ myCourseName ->
            if(course.name == myCourseName){
                return true
            }
        }
        return false
    }

}