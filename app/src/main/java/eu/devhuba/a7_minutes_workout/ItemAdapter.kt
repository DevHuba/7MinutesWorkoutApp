package eu.devhuba.a7_minutes_workout

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import eu.devhuba.a7_minutes_workout.databinding.ItemHistoryRowBinding

class ItemAdapter(private val items: ArrayList<HistoryEntity>) : RecyclerView.Adapter<ItemAdapter.ViewHolder>() {

    class ViewHolder(binding: ItemHistoryRowBinding) : RecyclerView.ViewHolder(binding.root) {
        val llDateItemMain = binding.llHistoryItem
        val tvPosition = binding.tvPosition
        val tvDate = binding.tvItem
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(ItemHistoryRowBinding.inflate(
            LayoutInflater.from(parent.context),parent, false
        ))
    }


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val date : HistoryEntity = items[position]
        holder.tvPosition.text = (position + 1).toString()
        holder.tvDate.text = date.toString()

    }

    override fun getItemCount(): Int {
        return items.size
    }

}