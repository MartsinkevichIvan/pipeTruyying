package driver;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

import java.time.Duration;

public class SingletonDriver{
    private static final int WAITING_TIME = 20;
    private static WebDriver driver;

    public static WebDriver getDriver(){
        if(driver == null){
            if(System.getProperty("browser") == null){
                System.setProperty("browser", "chrome");
            }
            switch(System.getProperty("browser")){
                case "firefox":{
                    WebDriverManager.firefoxdriver().setup();
                    driver = new FirefoxDriver();
                }
                case "explorer":{
                    WebDriverManager.edgedriver().setup();
                    driver = new EdgeDriver();
                }
                default:{
                    WebDriverManager.chromedriver().setup();
                    driver = new ChromeDriver();
                }
            }
            driver.manage().window().maximize();
            driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(WAITING_TIME));
            driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(WAITING_TIME));
        }
        return driver;
    }

    public static void closeDriver(){
        driver.quit();
        driver = null;
    }

    public static void deleteCookies(){
        driver.manage().deleteAllCookies();
    }
}
