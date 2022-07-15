import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.victorloveday.leavemanager.database.model.Leave
import com.victorloveday.leavemanager.databinding.ItemHistoryBinding

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
            leaveTittle.text = leave.title
            leaveType.text = leave.type
            leaveDescription.text = leave.reason
            leaveStatus.text = leave.status
            leaveDuration.text = "${leave.startDate} - ${leave.endDate}"

            holder.itemView.apply {
                setOnLongClickListener {
                    true
                }

                shareLeaveDetailsIcon.setOnClickListener {
                    onLeaveClickListener?.let {it(leave)}
                }

            }
        }

    }

    fun setData(leave: List<Leave>) {
        this.leaves = leave
        notifyDataSetChanged()
    }

    private var onLeaveClickListener: ((Leave) -> Unit)? = null

    fun setOnLeaveClickListener(listener : (Leave) -> Unit) {
        onLeaveClickListener = listener
    }
}