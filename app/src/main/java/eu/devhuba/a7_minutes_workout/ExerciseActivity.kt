package eu.devhuba.a7_minutes_workout

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.view.View
import android.widget.Toast
import eu.devhuba.a7_minutes_workout.databinding.ActivityExerciseBinding


class ExerciseActivity : AppCompatActivity() {

    private lateinit var binding: ActivityExerciseBinding

    private val restMillis: Long = 10000
    private val exerciseMillis: Long = 30000
    private val countDown: Long = 1000

    private var restTimer: CountDownTimer? = null
    private var exerciseTimer: CountDownTimer? = null
    private val exerciseStartTime = 5
    private var exerciseProgress = 0
    private val restStartTime = 5
    private var restProgress = 0
    private var exerciseCounter = 0

    private var exerciseList: ArrayList<ExerciseModel>? = null
    private var currentExercisePosition = -1


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityExerciseBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.tbExercise)

        binding.ivExercise.visibility = View.INVISIBLE


        //Hide toolbar title
        supportActionBar?.setDisplayShowTitleEnabled(false)

        if (supportActionBar != null) {
            supportActionBar?.setDisplayHomeAsUpEnabled(true)
        }

        exerciseList = Constants.defaultExerciseList()

        //Back button logic
        binding.tbExercise.setNavigationOnClickListener {
            onBackPressed()
        }

        //Start preparation timer
        setupRestView()
    }

    //Start rest layout
    private fun setupRestView() {
        //Show only rest frame layout and title
        binding.flProgressbarRest.visibility = View.VISIBLE
        binding.flProgressbarExercise.visibility = View.GONE
        binding.tvTitleRest.visibility = View.VISIBLE
        binding.tvTitleExercise.visibility = View.GONE
        binding.ivExercise.visibility = View.INVISIBLE

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

        restTimer = object : CountDownTimer(3000, countDown) {
            override fun onTick(millisUntilFinished: Long) {
                restProgress++
                binding.pbRest.progress = restStartTime - restProgress
                binding.tvTimerRest.text = (restStartTime - restProgress).toString()
            }

            override fun onFinish() {
                restProgress = 0
                currentExercisePosition++

                //Activate exercise layout
                setupExerciseView()

            }
        }.start()
    }

    //Start exercise layout
    private fun setupExerciseView() {
        //Show only exercise frame layout and title
        binding.flProgressbarRest.visibility = View.GONE
        binding.flProgressbarExercise.visibility = View.VISIBLE
        binding.tvTitleRest.visibility = View.GONE
        binding.tvTitleExercise.visibility = View.VISIBLE
        binding.ivExercise.visibility = View.VISIBLE

        //Set up specific exercise
        binding.ivExercise.setImageResource(exerciseList!![currentExercisePosition].getImage())
        binding.tvTitleExercise.text = exerciseList!![currentExercisePosition].getName()

        //Next exercise text
        binding.tvNextExercise.text =  getString(R.string.tv_next_exercise,exerciseList!![currentExercisePosition+1].getName())

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

        exerciseTimer = object : CountDownTimer(3000, countDown) {
            override fun onTick(millisUntilFinished: Long) {
                exerciseProgress++
                binding.pbExercise.progress = exerciseStartTime - exerciseProgress
                binding.tvTimerExercise.text = (exerciseStartTime - exerciseProgress).toString()
            }

            override fun onFinish() {
                exerciseProgress = 0

                if (exerciseCounter == 11) {

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