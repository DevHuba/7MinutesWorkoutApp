package eu.devhuba.a7_minutes_workout

import android.app.Dialog
import android.content.Intent
import android.media.MediaPlayer
import android.net.Uri
import android.os.Bundle
import android.os.CountDownTimer
import android.speech.tts.TextToSpeech
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import eu.devhuba.a7_minutes_workout.databinding.ActivityExerciseBinding
import eu.devhuba.a7_minutes_workout.databinding.CustomDialogBackBinding
import java.util.*


class ExerciseActivity : AppCompatActivity(), TextToSpeech.OnInitListener {
    //Binding
    private lateinit var binding: ActivityExerciseBinding

    //Timer
//    10000
    private val restMillis: Long = 1000
//    30000
    private val exerciseMillis: Long = 1000
    private val countDown: Long = 1000
    private var restTimer: CountDownTimer? = null
    private var exerciseTimer: CountDownTimer? = null
    private val exerciseStartTime = 30
    private var exerciseProgress = 0
    private val restStartTime = 10
    private var restProgress = 0
    private var exerciseCounter = 0

    //Exercises
    private var exerciseList = Constants.defaultExerciseList()
    private var currentExercisePosition = -1
    private var scopeForRandomExercise = (exerciseList.indices).toMutableSet()
    private var gRandomExercise: Int? = null

    //Emoji
    private val emoWarningUnicode: Int = 0x26A0

    //Text to speech
    private var tts: TextToSpeech? = null

    //Media player
    private var playerRest: MediaPlayer? = null
    private var playerExercise: MediaPlayer? = null

    //Random
    private val listOfSongs: List<Int> = mutableListOf(
        R.raw.astronaut_in_ocean,
        R.raw.god_we_need_you_now,
        R.raw.wasted_on_u,
        R.raw.cotton_eye_joe,
        R.raw.knocking_boots,
        R.raw.rockstar,
        R.raw.uptown_funk,
        R.raw.pirates_of_caribbean,
        R.raw.ed_sheeran_shivers,
        R.raw.one_of_them_girls,
        R.raw.push_it_up,
        R.raw.running_up_that_hill
    )
    private val scopeForRandomSong = (listOfSongs.indices).toMutableSet()
    private var gRandomSong: Int? = null

