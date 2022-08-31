package eu.devhuba.a7_minutes_workout

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import eu.devhuba.a7_minutes_workout.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //Into exercises
        binding.flStart.setOnClickListener {
            try{
                val intent = Intent(this, ExerciseActivity::class.java)
                startActivity(intent)
            }catch(e:Exception){
                e.printStackTrace()
            }
        }

        //Into BMI calculator
        binding.flBMI.setOnClickListener {
            try{
                val intent = Intent(this, BMIActivity::class.java)
                startActivity(intent)
            }catch(e:Exception){
                e.printStackTrace()
            }
        }


    }
}
