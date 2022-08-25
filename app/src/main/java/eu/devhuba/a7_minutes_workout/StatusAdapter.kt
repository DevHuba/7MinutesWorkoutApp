package eu.devhuba.a7_minutes_workout

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import eu.devhuba.a7_minutes_workout.databinding.StatusItemBinding

class StatusAdapter(val items: ArrayList<ExerciseModel>):
    RecyclerView.Adapter<StatusAdapter.ViewHolder>(){
    class ViewHolder(binding: StatusItemBinding):RecyclerView.ViewHolder(binding.root) {
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
    }

    override fun getItemCount(): Int {
        return items.size
    }

}
