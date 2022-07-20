import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.victorloveday.leavemanager.R
import com.victorloveday.leavemanager.database.model.Leave
import com.victorloveday.leavemanager.database.model.Notification
import com.victorloveday.leavemanager.databinding.ItemNotificationBinding

class NotificationAdapter(private val context: Context) :
    RecyclerView.Adapter<NotificationAdapter.HistoryViewHolder>() {

    inner class HistoryViewHolder(val binding: ItemNotificationBinding) :
        RecyclerView.ViewHolder(binding.root)

    private val diffCallback = object : DiffUtil.ItemCallback<Notification>() {
        override fun areItemsTheSame(oldItem: Notification, newItem: Notification): Boolean {
            return oldItem.id== newItem.id
        }

        override fun areContentsTheSame(oldItem: Notification, newItem: Notification): Boolean {
            return oldItem == newItem
        }
    }

    private val differ = AsyncListDiffer(this, diffCallback)
    var notifications = emptyList<Notification>()

    override fun getItemCount() = notifications.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HistoryViewHolder {
        return HistoryViewHolder(
            ItemNotificationBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: HistoryViewHolder, position: Int) {
        holder.binding.apply {
            val notification = notifications[position]
            notificationTitle.text = notification.title
            notificationBody.text = notification.body
            notificationDate.text = notification.date
            notificationTime.text = notification.time

            //set status color
            when(notification.title) {
                "Application Approved" -> {
                    notificationIcon.setImageResource(R.drawable.pending_bg)
                }
                "Application Declined" -> {
                    notificationIcon.setImageResource(R.drawable.approved_bg)
                }
            }

        }

    }

    fun setData(notification: List<Notification>) {
        this.notifications = notification
        notifyDataSetChanged()
    }
}