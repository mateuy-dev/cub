package dev.mateuy.cube.data

const val PASSED = 5
data class StudentGrades(val name: String, val grades: List<String?>){
    val averageGrades get() = grades.last()
    val partialGrades get() = grades.dropLast(1)
    val averageGradeIndex get() = grades.lastIndex
    val lastPartialGradeIndex get() = grades.lastIndex-1

    /**
     * Last grade is the average. Must only be set if all other are >= 5
     */
    fun hasValidAverageGrade(): Boolean {
        val mustHaveAverage = partialGrades.all { (it?.toIntOrNull() ?: 0) >= PASSED }
        val averageSet = averageGrades?.toIntOrNull() !=null
        return (mustHaveAverage && averageSet && averageGrades!!.toInt()>=PASSED) || (!mustHaveAverage && !averageSet)
    }
}
