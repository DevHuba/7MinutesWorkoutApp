package eu.devhuba.a7_minutes_workout

import android.media.MediaPlayer
import android.net.Uri
import android.os.Bundle
import android.os.CountDownTimer
import android.speech.tts.TextToSpeech
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import eu.devhuba.a7_minutes_workout.databinding.ActivityExerciseBinding
import java.util.*


class ExerciseActivity : AppCompatActivity(), TextToSpeech.OnInitListener {
    //Binding
    private lateinit var binding: ActivityExerciseBinding

    //Timer
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

    //Exercise
    private var exerciseList: ArrayList<ExerciseModel>? = null
    private var currentExercisePosition = -1

    //Emoji
    private val emoWarningUnicode: Int = 0x26A0

    //Text to speech
    private var tts: TextToSpeech? = null

    private var playerRest: MediaPlayer? = null
    private var playerExercise: MediaPlayer? = null

    private val listOfSongs: List<Int> = mutableListOf(
        R.raw.astronaut_in_ocean,
        R.raw.gods_country,
        R.raw.stranger_things,
        R.raw.god_we_need_you_now,
        R.raw.wasted_on_u,
        R.raw.welcome_to_my_house
    )

    private val scopeForRandomSong = (listOfSongs.indices).toMutableSet()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityExerciseBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.tbExercise)

        binding.ivExercise.visibility = View.INVISIBLE

        tts = TextToSpeech(this, this)

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

        //Set up sound
        try {
            val soundRestUri = Uri.parse(
                "android.resource://eu.devhuba.a7_minutes_workout/" + R.raw.be_happy
            )
            playerRest = MediaPlayer.create(applicationContext, soundRestUri)
            playerRest?.isLooping = false
            playerRest?.start()

        } catch (e: Exception) {
            e.printStackTrace()
        }

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

        //Text speech start
        speakOut(binding.tvNextExercise.text.toString())

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
                //Stop player before start exercise random song
                playerRest?.stop()

                //Activate exercise layout
                setupExerciseView()


            }
        }.start()
    }

    //Start exercise layout
    private fun setupExerciseView() {

        //Take random number from mutable set
        val randomSong = scopeForRandomSong.random()
        //Remove picked random number from mutable set of numbers
        scopeForRandomSong.remove(randomSong)
        println(scopeForRandomSong)


        try {

            val soundExerciseUri = Uri.parse(
                "android.resource://eu.devhuba.a7_minutes_workout/"
                        + listOfSongs[randomSong]
            )
            playerExercise = MediaPlayer.create(applicationContext, soundExerciseUri)
            playerExercise?.isLooping = false
            playerExercise?.start()
        } catch (e: Exception) {
            e.printStackTrace()
        }

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
                    Toast.makeText(this@ExerciseActivity, "WELL DONE ! YOU FINISHED WORKOUT !", Toast.LENGTH_SHORT)
                        .show()

                } else {
                    setupRestView()

                }
                playerExercise?.stop()

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

        if (playerRest != null) {
            playerRest!!.stop()

        }
        if (playerExercise != null) {
            playerExercise!!.stop()

        }
    }

    //Text to speech
    override fun onInit(status: Int) {
        if (status == TextToSpeech.SUCCESS) {
            val result = tts!!.setLanguage(Locale.getDefault())

            if (result == TextToSpeech.LANG_MISSING_DATA || result == TextToSpeech.LANG_NOT_SUPPORTED) {
                Toast.makeText(this@ExerciseActivity, "Your language is not supported!", Toast.LENGTH_SHORT).show()
            }

        } else {
            Log.e("er", "Error in status check!")
        }
    }

    private fun speakOut(text: String) {
        tts?.speak(text, TextToSpeech.QUEUE_FLUSH, null, null)

    }

    //Emoji logic
    private fun getEmoji(unicode: Int): String {
        return String(Character.toChars(unicode))
    }


}