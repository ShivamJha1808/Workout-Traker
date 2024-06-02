package com.shivamjsr18.workouttraker.model

enum class MuscleGroup()
{
    Back,
    Chest,
    Shoulder,
    Biceps,
    Triceps,
    Forearms,
    Legs,
    Core;
    companion object
    {
        fun getMuscleGroupList(): List<String>
        {
            val list = mutableListOf<String>()
            entries.forEach{
                list.add(it.name)
            }
            return list
        }
        fun getMuscleGroupByName(name:String):MuscleGroup{
            var muscleGrp = Biceps
            entries.forEach{
                if(it.name==name) muscleGrp=it
            }
            return muscleGrp
        }
    }
}