    //Status
    private var statusAdapter: StatusAdapter? = null
    private var statusList = Constants.defaultStatusList()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityExerciseBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.tbExercise)

        //Hide exercise view
        binding.ivExercise.visibility = View.INVISIBLE

        //Text to speech logic
        tts = TextToSpeech(this, this)

        if (tts != null) {
            tts?.speak("success", TextToSpeech.QUEUE_FLUSH, null, null)
        } else {
            println("tts = null")
        }



        if (supportActionBar != null) {
            //Show back arrow
            supportActionBar?.setDisplayHomeAsUpEnabled(true)
            //Hide toolbar title
            supportActionBar?.setDisplayShowTitleEnabled(false)
        }

        //Taskbar back button
        binding.tbExercise.setNavigationOnClickListener {
            customDialogForBack()
        }

        //Start preparation timer
        setupRestView()

        //Set status
        setStatusRV()

    }

    //Fix of back button press issue
    @Deprecated("Deprecated in Java")
    override fun onBackPressed() {
        customDialogForBack()
    }


    private fun customDialogForBack() {
        val customDialog = Dialog(this)
        val dialogBinding = CustomDialogBackBinding.inflate(layoutInflater)
        customDialog.setContentView(dialogBinding.root)
        //Turn off cancel when you touch outside of custom dialog window
        customDialog.setCanceledOnTouchOutside(false)
        dialogBinding.btnYes.setOnClickListener{
            //Close specific activity
            this@ExerciseActivity.finish()
            customDialog.dismiss()
        }
        dialogBinding.btnNo.setOnClickListener{
            customDialog.dismiss()
        }
        customDialog.show()
    }


    //Start rest layout
    private fun setupRestView() {
        //Set up sound
        try {
            val soundRestUri = Uri.parse(
                "android.resource://eu.devhuba.a7_minutes_workout/" + R.raw.relax_melody
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
        val emoWarning = getEmoji()

        //Take random exercise
        gRandomExercise = scopeForRandomExercise.shuffled().last()
        scopeForRandomExercise.remove(gRandomExercise)

        //Next exercise text
        binding.tvNextExercise.text = getString(
            R.string.tv_next_exercise,
            emoWarning,
            exerciseList[gRandomExercise!!]
                .getName(), emoWarning
        )

        //Text speech start
        speakOut(binding.tvNextExercise.text.toString())

        //Start timer
        setRestProgressBar()

    }

    private fun setRestProgressBar() {
        binding.pbRest.progress = restProgress

        restTimer = object : CountDownTimer(restMillis, countDown) {
            override fun onTick(millisUntilFinished: Long) {
                restProgress++
                binding.pbRest.progress = restStartTime - restProgress
                binding.tvTimerRest.text = (restStartTime - restProgress).toString()
            }

            override fun onFinish() {
                restProgress = 0
                currentExercisePosition++

                //Use for status change status list
                statusList[currentExercisePosition].setIsSelected(true)
                //Tell to adapter for refresh data of items in recycler view list
                statusAdapter!!.notifyDataSetChanged()

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
        gRandomSong = scopeForRandomSong.shuffled().last()
        //Remove picked random number from mutable set of numbers
        scopeForRandomSong.remove(gRandomSong)

        //Set background for exercises
        //Uncomment line for custom background in exercise layout
        binding.clParent.setBackgroundResource(R.color.colorRest)


        try {

            val soundExerciseUri = Uri.parse(
                "android.resource://eu.devhuba.a7_minutes_workout/"
                        + listOfSongs[gRandomSong!!]
            )
            playerExercise = MediaPlayer.create(applicationContext, soundExerciseUri)
            playerExercise?.isLooping = true
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
        binding.ivExercise.setImageResource(exerciseList[gRandomExercise!!].getImage())
        binding.tvTitleExercise.text = exerciseList[gRandomExercise!!].getName()


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

        exerciseTimer = object : CountDownTimer(exerciseMillis, countDown) {
            override fun onTick(millisUntilFinished: Long) {
                exerciseProgress++
                binding.pbExercise.progress = exerciseStartTime - exerciseProgress
                binding.tvTimerExercise.text = (exerciseStartTime - exerciseProgress).toString()
            }

            override fun onFinish() {
                exerciseProgress = 0

                //Change style of status current item
                //Set statusList item to be unselected
                statusList[currentExercisePosition].setIsSelected(false)
                //Set statusList item to be completed
                statusList[currentExercisePosition].setIsCompleted(true)
                //Update recyclerView
                statusAdapter!!.notifyDataSetChanged()

                //Custom background in rest layout
                binding.clParent.setBackgroundResource(R.color.colorRest)
                //Check for last exercise
                if (exerciseCounter == statusList.size) {
                    //Go into finish activity and finish exercise activity
                    val intent = Intent(this@ExerciseActivity, FinishActivity::class.java)
                        startActivity(intent)
                        finish()

                } else {
                    //Continue challenge
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

        if (tts != null) {
            tts!!.stop()
            tts!!.shutdown()

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
            Log.e("err", "Error in status check!")
        }
    }

    private fun speakOut(text: String) {
        tts?.speak(text, TextToSpeech.QUEUE_FLUSH, null, null)

    }

    //Emoji logic
    private fun getEmoji(): String {
        return String(Character.toChars(emoWarningUnicode))
    }

    private fun setStatusRV() {
        //Manage how your items appear on screen
        binding.rvStatus.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)

        //Pass here also exerciseList if you need to change something dynamically in rv
        statusAdapter = StatusAdapter(statusList)
        binding.rvStatus.adapter = statusAdapter
    }


}