package com.example.todoappkpc.view

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.os.Bundle
import android.text.format.DateFormat
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import androidx.work.workDataOf
import com.example.todoappkpc.R
import com.example.todoappkpc.databinding.FragmentCreateTodoBinding
import com.example.todoappkpc.model.Todo
import com.example.todoappkpc.util.NotificationHelper
import com.example.todoappkpc.util.TodoWorker
import com.example.todoappkpc.viewmodel.DetailTodoViewModel
import java.util.Calendar
import java.util.concurrent.TimeUnit

class CreateTodoFragment : Fragment(), FragmentEditTodoInterface, DateClickListener, TimeClickListener,
    DatePickerDialog.OnDateSetListener, TimePickerDialog.OnTimeSetListener {
    private lateinit var viewModel: DetailTodoViewModel
    private lateinit var databinding:FragmentCreateTodoBinding
    var year = 0
    var month = 0
    var day = 0
    var hour = 0
    var minute = 0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        databinding = DataBindingUtil.inflate(inflater, R.layout.fragment_create_todo, container, false)
        return databinding.root
        //return inflater.inflate(R.layout.fragment_create_todo, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(this).get(DetailTodoViewModel::class.java)

        databinding.todo = Todo("", "", 3, 0, 0)
        databinding.radiolistener = this
        databinding.savelistener = this
        databinding.dateListener = this
        databinding.timeListener = this


        /*val btnAdd = view.findViewById<Button>(R.id.btnAdd)
        btnAdd.setOnClickListener {
            val myWorkRequest = OneTimeWorkRequestBuilder<TodoWorker>()
                .setInitialDelay(30, TimeUnit.SECONDS)
                .setInputData(workDataOf(
                    "title" to "Todo Created",
                    "message" to "A new todo has been created! Stay Focus!")
                )
                .build()
            WorkManager.getInstance(requireContext()).enqueue(myWorkRequest)
            val txtTitle = view.findViewById<EditText>(R.id.txtTitle)
            val txtNotes = view.findViewById<EditText>(R.id.txtNotes)

            val radioGroup = view.findViewById<RadioGroup>(R.id.radioGroupPriority)
            val radioButton = view.findViewById<RadioButton>(radioGroup.checkedRadioButtonId)

            val todo = Todo(txtTitle.text.toString(), txtNotes.text.toString(), radioButton.tag.toString().toInt(), 0)
            viewModel.addTodo(todo)
            Toast.makeText(view.context, "data added", Toast.LENGTH_LONG).show()
            Navigation.findNavController(it).popBackStack()
        }*/
    }

    override fun onRadioClick(v: View, todo: Todo) {
        todo.priority = v.tag.toString().toInt()
    }

    override fun onTodoSaveClick(v: View, todo: Todo) {
        val c = Calendar.getInstance()
        c.set(year,month,day,hour,minute, 0)

        val today = Calendar.getInstance()
        val diff = (c.timeInMillis/1000L) - (today.timeInMillis/1000L)

        databinding!!.todo!!.todo_date = (c.timeInMillis/1000L).toInt()

        viewModel.addTodo(todo)
        Toast.makeText(v.context, "Todo Created", Toast.LENGTH_SHORT).show()
        val myWorkRequest = OneTimeWorkRequestBuilder<TodoWorker>()
            .setInitialDelay(diff, TimeUnit.SECONDS)
            .setInputData(workDataOf(
                "title" to "Todo is due",
                "message" to "Todo ${databinding.todo!!.title} due date is approaching")
            )
            .build()
        WorkManager.getInstance(requireContext()).enqueue(myWorkRequest)
        Navigation.findNavController(v).popBackStack()
    }

    override fun onDateClick(v: View) {
        val c = Calendar.getInstance()
        val y = c.get(Calendar.YEAR)
        val m = c.get(Calendar.MONTH)
        val d = c.get(Calendar.DAY_OF_MONTH)

        activity?.let {
            DatePickerDialog(it, this, y, m, d).show()
        }
    }

    override fun onTimeClick(v: View) {
        val c = Calendar.getInstance()
        val h = c.get(Calendar.HOUR_OF_DAY)
        val m = c.get(Calendar.MINUTE)

        TimePickerDialog(activity, this, h, m, DateFormat.is24HourFormat(activity)).show()
    }

    override fun onDateSet(view: DatePicker?, year: Int, month: Int, dayOfMonth: Int) {
        databinding!!.txtDate.setText(
            dayOfMonth.toString().padStart(2,'0') + "/" +
                    month.toString().padStart(2, '0') + "/" +
                    year.toString().padStart(2, '0')
        )
        this.year = year
        this.month = month
        this.day = dayOfMonth
    }

    override fun onTimeSet(view: TimePicker?, hourOfDay: Int, minute: Int) {
        databinding!!.txtTime.setText(
            hourOfDay.toString().padStart(2,'0') + ":" +
                    minute.toString().padStart(2, '0')
        )
        this.hour = hourOfDay
        this.minute = minute
    }
}