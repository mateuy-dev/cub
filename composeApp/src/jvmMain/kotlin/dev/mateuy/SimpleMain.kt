package dev.mateuy

import io.github.bonigarcia.wdm.WebDriverManager
import org.openqa.selenium.chrome.ChromeDriver



fun main() {
    WebDriverManager.chromedriver().setup()
    val driver = ChromeDriver()
    driver.get("https://www.google.com")
}