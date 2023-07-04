package com.example.todoappkpc.view

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.CompoundButton
import android.widget.ImageView
import androidx.databinding.DataBindingUtil
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.example.todoappkpc.R
import com.example.todoappkpc.databinding.TodoItemLayoutBinding
import com.example.todoappkpc.model.Todo

class TodoListAdapter(val todoList:ArrayList<Todo>, val adapter: (Todo) -> Unit):RecyclerView.Adapter<TodoListAdapter.TodoViewHolder>(),
TodoItemInterface{
    class TodoViewHolder(val view: TodoItemLayoutBinding): RecyclerView.ViewHolder(view.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TodoViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = TodoItemLayoutBinding.inflate(inflater, parent, false)
        return TodoViewHolder(view)
    }

    override fun onBindViewHolder(holder: TodoViewHolder, position: Int) {
        /*val checkTask = holder.view.findViewById<CheckBox>(R.id.checkTask)
        checkTask.text = todoList[position].title
        checkTask.isChecked = false
        
        checkTask.setOnCheckedChangeListener { compoundButton, isChecked ->
            if(isChecked){
                adapter(todoList[position])
            }
        }

        val imgEdit = holder.view.findViewById<ImageView>(R.id.imgEdit)
        imgEdit.setOnClickListener {
            val uuid = todoList[position].uuid
            val action = TodoListFragmentDirections.actionEditFragment(uuid)
            Navigation.findNavController(it).navigate(action)
        }*/

        //data binding week 10
        holder.view.todo = todoList[position]
        holder.view.checklistener = this
        holder.view.editlistener = this
        holder.view.checkTask.isChecked = false
    }

    override fun getItemCount(): Int {
        return todoList.size
    }

    fun updateTodoList(newTodoList: List<Todo>){
        todoList.clear()
        todoList.addAll(newTodoList)
        notifyDataSetChanged()
    }

    override fun onCheckedChange(cb: CompoundButton, isChecked: Boolean, todo: Todo) {
        if(isChecked){ //update week 10
            adapter(todo)
        }
    }

    override fun onTodoEditClick(v: View) {
        val uuid = v.tag.toString().toInt()
        val action = TodoListFragmentDirections.actionEditFragment(uuid)
        Navigation.findNavController(v).navigate(action)
    }
}