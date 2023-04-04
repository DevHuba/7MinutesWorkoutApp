package eu.devhuba.a7_minutes_workout

import android.app.Application

class WorkoutApp: Application() {

    val db by lazy {
        HistoryDatabase.getInstance(this)
    }

}