package eu.devhuba.a7_minutes_workout

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import eu.devhuba.a7_minutes_workout.databinding.ActivityFinishBinding
import eu.devhuba.a7_minutes_workout.databinding.ActivityMainBinding

class FinishActivity : AppCompatActivity() {
    private lateinit var binding: ActivityFinishBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFinishBinding.inflate(layoutInflater)
        setContentView(binding.root)

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
            //Use finish to close current activity and jump into last not closed activity
            //If you use intent here then all activity that was not finished spend memory
            finish()
        }

    }

}