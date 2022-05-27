package com.example.todolist

import android.graphics.Paint.STRIKE_THRU_TEXT_FLAG
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.itemtodo.view.*


class ToDoAdapter (
    private val todos: MutableList<ToDo>
) : RecyclerView.Adapter<ToDoAdapter.ToDoViewHolder>() {

    class ToDoViewHolder (itemView: View) :RecyclerView.ViewHolder(itemView)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ToDoViewHolder {
        return ToDoViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.itemtodo,
                parent,
                false
            )
        )
    }

    fun addTodo(todo: ToDo) {
        todos.add(todo)
        notifyItemInserted(todos.size-1)
    }

    fun deleteDone() {
        todos.removeAll { todo->
            todo.isChecked
        }
        notifyDataSetChanged()
    }
    private fun toggleStrike(tvTodoTitle: TextView, isChecked: Boolean) {
        if(isChecked){
            tvTodoTitle.paintFlags = tvTodoTitle.paintFlags or STRIKE_THRU_TEXT_FLAG
        } else {
            tvTodoTitle.paintFlags = tvTodoTitle.paintFlags and STRIKE_THRU_TEXT_FLAG.inv()
        }
    }

    override fun onBindViewHolder(holder: ToDoViewHolder, position: Int) {
        var curTodo= todos[position]
        holder.itemView.apply {
            tvToDoTitle.text = curTodo.title
            cbDone.isChecked = curTodo.isChecked
            toggleStrike(tvToDoTitle, curTodo.isChecked)
            cbDone.setOnCheckedChangeListener() { _, isChecked ->
                toggleStrike(tvToDoTitle, isChecked)
                curTodo.isChecked = !curTodo.isChecked
            }
        }
    }

    override fun getItemCount(): Int {
        return todos.size
    }
}