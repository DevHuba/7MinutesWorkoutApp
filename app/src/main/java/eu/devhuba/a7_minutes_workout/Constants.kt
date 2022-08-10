package eu.devhuba.a7_minutes_workout

import java.util.*
import kotlin.collections.ArrayList

object Constants {

    fun defaultExerciseList(): ArrayList<ExerciseModel> {
        val exerciseList = ArrayList<ExerciseModel>()

        val exerciseOne = ExerciseModel(1, "jumping jacks", R.drawable.ic_jumping_jacks)
        val exerciseTwo = ExerciseModel(2, "abdominal crunch", R.drawable.ic_abdominal_crunch)
        val exerciseThree = ExerciseModel(3, "high knees running", R.drawable.ic_high_knees_running_in_place)
        val exerciseFour = ExerciseModel(4, "lunge", R.drawable.ic_lunge)
        val exerciseFive = ExerciseModel(5, "plank", R.drawable.ic_plank)
        val exerciseSix = ExerciseModel(6, "push up", R.drawable.ic_push_up)
        val exerciseSeven = ExerciseModel(7, "push up and rotate", R.drawable.ic_push_up_and_rotation)
        val exerciseEight = ExerciseModel(8, "side plank", R.drawable.ic_side_plank)
        val exerciseNine = ExerciseModel(9, "squat", R.drawable.ic_squat)
        val exerciseTen = ExerciseModel(10, "step up", R.drawable.ic_step_up_onto_chair)
        val exerciseEleven = ExerciseModel(11, "triceps", R.drawable.ic_triceps_dip_on_chair)
        val exerciseTwelve = ExerciseModel(12, "wall sit", R.drawable.ic_wall_sit)

        Collections.addAll(exerciseList,exerciseOne,exerciseTwo,exerciseThree,exerciseFour,exerciseFive,exerciseSix,
            exerciseSeven,
            exerciseEight,exerciseNine,exerciseTen,exerciseEleven,exerciseTwelve)

        return exerciseList
    }

}