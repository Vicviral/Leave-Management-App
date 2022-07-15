package com.victorloveday.leavemanager.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.victorloveday.leavemanager.database.model.Leave
import com.victorloveday.leavemanager.databinding.ItemHistoryBinding

class HistoryAdapter(private val context: Context) :
    RecyclerView.Adapter<HistoryAdapter.LeaveViewHolder>() {

    inner class LeaveViewHolder(val binding: ItemHistoryBinding) :
        RecyclerView.ViewHolder(binding.root)

    private val diffCallback = object : DiffUtil.ItemCallback<Leave>() {
        override fun areItemsTheSame(oldItem: Leave, newItem: Leave): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Leave, newItem: Leave): Boolean {
            return oldItem == newItem
        }
    }

    val differ = AsyncListDiffer(this, diffCallback)
    private lateinit var leave: Leave

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LeaveViewHolder {
        return LeaveViewHolder(
            ItemHistoryBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: LeaveViewHolder, position: Int) {
        holder.binding.apply {
            leave = differ.currentList[position]
            holder.itemView.apply {

//                title.text = leave.title
//                description.text = leave.description

                //add click event for articles
                setOnClickListener {
                    onLeaveClickListener?.let { it(leave) }
                }

                setOnLongClickListener { view ->

                    true
                }
            }
        }
    }


    private var onLeaveClickListener: ((Leave) -> Unit)? = null

    fun setOnArticleClickListener(listener: (Leave) -> Unit) {
        onLeaveClickListener = listener
    }
}
