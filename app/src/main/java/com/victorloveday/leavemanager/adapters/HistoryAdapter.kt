import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.victorloveday.leavemanager.R
import com.victorloveday.leavemanager.database.model.Leave
import com.victorloveday.leavemanager.databinding.ItemHistoryBinding
import com.victorloveday.leavemanager.utils.DateFormatter
import java.text.DateFormat
import java.text.SimpleDateFormat

class HistoryAdapter(private val context: Context) :
    RecyclerView.Adapter<HistoryAdapter.HistoryViewHolder>() {

    inner class HistoryViewHolder(val binding: ItemHistoryBinding) :
        RecyclerView.ViewHolder(binding.root)

    private val diffCallback = object : DiffUtil.ItemCallback<Leave>() {
        override fun areItemsTheSame(oldItem: Leave, newItem: Leave): Boolean {
            return oldItem.id== newItem.id
        }

        override fun areContentsTheSame(oldItem: Leave, newItem: Leave): Boolean {
            return oldItem == newItem
        }
    }

    private val differ = AsyncListDiffer(this, diffCallback)
    var leaves = emptyList<Leave>()

    override fun getItemCount() = leaves.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HistoryViewHolder {
        return HistoryViewHolder(
            ItemHistoryBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: HistoryViewHolder, position: Int) {
        holder.binding.apply {
            val leave = leaves[position]
            leaveTittle.text = leave.leave_title
            leaveType.text = leave.leave_type
            leaveDescription.text = leave.leave_message
            leaveStatus.text = leave.status
            leaveDuration.text = leave.leave_duration
            leaveYear.text = leave.start_date


            //set status color
            when(leave.status) {
                "Pending" -> {
                    leaveStatus.setBackgroundResource(R.drawable.pending_bg)
                    leaveStatus.setTextColor(Color.parseColor("#F76332"))
                }
                "Declined" -> {
                    leaveStatus.setBackgroundResource(R.drawable.declined_bg)
                    leaveStatus.setTextColor(Color.parseColor("#FFCC0000"))
                }
                "Approved" -> {
                    leaveStatus.setBackgroundResource(R.drawable.approved_bg)
                    leaveStatus.setTextColor(Color.parseColor("#3DD598"))
                }
            }

            holder.itemView.apply {
                setOnLongClickListener {
                    onLeaveLongClickListener?.let {it(leave)}
                    deleteLeave.visibility = View.VISIBLE
                    shareLeaveDetailsIcon.visibility = View.VISIBLE

                    leaveTime.visibility = View.INVISIBLE
                    dot2.visibility = View.INVISIBLE

                    true
                }

                setOnClickListener {
                    if (deleteLeave.isVisible == true) {
                        deleteLeave.isVisible = false
                        shareLeaveDetailsIcon.isVisible = false

                        leaveTime.isVisible = true
                        dot2.isVisible = true
                    }
                }

                shareLeaveDetailsIcon.setOnClickListener {
                    onLeaveClickListener?.let {it(leave)}
                }
                deleteLeave.setOnClickListener {
                    onLeaveClickListener?.let {it(leave)}
                    deleteItem?.let { it(true) }
                }

            }
        }

    }

    fun setData(leave: List<Leave>) {
        this.leaves = leave
        notifyDataSetChanged()
    }

    private var onLeaveClickListener: ((Leave) -> Unit)? = null
    private var onLeaveLongClickListener: ((Leave) -> Unit)? = null
    private var deleteItem: ((Boolean) -> Unit)? = null

    fun setOnLeaveClickListener(listener : (Leave) -> Unit) {
        onLeaveClickListener = listener
        onLeaveLongClickListener = listener
    }

    fun onDeleteItem(bool : (Boolean) -> Unit) {
        deleteItem = bool
    }
}