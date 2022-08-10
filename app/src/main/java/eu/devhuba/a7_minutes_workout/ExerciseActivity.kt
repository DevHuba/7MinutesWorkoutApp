package eu.devhuba.a7_minutes_workout

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.view.View
import android.widget.Toast
import eu.devhuba.a7_minutes_workout.databinding.ActivityExerciseBinding
import kotlin.math.log


class ExerciseActivity : AppCompatActivity() {

    private lateinit var binding: ActivityExerciseBinding

    private var restTimer: CountDownTimer? = null
    private var exerciseTimer: CountDownTimer? = null
    private val exerciseStartTime = 10
    private var exerciseProgress = 0
    private val restStartTime = 5
    private var restProgress = 0
    private var exerciseCounter = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityExerciseBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.tbExercise)

        //Hide toolbar title
        supportActionBar?.setDisplayShowTitleEnabled(false)

        if (supportActionBar != null) {
            supportActionBar?.setDisplayHomeAsUpEnabled(true)
        }

        //Back button logic
        binding.tbExercise.setNavigationOnClickListener {
            onBackPressed()
        }

        //Start preparation timer
        setupRestView()


    }

    private fun setupRestView() {
        //Show only rest frame layout and title
        binding.flProgressbarRest.visibility = View.VISIBLE
        binding.flProgressbarExercise.visibility = View.GONE
        binding.tvTitleRest.visibility = View.VISIBLE
        binding.tvTitleExercise.visibility = View.GONE

        //Clean rest timer
        if (restTimer != null) {
            restTimer?.cancel()
            restProgress = 0
        }
        //Start timer
        setRestProgressBar()
    }

    private fun setRestProgressBar() {
        binding.pbRest.progress = restProgress

        restTimer = object : CountDownTimer(5000, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                restProgress++
                binding.pbRest.progress = restStartTime - restProgress
                binding.tvTimerRest.text = (restStartTime - restProgress).toString()
            }

            override fun onFinish() {
                restProgress = 0

                //Activate exercise layout
                setupExerciseView()

            }
        }.start()

    }

    private fun setupExerciseView() {
        //Show only exercise frame layout and title
        binding.flProgressbarRest.visibility = View.GONE
        binding.flProgressbarExercise.visibility = View.VISIBLE
        binding.tvTitleRest.visibility = View.GONE
        binding.tvTitleExercise.visibility = View.VISIBLE

        //Clean exercise timer
        if (exerciseTimer != null) {
            exerciseTimer?.cancel()
            exerciseProgress = 0
        }

        //Start timer
        setExerciseProgressBar()
    }

    private fun setExerciseProgressBar() {
        exerciseCounter++
        binding.pbExercise.progress = exerciseProgress

        exerciseTimer = object : CountDownTimer(10000, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                exerciseProgress++
                binding.pbExercise.progress = exerciseStartTime - exerciseProgress
                binding.tvTimerExercise.text = (exerciseStartTime - exerciseProgress).toString()
            }

            override fun onFinish() {
                exerciseProgress = 0
                println(exerciseCounter)

                if (exerciseCounter == 2) {

                    // TODO: Add here DONE ACTIVITY. 
                    Toast.makeText(this@ExerciseActivity, "WELL DONE ! YOU FINISHED WORKOUT !", Toast.LENGTH_SHORT)
                        .show()

                } else {
                    setupRestView()

                }
            }
        }.start()

    }

    override fun onDestroy() {
        super.onDestroy()

        if (restTimer != null) {
            restTimer?.cancel()
            exerciseTimer?.cancel()
            restProgress = 0
            exerciseProgress = 0
        }

    }

}