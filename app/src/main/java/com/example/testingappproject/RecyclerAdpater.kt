package com.example.testingappproject

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import androidx.recyclerview.widget.RecyclerView

class RecyclerAdapter(private val list: ArrayList<RecyclerDataClass>,
                      private val clickInterface: OnClick) : RecyclerView.Adapter<RecyclerAdapter.ViewHolder>() {

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val name: EditText = view.findViewById(R.id.name)
        val description: EditText= view.findViewById(R.id.description)
        val editBtn= view.findViewById<Button>(R.id.editBtn)
        val deleteBtn = view.findViewById<Button>(R.id.deleteBtn)

        init {
            view.setOnClickListener {
                clickInterface.onItemClick(adapterPosition)
            }
            editBtn.setOnClickListener {
                clickInterface.update(adapterPosition)
            }
            deleteBtn.setOnClickListener {
                clickInterface.delete(adapterPosition)
            }
        }
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.fragment_training, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = list[position]
        holder.name.setText(item.name)
        holder.description.setText(item.description)
    }

    override fun getItemCount(): Int = list.size

    interface OnClick {
        fun save(position: Int)        // Optional: only if you're editing inline
        fun update(position: Int)
        fun delete(position: Int)
        fun onItemClick(position: Int)

    }
}
