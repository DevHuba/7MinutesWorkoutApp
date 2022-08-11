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

    private val emoWarningUnicode: Int = 0x26A0


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
        binding.tvNextExercise.visibility = View.VISIBLE

        //Clean rest timer
        if (restTimer != null) {
            restTimer?.cancel()
            restProgress = 0
        }

        //Take emoji int and make it emoji char
        val emoWarning = getEmoji(emoWarningUnicode)

        //Next exercise text
        binding.tvNextExercise.text = getString(
            R.string.tv_next_exercise,
            emoWarning,
            exerciseList!![currentExercisePosition + 1]
                .getName(), emoWarning
        )

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

                binding.tvNextExercise.visibility = View.GONE

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

    //Emoji logic
    private fun getEmoji(unicode: Int): String {
        return String(Character.toChars(unicode))
    }
}

//https://apps.timwhitlock.info/emoji/tables/unicode

//Usage in code (Kotlin)
//Add 0x instead of U+

//val emoji: String = getEmoji(0x1F389)

//private fun getEmoji(unicode: Int): String {
//    return String(Character.toChars(unicode))
//}

//List of nice emojis
//U+1F4AA	\xF0\x9F\x92\xAA	flexed biceps            0x1F4AA
//U+1F525	\xF0\x9F\x94\xA5	fire                     0x1F525
//U+1F4A2	\xF0\x9F\x92\xA2	anger symbol             0x1F4A2
//U+1F47A	\xF0\x9F\x91\xBA	japanese goblin          0x1F47A
//U+1F44F	\xF0\x9F\x91\x8F	clapping hands           0x1F44F
//U+1F44E	\xF0\x9F\x91\x8E	thumbs down              0x1F44E
//U+1F44D	\xF0\x9F\x91\x8D	thumbs up                0x1F44D
//U+1F44C	\xF0\x9F\x91\x8C	ok hand                  0x1F44C
//U+1F44A	\xF0\x9F\x91\x8A	fisted hand              0x1F44A
//U+1F393	\xF0\x9F\x8E\x93	graduation cap           0x1F393
//U+1F389	\xF0\x9F\x8E\x89	party popper             0x1F389
//U+1F320	\xF0\x9F\x8C\xA0	shooting star            0x1F320
//U+2B50	\xE2\xAD\x90	white medium star            0x2B50
//U+26A0    \xE2\x9A\xA0	warning sign                 0x26A0
//U+2728	\xE2\x9C\xA8	sparkles                     0x2728
//U+270C	\xE2\x9C\x8C	victory hand                 0x270C
//U+1F603	\xF0\x9F\x98\x83	smiling face with open mouth        0x1F603
//U+1F601	\xF0\x9F\x98\x81	grinning face with smiling eyes     0x1F601