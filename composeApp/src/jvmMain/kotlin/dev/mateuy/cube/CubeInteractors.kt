package dev.mateuy.cube

import dev.mateuy.cube.data.CheckError
import dev.mateuy.cube.data.CubeError
import dev.mateuy.cube.data.StudentGrades
import dev.mateuy.cube.data.UpdateError
import dev.mateuy.cube.driver.SagaDriver

class CubeInteractors(val driver : SagaDriver = SagaDriver()) {
    var students = listOf<String>()

    fun updateStundentList(students : List<String>){
        this.students = students
    }

    fun applyGrades(rawGrades: List<List<String?>>, validateAverage: Boolean) : List<CubeError> {
        val gradeTable = readSagaTable()
        val studentGrades = mixGradesWithStudents(rawGrades)
        val averageErrors = if(validateAverage) validateAverageGrades(studentGrades.values) else listOf()
        return averageErrors + gradeTable.apply(studentGrades)
    }

    fun validateGrades(rawGrades: List<List<String>>, checkAverages : Boolean): List<CubeError> {
        val gradeTable = readSagaTable().extractGrades()
        val studentGrades = mixGradesWithStudents(rawGrades)

        return validateAverageGrades(studentGrades.values) + validateGrades(gradeTable, studentGrades, checkAverages)
    }

    private fun validateGrades(sagaGradesTable: List<StudentGrades>, grades: MutableMap<String, StudentGrades>, checkAverages: Boolean): List<CubeError> {
        val errors = mutableListOf<CubeError>()
        for(sagaGrades in sagaGradesTable){
            val givenGrades = grades[sagaGrades.name.simpleChars()]
            if(givenGrades==null){
                errors += UpdateError.StudentNotFoundInGrades(sagaGrades.name)
            } else {
                val givenToCompare = givenGrades.grades
                val extractedToCompare = if(checkAverages) sagaGrades.grades else sagaGrades.partialGrades
                if(givenToCompare!=extractedToCompare){
                    errors += CheckError.GradesNotMatch(sagaGrades.name, givenToCompare, extractedToCompare)
                }
            }
        }
        return errors
    }

    fun mixGradesWithStudents(rawGrades: List<List<String?>>): MutableMap<String, StudentGrades> {
        return students
            .mapIndexed { i, name -> StudentGrades(name, rawGrades[i]) }
            .filter { it.name.isNotEmpty() }
            .associateBy { it.name.simpleChars() }.toMutableMap()
    }

    fun validateStudentNames(): List<UpdateError> {
        val sagaStudentNames = readSagaTable().studentRows.map{it.name}
        val onlyInSaga = sagaStudentNames - students
        val onlyInGrades = students - sagaStudentNames
        return onlyInGrades.map { UpdateError.StudentNotFoundInSaga(it) } + onlyInSaga.map{ UpdateError.StudentNotFoundInGrades(it)}
    }

    fun readSagaTable() = driver.studentRows()

    private fun validateAverageGrades(cubeGrades: Collection<StudentGrades>) =
        cubeGrades.filter { !it.hasValidAverageGrade() }.map { CubeError.InvalidAverage(it.name) }

}