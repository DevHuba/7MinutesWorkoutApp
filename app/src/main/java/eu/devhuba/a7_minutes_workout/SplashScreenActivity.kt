package eu.devhuba.a7_minutes_workout

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import eu.devhuba.a7_minutes_workout.databinding.ActivitySplashScreenBinding

@SuppressLint("CustomSplashScreen")
class SplashScreenActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySplashScreenBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashScreenBinding.inflate(layoutInflater)
        setContentView(binding.root)


//        Uncomment for enable splashscreen and home screen

//        val splashScreen = binding.ssBackground
//        splashScreen.alpha = 0f
//        splashScreen.animate().setDuration(1500).alpha(1f).withEndAction {
//                try{
//                    val i = Intent(this, MainActivity::class.java)
//            startActivity(i)
//            finish()
//                }catch(e:Exception){
//                    e.printStackTrace()
//                }

//
//        }

        //Also comment that for test splashscreen
        val i = Intent(this, MainActivity::class.java)
        startActivity(i)
        finish()

    }
}






        
       