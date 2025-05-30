package dev.mateuy.cube.driver


import dev.mateuy.cube.data.StudentGrades
import dev.mateuy.cube.data.UpdateError

/**
 * Stores name and inputs for
 */
data class StudentRow(val name:String, val markSelectors: MarkSelector){
    fun isFixed() = markSelectors is MarkSelector.Fixed
    fun applyGrade(grade: Int) : Boolean{
        val selector = markSelectors
        if(selector is MarkSelector.Selector) {
            selector.setValue(grade)
            return true
        } else {
            return false
        }
    }
    fun applyGrades(studentGrades: StudentGrades, ra: Int): MutableList<UpdateError> {
        val errors = mutableListOf<UpdateError>()
        val grade = studentGrades.grades[ra]
            if(grade!=null) {
                val gradeSet = applyGrade(grade.toInt())
                if(!gradeSet)
                    errors+= UpdateError.CouldNotBeSet(name, ra+1)
            } else {
                if(!isFixed()){
                    errors+= UpdateError.GradeNotFound(name, ra+1)
                }
            }

        return errors
    }
}