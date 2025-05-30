package dev.mateuy.cube.driver

import io.github.bonigarcia.wdm.WebDriverManager
import org.openqa.selenium.WebElement
import org.openqa.selenium.chrome.ChromeDriver
import org.openqa.selenium.chrome.ChromeOptions
import org.openqa.selenium.firefox.FirefoxDriver
import org.openqa.selenium.firefox.FirefoxOptions
import org.openqa.selenium.remote.RemoteWebDriver
import org.openqa.selenium.safari.SafariDriver


enum class Browser{CHROME, FIREFOX, SAFARI}

fun setupDriver(browser: Browser = Browser.CHROME) {
    return when(browser){
        Browser.CHROME -> WebDriverManager.chromedriver().setup()
        Browser.FIREFOX -> WebDriverManager.firefoxdriver().setup()
        Browser.SAFARI -> WebDriverManager.safaridriver().setup()
    }
}

fun createDriver(browser: Browser = Browser.CHROME, hidden: Boolean = true) : RemoteWebDriver = when(browser){
        Browser.CHROME -> { //ChromeDriver()
            System.setProperty("webdriver.http.factory", "jdk-http-client");
            WebDriverManager.chromedriver().setup()

            if(hidden) {
                val options = ChromeOptions()
                options.addArguments("--headless", "--window-size=1920,1080", "--disable-gpu", "--remote-allow-origins=*")
                ChromeDriver(options)
            } else {
                val options = ChromeOptions()
                options.addArguments("--window-size=1920,1080", "--no-sandbox", "--disable-dev-shm-usage", "--remote-debugging-port=9222")//"--log-level=3", "--disable-logging", "--remote-allow-origins=*")
                ChromeDriver(options)
            }
        }
        Browser.FIREFOX -> {
            val options = FirefoxOptions()



            FirefoxDriver(options)
            //FirefoxDriver()
        }
        Browser.SAFARI -> SafariDriver()
    }
//        OS.MAC -> ChromeDriver()
//        OS.LINUX -> {
//  ChromeDriver()
//            val profile = FirefoxProfile()
//            profile.setPreference("browser.helperApps.neverAsk.openFile", "application/octet-stream")
////            val profileIni = ProfilesIni()
////            val profile = profileIni.getProfile("default")
//            val options = FirefoxOptions()
//            options.profile = profile
//            FirefoxDriver(options)
            //FirefoxDriver()

//        else -> throw Exception("${getOS()}")
//}

fun sendTextWithAds(webElement: WebElement, text: String){
    webElement.sendKeys(text)
//    when(getOS()) {
//        OS.MAC -> {
//            val arroba = Keys.chord(Keys.ALT, "2")
//            val parts = text.split("@")
//            webElement.sendKeys(parts.first())
//
//            for (part in parts.drop(1)) {
//                webElement.sendKeys(arroba)
//                webElement.sendKeys(Keys.BACK_SPACE);
//                webElement.sendKeys(part)
//            }
//        }
//        else -> webElement.sendKeys(text)
//    }
}
