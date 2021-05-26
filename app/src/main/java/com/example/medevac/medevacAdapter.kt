package com.example.medevac

import android.graphics.Paint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.tester.medaData
import kotlinx.android.synthetic.main.item_medevac.view.*

class TodoAdapter (
    private val todos: MutableList<medaData>
) :RecyclerView.Adapter<TodoAdapter.medavacViewHolder>() {
    class medavacViewHolder(itemView: View): RecyclerView.ViewHolder(itemView)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): medavacViewHolder {
        return medavacViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.item_medevac,
                parent,
                false
            )
        )
    }

    fun addTodo(todo: medaData){
        todos.add(todo)
        notifyItemInserted(todos.size -1)
    }

    fun deleteDoneTodos() {
        todos.removeAll { medaData ->
            medaData.isChecked
        }
        notifyDataSetChanged()
    }

    fun UploadTodo(){
    }

    private fun toggleStrikeThrough(tvMedevac: TextView, isChecked: Boolean){
        if(isChecked) {
            tvMedevac.paintFlags = tvMedevac.paintFlags or Paint.STRIKE_THRU_TEXT_FLAG
        } else {
            tvMedevac.paintFlags = tvMedevac.paintFlags and Paint.STRIKE_THRU_TEXT_FLAG.inv()
        }
    }

    override fun getItemCount(): Int {
        return todos.size
    }

    override fun onBindViewHolder(holder: medavacViewHolder, position: Int) {
        val curTodo = todos[position]
        holder.itemView.apply {
            tvTodoTitle.text = curTodo.title
            cbDone.isChecked = curTodo.isChecked
            toggleStrikeThrough(tvTodoTitle, curTodo.isChecked)
            cbDone.setOnCheckedChangeListener { _, isChecked ->
                toggleStrikeThrough(tvTodoTitle, isChecked)
                curTodo.isChecked = !curTodo.isChecked
            }
        }
    }
}