package eu.devhuba.a7_minutes_workout

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import eu.devhuba.a7_minutes_workout.databinding.StatusItemBinding

data class StatusAdapter( val items: ArrayList<ExerciseModel>) :
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
        val model: ExerciseModel = items[position]
        holder.tvItem.text = model.getId().toString()

        when {
            //Style for completed items
            model.getIsCompleted() -> {
                //Change style
                holder.tvItem.background =
                    ContextCompat.getDrawable(holder.itemView.context, R.drawable.bg_status_completed)
            }
            //Style for current items
            model.getIsSelected() -> {
                //Change size
                holder.tvItem.layoutParams.height = 150
                holder.tvItem.layoutParams.width = 150
                //Change text size
                holder.tvItem.textSize = 25f
                //Change style
                holder.tvItem.background =
                    ContextCompat.getDrawable(holder.itemView.context, R.drawable.bg_status_current_item)
            }
            //Style for undone items
            else -> {
                //Change style
                holder.tvItem.background =
                    ContextCompat.getDrawable(holder.itemView.context, R.drawable.bg_status_item)
            }
        }
    }

    override fun getItemCount(): Int {
        return items.size
    }

}
