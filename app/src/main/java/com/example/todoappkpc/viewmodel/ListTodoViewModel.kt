package com.example.todoappkpc.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.room.Room
import com.example.todoappkpc.model.Todo
import com.example.todoappkpc.model.TodoDatabase
import com.example.todoappkpc.util.buildDB
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext

class ListTodoViewModel(application: Application):AndroidViewModel(application), CoroutineScope {
    val todoLD = MutableLiveData<List<Todo>>()
    val todoLoadErrorLD = MutableLiveData<Boolean>()
    val loadingLD = MutableLiveData<Boolean>()
    private var job = Job()

    override val coroutineContext: CoroutineContext
        get() = job + Dispatchers.IO

    fun refresh(){
        loadingLD.value = true
        todoLoadErrorLD.value = false
        launch {
            val db = buildDB(getApplication())
            /*todoLD.postValue(db.todoDao().selectAllTodo())*/
            todoLD.postValue(db.todoDao().isCheck())//tugas week 9
        }
    }

    fun clearTask(todo: Todo){
        launch {
            val db = Room.databaseBuilder(getApplication(), TodoDatabase::class.java,
            "newtododb").build()
            db.todoDao().deleteTodo(todo)
            todoLD.postValue(db.todoDao().selectAllTodo())
        }
    }

    //tugas week 9
    fun taskToDo(is_done: Int, uuid: Int){
        launch{
            val db = Room.databaseBuilder(getApplication(), TodoDatabase::class.java,
            "newtododb").build()
            db.todoDao().check(is_done, uuid)
            todoLD.postValue(db.todoDao().isCheck())
        }
    }
}