package eu.devhuba.a7_minutes_workout

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import eu.devhuba.a7_minutes_workout.databinding.ActivityBmiBinding

class BMIActivity : AppCompatActivity() {

    //Add before onCreate method
    private lateinit var binding: ActivityBmiBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityBmiBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.tbBMI)

        if (supportActionBar != null) {
            supportActionBar?.setDisplayHomeAsUpEnabled(true)
            supportActionBar?.title = "Calculate BMI"
        }


        //Taskbar back button
        binding.tbBMI.setNavigationOnClickListener {
            onBackPressed()


        }





    }
}