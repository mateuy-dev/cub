package dev.mateuy.cube.ui

import dev.mateuy.cube.data.CheckError
import dev.mateuy.cube.data.CubeError
import dev.mateuy.cube.data.UpdateError


//class SaSagaCli(val driver : SagaDriver = SagaDriver(), val scanner : Scanner = Scanner(System.`in`)) {
//    lateinit var studentNames : List<String>
//    fun start() {
//        //driver.login()
//        studentNames = readStudentNames()
//        while(true){
//            val text = "> Ves a la pàgina amb la taula que vulguis processar i " +
//                    "indica si vols (1) verificar, (2) omplir o (-1) sortir"
//            println(text)
//            val option = scanner.nextIntDiscardLine()
//            println("Processant taula (tarda uns segons)...")
//            val gradeTable = driver.studentRows()
//            println("Taula processada")
//            when(option){
//                1-> validateGrades(gradeTable)
//                2 -> applyGrades(gradeTable)
//                else -> break
//            }
//        }
//    }
//
//    private fun applyGrades(gradeTable: GradeTable) {
//        val grades = readStudentNamesAndGrades()
//        if(validateAverageGrades(grades.values)){
//            println("El procés s'ha aturat pels errors")
//            return
//        }
//        val errors = gradeTable.apply(grades)
//        if(errors.isNotEmpty())
//            printErrors(errors)
//    }
//
//    private fun validateAverageGrades(extractedGrades: Collection<StudentGrades>) : Boolean{
//        val errors = extractedGrades.filter { !it.hasValidAverageGrade() }
//        if(errors.isNotEmpty()) {
//            println("S'han trobat notes de modul incorrectes als següents alumnes:")
//            errors.forEach { println("- ${it.name}") }
//        }
//        return errors.isNotEmpty()
//    }
//
//    private fun validateGrades(gradeTable: GradeTable) {
//        val extractedGradesTable = gradeTable.extractGrades()
//        if(validateAverageGrades(extractedGradesTable)){
//            println("El procés s'ha aturat pels errors")
//            return
//        }
//
//        println("> També vols introduir les notesd finals de mòdul per comparar-les? (y/n)")
//        val checkAverages = scanner.nextLine()=="y"
//        val grades = readStudentNamesAndGrades()
//
//        validateGrades(extractedGradesTable, grades, checkAverages)
//    }
//
//    private fun validateGrades(extractedGradesTable: List<StudentGrades>, grades: MutableMap<String, StudentGrades>,
//                               checkAverages: Boolean) {
//        println("Resultat comparativa")
//        var errorfound = false
//        for(extractedGrades in extractedGradesTable){
//            val givenGrades = grades[extractedGrades.name.simpleChars()]
//            if(givenGrades==null){
//                errorfound = true
//                println("- Alumne al SAGA del que no has introduit les notes al programa: ${extractedGrades.name}")
//            } else {
//                val givenToCompare = givenGrades.grades
//                val extractedToCompare = if(checkAverages) extractedGrades.grades else extractedGrades.partialGrades
//                if(givenToCompare!=extractedToCompare){
//                    errorfound = true
//                    println("- Les notes no coincideixen: ${extractedGrades.name} - ${givenToCompare} - ${extractedToCompare}")
//                }
//            }
//        }
//        if(!errorfound)
//            println("- Les notes al SAGA són correctes")
//    }
//
//
//    fun asCvs(extractedGrades: List<StudentGrades>) :String {
//        return extractedGrades.joinToString("\n") {
//            "\"${it.name}\", ${it.grades.joinToString("\",\"", "\"", "\"")}"
//        }
//    }
//
//    fun printErrors(errors: MutableList<UpdateError>) {
//        println("##### Possibles errors #####")
//        println("S'han trobat els següents problemes per introduir les notes al saga:")
//        errors.forEach { error ->
//            val text = when(error){
//                is UpdateError.CouldNotBeSet -> "- La nota no es pot posar al SAGA la següent nota: ${error.student} - UF${error.uf}"
//                is UpdateError.GradeNotFound -> "- No has introduit la següent nota: ${error.student} UF${error.uf}"
//                is UpdateError.StudentNotFoundInGrades -> "- No has introduït l'estudiant: ${error.student}"
//                is UpdateError.StudentNotFoundInSaga -> "- Estudiant no s'ha trobat a l'Esfera: ${error.student}"
//            }
//            println(text)
//        }
//        println()
//
//    }
//
//    fun readStudentNamesAndGrades(): MutableMap<String, StudentGrades> {
//        val processedMarks = readStudentGrades(studentNames.size)
//        return studentNames
//            .mapIndexed { i, name -> StudentGrades(name, processedMarks[i]) }
//            .filter { it.name.isNotEmpty() }
//            .associateBy { it.name.simpleChars() }.toMutableMap()
//    }
//
//    private fun readStudentNames(): List<String> {
//        println("> Introdueix els estudiants. Per finalitzar introdueix la linia END")
//        val studentNames = mutableListOf<String>()
//        while(true){
//            val name = scanner.nextLine()
//            if(name == "END") break
//            studentNames+=name
//        }
//        return studentNames
//    }
//
//    fun readStudentGrades(size: Int): List<List<String?>> {
//        println("> Introdueix les notes dels estudiants")
//        val studentMarks = List(size){ scanner.nextLine() }
//        return studentMarks.map { it.split("\t").map { it.nullIfEmpty() } }
//    }
//
//    fun Scanner.nextIntDiscardLine() : Int {
//        val value = nextInt()
//        nextLine()
//        return value
//    }
//}
//
//
fun CubeError.asMessage() = when(this){
    is UpdateError.CouldNotBeSet -> "- La nota no es pot posar a l'Esfera la següent nota: ${this.student} - UF${this.uf}"
    is UpdateError.GradeNotFound -> "- No has introduit la següent nota: ${this.student} UF${this.uf}"
    is UpdateError.StudentNotFoundInGrades -> "- No has introduït l'estudiant: ${this.student}"
    is UpdateError.StudentNotFoundInSaga -> "- Estudiant no s'ha trobat a l'Esfera: ${this.student}"
    is CheckError.GradesNotMatch -> "- Les notes del Saga i les introduïdes no concorden per l'estudiant ${this.student}"
    is CubeError.InvalidAverage -> "- La mitjana del mòdul és incorrecte per l'estudiant ${this.student}"
}

fun CubeError.asMessageWithoutName() = when(this){
    is UpdateError.CouldNotBeSet -> "- La nota no es pot posar a l'Esfera: UF${this.uf}"
    is UpdateError.GradeNotFound -> "- No has introduit la següent nota: UF${this.uf}"
    is UpdateError.StudentNotFoundInGrades -> "- No has introduït l'estudiant"
    is UpdateError.StudentNotFoundInSaga -> "- Estudiant no s'ha trobat a l'Esfera"
    is CheckError.GradesNotMatch -> "- Les notes del Saga i les introduïdes no concorden"
    is CubeError.InvalidAverage -> "- La mitjana del mòdul és incorrecte"
}

