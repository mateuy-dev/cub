package dev.mateuy.cube.driver

import org.openqa.selenium.By
import org.openqa.selenium.WebElement
import org.openqa.selenium.support.ui.Select

/**
 * Stores selenium elment for a grade td (select or fixed)
 */
sealed class MarkSelector(val webElement: WebElement){
    companion object{
        fun create(webElement: WebElement): MarkSelector {
            if(webElement.findElements(By.tagName("select")).isNotEmpty())
                return Selector(webElement)
            else if(webElement.findElements(By.cssSelector("input[type=text]")).isNotEmpty())
                return UnmodifiableInput(webElement)
            else
                return Fixed(webElement)

        }
    }
    val value get() : String = rawValue.replace(Regex("[A-Z]\\)"), "").trim()
    abstract val rawValue : String

    class UnmodifiableInput(webElement: WebElement): MarkSelector(webElement){
        val inputElement = webElement.findElement(By.cssSelector("input[type=text]"))
        override val rawValue: String get() = inputElement.getAttribute("value")!!
    }

    class Selector(webElement: WebElement): MarkSelector(webElement){
        private val selector = Select(webElement.findElement(By.tagName("select")))
        override val rawValue: String get() = selector.firstSelectedOption.text
        fun setValue(mark: Int){
            val value = if(mark>=5){
                "string:A$mark"
            } else {
                "string:NA"
            }
            selector.selectByValue(value)
        }
    }
    class Fixed(webElement: WebElement): MarkSelector(webElement){
        override val rawValue: String get() = webElement.text

    }
}