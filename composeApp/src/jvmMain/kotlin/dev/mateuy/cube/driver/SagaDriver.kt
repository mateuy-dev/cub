package dev.mateuy.cube.driver


import dev.mateuy.cube.driver.*
import io.github.bonigarcia.wdm.WebDriverManager
import org.openqa.selenium.*
import org.openqa.selenium.chrome.ChromeDriver
import org.openqa.selenium.chrome.ChromeOptions
import org.openqa.selenium.remote.RemoteWebDriver



class SagaDriver(browser : Browser = Browser.CHROME, hidden : Boolean = false) {
    val waitSecs = 3L
    val driver : RemoteWebDriver

    init {
        setupDriver(browser)
        System.setProperty("webdriver.http.factory", "jdk-http-client");
//        driver =  createDriver(browser, hidden)

        WebDriverManager.chromiumdriver().setup()

//        val options = ChromeOptions()
//        options.addArguments("start-maximized", "--no-sandbox", "--disable-dev-shm-usage", "--remote-debugging-port=9222")//--enable-automation", "--window-size=1920,1080", "--log-level=3", "--disable-logging", "--remote-allow-origins=*")
        driver = ChromeDriver()//options)
        //driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(waitSecs));
        driver.get("https://bfgh.aplicacions.ensenyament.gencat.cat/bfgh/avaluacio/finalAvaluacioGrupMateria/#/");
    }

    fun close(){
        driver.close()
    }


    fun studentRows(): GradeTable {
        val tableWebElement = driver.findElements(By.cssSelector(".table"))[1]
        return GradeTable.create(tableWebElement)
    }

//    fun login(){
//        driver.findElement(By.id("user")).sendKeys(username)
//        driver.findElement(By.id("password")).sendKeys(password)
//        Actions(driver).moveToElement(driver.findElement(By.id("password"))).perform()
//        driver.findElement(By.cssSelector("input[type=\"submit\"]")).click()
//    }
}