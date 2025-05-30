package dev.mateuy.cube

import java.text.Normalizer

private val REGEX_UNACCENT = "\\p{InCombiningDiacriticalMarks}+".toRegex()

fun CharSequence.unaccent(): String {
    val temp = Normalizer.normalize(this, Normalizer.Form.NFD)
    return REGEX_UNACCENT.replace(temp, "")
}

fun String.simpleChars() = lowercase().unaccent()

fun String?.nullIfEmpty() : String? {
    if(this==null) return null
    if(trim().isEmpty()) return null
    return this
}

