package com.example.todoappkpc.view

import android.view.View
import android.widget.CompoundButton
import com.example.todoappkpc.model.Todo

interface TodoItemInterface{
    fun onCheckedChange(cb: CompoundButton, isChecked: Boolean, todo: Todo)
    fun onTodoEditClick(v:View)
}

interface FragmentEditTodoInterface{
    fun onRadioClick(v:View, todo: Todo)
    fun onTodoSaveClick(v:View, todo: Todo)
}

interface DateClickListener{
    fun onDateClick(v:View)
}

interface TimeClickListener{
    fun onTimeClick(v:View)
}