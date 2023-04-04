package eu.devhuba.a7_minutes_workout

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import eu.devhuba.a7_minutes_workout.databinding.ActivityHistoryBinding
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class HistoryActivity : AppCompatActivity() {

    //Add before onCreate method
    private lateinit var binding: ActivityHistoryBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHistoryBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.tbHistory)

        //Creating dao variable to use our DB
        val historyDao = (application as WorkoutApp).db.historyDao()

        if (supportActionBar != null) {
            //Enable back arrow in toolBar
            supportActionBar?.setDisplayHomeAsUpEnabled(true)
            //Hide toolbar title
            supportActionBar?.setDisplayShowTitleEnabled(false)
        }

        binding.tbHistory.setNavigationOnClickListener {
            onBackPressed()
            finish()
        }

        binding.btnShowHistory.setOnClickListener {
            //Use DB
            getAllCompletedDates(historyDao)
        }


    }

    private fun getAllCompletedDates(historyDao: HistoryDao) {
        lifecycleScope.launch {
            historyDao.fetchAllDates().collect { allCompletedDateList ->
                if (allCompletedDateList.isNotEmpty()) {
                    binding.rvHistory.visibility = View.VISIBLE
                    binding.tvNoData.visibility = View.GONE

                    binding.rvHistory.layoutManager = LinearLayoutManager(this@HistoryActivity)

                    val dates = ArrayList<String>()
                    for (date in allCompletedDateList) {
                        dates.add(date.date)
                    }

                    val historyAdapter = ItemAdapter(dates)
                    binding.rvHistory.adapter = historyAdapter

                } else {
                    binding.tvNoData.visibility = View.VISIBLE
                }
            }
        }
    }

}