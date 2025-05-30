package dev.mateuy.cube.driver

import dev.mateuy.cube.data.StudentGrades
import dev.mateuy.cube.data.UpdateError
import dev.mateuy.cube.simpleChars
import org.openqa.selenium.By
import org.openqa.selenium.WebElement

data class GradeTable(val studentRows : List<StudentRow>) {
    companion object{
        fun create(tableWebElement : WebElement): GradeTable {
            val webElementMatrix = tableWebElement.findElements(By.cssSelector("tbody tr")).map {
                it.findElements(By.tagName("td"))
            }

            val studentRows = webElementMatrix.map {
                val student1stSurname = it[1].text
                val student2ndSurname = it[2].text
                val studentSimpleName = it[3].text
                val studentId= it[4].text
                val markSelector = MarkSelector.create(it[5])
                val studentFullName = "$student1stSurname $student2ndSurname, $studentSimpleName"

                //val selectors = it.filterIndexed { i, _ -> i%2==0 && i>1 }.dropLast(1).map { MarkSelector.create(it) }
                StudentRow(studentFullName, markSelector)
            }
            return GradeTable(studentRows)
        }
    }


    fun apply(grades: MutableMap<String, StudentGrades>): MutableList<UpdateError> {
        val errors = mutableListOf<UpdateError>()
        for(studentRow in studentRows){
            val studentGrades = grades.remove(studentRow.name.simpleChars())
            if(studentGrades==null){
                errors+= UpdateError.StudentNotFoundInGrades(studentRow.name)
                continue
            }
            errors += studentRow.applyGrades(studentGrades, 0)

        }
        grades.keys.forEach{ errors+= UpdateError.StudentNotFoundInSaga(it)}

        return errors
    }

    fun extractGrades() : List<StudentGrades> {
        TODO()
//        return studentRows.map{ studentRow->
//            val grades = studentRow.markSelectors.map{it.value.nullIfEmpty()}
//            StudentGrades(studentRow.name, grades)
//        }
    }

}