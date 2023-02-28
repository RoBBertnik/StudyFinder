package com.example.studyfinder.viewModel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.studyfinder.R
import com.example.studyfinder.model.Course
import com.example.studyfinder.model.User
import com.example.studyfinder.model.retrofit.userAPI
import kotlinx.coroutines.*

class CourseViewModel : ViewModel() {

    private var _users: MutableLiveData<List<User>> = MutableLiveData()
    val users: LiveData<List<User>> get() = _users

    private var _currentCourse: Course? = null
    val currentCourse get() = _currentCourse

    fun changeCurrentCourse(course: Course) {
        _currentCourse = course
    }


    fun getCourseByString(courseString: String): Course {

        if (courseString.equals(Course.Mathe_1.name)) {
            return Course.Mathe_1
        }
        if (courseString.equals(Course.Mathe_2.name)) {
            return Course.Mathe_2
        }
        if (courseString.equals(Course.Ap_1.name)) {
            return Course.Ap_1
        }
        if (courseString.equals(Course.Ap_2.name)) {
            return Course.Ap_2
        }
        if (courseString.equals(Course.MoCo.name)) {
            return Course.MoCo
        } else {
            throw Exception("Kein Kurs mit dem Namen existiert!")
        }
    }

    fun getPictureForCourse(): Int {
        return when (currentCourse) {
            Course.Mathe_1 -> R.drawable.ic_mathe_symbol_24dp
            Course.Mathe_2 -> R.drawable.ic_mathe_symbol_24dp
            Course.Ap_1 -> R.drawable.ic_ap_symbol_24dp
            Course.Ap_2 -> R.drawable.ic_ap_symbol_24dp
            Course.MoCo -> R.drawable.ic_moco_symbol_24dp
            null -> throw Exception("Es gibt keinen currentCourse!")
        }
    }

    suspend fun getCourseMembers() {
        delay(100)
        withContext(Dispatchers.IO) {
            _users.postValue(userAPI.getUsersByCourse(currentCourse!!.name).body()!!)
            Log.e("getCourseMembersValueAPI", users.value.toString())

        }
    }


}