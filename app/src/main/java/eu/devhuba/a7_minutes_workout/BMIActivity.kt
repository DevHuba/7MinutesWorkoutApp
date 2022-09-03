package eu.devhuba.a7_minutes_workout

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import eu.devhuba.a7_minutes_workout.databinding.ActivityBmiBinding

class BMIActivity : AppCompatActivity() {

    private lateinit var binding: ActivityBmiBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityBmiBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.tbBMI)

        if (supportActionBar != null) {
            //Enable back arrow in toolBar
            supportActionBar?.setDisplayHomeAsUpEnabled(true)
            //Hide toolbar title
            supportActionBar?.setDisplayShowTitleEnabled(false);
        }


        //Taskbar back button
        binding.tbBMI.setNavigationOnClickListener {
            onBackPressed()
            finish()
        }


    }
}