package eu.devhuba.a7_minutes_workout

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.lifecycleScope
import eu.devhuba.a7_minutes_workout.databinding.ActivityFinishBinding
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*

class FinishActivity : AppCompatActivity() {
    private lateinit var binding: ActivityFinishBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFinishBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //Creating dao variable to use our DB
        val historyDao = (application as WorkoutApp).db.historyDao()

        //Action bar
        supportActionBar?.setDisplayShowTitleEnabled(false)

        if (supportActionBar != null) {
            supportActionBar?.setDisplayHomeAsUpEnabled(true)
        }

        //Back button
        binding.tbFinish.setNavigationOnClickListener {
            //Return to home screen
            onBackPressed()
        }

        //Finish button
        binding.btnFinish.setOnClickListener {

            //Insert date into DB
            addDateToDB(historyDao)

            //Use finish to close current activity and jump into last not closed activity
            //If you use intent here then all activity that was not finished spend memory
            finish()
        }




    }
    
    private fun addDateToDB(historyDao: HistoryDao) {

        //Get current date
        val c = Calendar.getInstance()
        val dateTime = c.time

        //Specify needed format of date
        val sdf = SimpleDateFormat("dd MMM yyyy HH:mm:ss", Locale.getDefault())

        //Format date
        val date = sdf.format(dateTime)

        //Add info to DB
        lifecycleScope.launch {
            historyDao.insert(HistoryEntity(date))
        }
    }

}