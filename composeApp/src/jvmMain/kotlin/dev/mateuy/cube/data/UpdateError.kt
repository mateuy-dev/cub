package dev.mateuy.cube.data

sealed class CubeError(val student: String){
    class InvalidAverage(stundent: String):CubeError(stundent)
}

sealed class CheckError(student: String) : CubeError(student){
    class GradesNotMatch(student: String, grades: List<String?>, sagaGrades: List<String?>): CheckError(student)
}

sealed class UpdateError(student: String): CubeError(student){
    class CouldNotBeSet(student: String, val uf: Int): UpdateError(student)
    class GradeNotFound(student: String, val uf: Int): UpdateError(student)
    class StudentNotFoundInSaga(student: String): UpdateError(student)
    class StudentNotFoundInGrades(student: String): UpdateError(student)
}

