import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.victorloveday.leavemanager.R
import com.victorloveday.leavemanager.database.model.Leave
import com.victorloveday.leavemanager.database.model.User
import com.victorloveday.leavemanager.databinding.ItemEmployeeBinding

class EmployeesAdapter(private val context: Context) :
    RecyclerView.Adapter<EmployeesAdapter.HistoryViewHolder>() {

    inner class HistoryViewHolder(val binding: ItemEmployeeBinding) :
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
    var employees = emptyList<User>()

    override fun getItemCount() = employees.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HistoryViewHolder {
        return HistoryViewHolder(
            ItemEmployeeBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: HistoryViewHolder, position: Int) {
        holder.binding.apply {
            val employee = employees[position]
            employeeName.text = employee.name
            employeeRole.text = employee.role


            //set status color
            when(employee.isOnLeave) {
                true -> {
                    isOnLeave.setBackgroundResource(R.drawable.approved_bg)
                    isOnLeave.setTextColor(Color.parseColor("#3DD598"))
                    isOnLeave.text = "On Leave"
                }
                false -> {
                    isOnLeave.setBackgroundResource(R.drawable.declined_bg)
                    isOnLeave.setTextColor(Color.parseColor("#F76332"))
                }
            }

            holder.itemView.apply {

                setOnClickListener {
                    onLeaveClickListener?.let {it(employee)}
                }

            }
        }

    }

    fun setData(employees: List<User>) {
        this.employees = employees
        notifyDataSetChanged()
    }

    private var onLeaveClickListener: ((User) -> Unit)? = null

    fun setOnLeaveClickListener(listener : (User) -> Unit) {
        onLeaveClickListener = listener
    }
    }