package eu.devhuba.a7_minutes_workout

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import eu.devhuba.a7_minutes_workout.databinding.StatusItemBinding

class StatusAdapter( private val statusPositions: ArrayList<StatusModel>) :
    RecyclerView.Adapter<StatusAdapter.ViewHolder>() {

    class ViewHolder(binding: StatusItemBinding) : RecyclerView.ViewHolder(binding.root) {
        val tvItem = binding.tvItem
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        //Use .inflate() when you need to use binding outside of activity
        //Current binding logic
        return ViewHolder(StatusItemBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val statusPosition = statusPositions[position]
        holder.tvItem.text = statusPosition.getId().toString()
//        holder.tvItem.text = model.getId().toString()

        //Set size of items
        holder.tvItem.layoutParams.height = 75
        holder.tvItem.layoutParams.width = 75
        holder.tvItem.textSize = 15f

        when {
            //Style for completed items
            statusPosition.getIsCompleted() -> {

                //Change style
                holder.tvItem.background =
                    ContextCompat.getDrawable(holder.tvItem.context, R.drawable.bg_status_completed)
            }
            //Style for current items
            statusPosition.getIsSelected() -> {
                //Change size
                holder.tvItem.layoutParams.height = 150
                holder.tvItem.layoutParams.width = 150
                //Change text size
                holder.tvItem.textSize = 25f
                //Change style
                holder.tvItem.background =
                    ContextCompat.getDrawable(holder.itemView.context, R.drawable.bg_status_current_item)
            }
        }

    }

    override fun getItemCount(): Int {
        return statusPositions.size
    }

}
