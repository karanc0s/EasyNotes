package com.karan.easynotes.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatTextView
import androidx.recyclerview.widget.RecyclerView
import com.karan.easynotes.R
import com.karan.easynotes.eventhandler.EventHandler
import com.karan.easynotes.model.Note


class NoteAdapter(
    private val context: Context ,
    private var list: ArrayList<Note> ,
    private val listener : EventHandler)
    : RecyclerView.Adapter<NoteAdapter.ViewHolder>() {


    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        val tvData : AppCompatTextView = itemView.findViewById(R.id.tv_data)
        val tvTimeDate : AppCompatTextView = itemView.findViewById(R.id.tv_time_date)

        init {
            itemView.setOnClickListener{
                listener.onClickEventHandler(adapterPosition)
            }
        }
    }


    fun setData(notes : ArrayList<Note>){
        this.list = notes
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.note_single , parent ,false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val note = list[position]
        holder.tvData.text = note.data
        holder.tvTimeDate.text = note.date
    }
}