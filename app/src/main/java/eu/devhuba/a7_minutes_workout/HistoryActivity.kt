package eu.devhuba.a7_minutes_workout

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import eu.devhuba.a7_minutes_workout.databinding.ActivityHistoryBinding

class HistoryActivity : AppCompatActivity() {

    //Add before onCreate method
    private lateinit var binding: ActivityHistoryBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHistoryBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.tbHistory)

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



    }
}