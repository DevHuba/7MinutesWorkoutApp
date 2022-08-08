package eu.devhuba.a7_minutes_workout

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import eu.devhuba.a7_minutes_workout.databinding.ActivityExerciseBinding


class ExerciseActivity : AppCompatActivity() {

    private lateinit var binding: ActivityExerciseBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityExerciseBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.tbExercise)
        
        if (supportActionBar != null) {
            supportActionBar?.setDisplayHomeAsUpEnabled(true)
        } 

        //Back button logic
        binding.tbExercise.setNavigationOnClickListener {
            onBackPressed()
        }

    }
}