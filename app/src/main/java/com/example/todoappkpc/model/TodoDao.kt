package com.example.todoappkpc.model

import androidx.room.*
import java.util.UUID

@Dao
interface TodoDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(vararg todo: Todo)

    @Query("SELECT * FROM todo ORDER BY priority DESC")
    fun selectAllTodo(): List<Todo>

    @Query("SELECT * FROM todo WHERE uuid= :id")
    fun selectTodo(id:Int):Todo

    @Query("UPDATE todo SET title=:title, notes=:notes, priority=:priority WHERE uuid=:id")
    fun update(title:String, notes:String, priority:Int, id: Int)

    @Query("UPDATE todo SET is_done=:is_done WHERE uuid=:id")
    fun check(is_done:Int, id: Int)

    @Query("SELECT * FROM todo WHERE is_done=0")
    fun isCheck(): List<Todo>

    @Update
    fun updateTodo(vararg todo: Todo)

    @Delete
    fun deleteTodo(todo: Todo)
}