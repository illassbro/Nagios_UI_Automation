//nagios_ui_subtract.java
import java.util.concurrent.TimeUnit;
import java.util.List;
import java.util.Iterator;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.WebDriverWait; //WebDriverWait
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.UnhandledAlertException;
//
//CLASS
public class nagios_ui_subtract {
  public static void main(String[] args) {
    if (args.length > 0 ){
          System.out.println("hostName: "+ args[0]);
     } else{
        System.out.println("Usage: nagios_ui_subtract hostName");
        System.exit(0);
     }
    //VARS
    String URL = "http://Nagios_Server/nagiosxi/";
    String UID = "batman";
    String PASSWD = "123456";
    String hostName = args[0];
    //CHROME_DRIVER
    System.out.println("STARTING DRIVER: ");
    System.setProperty("webdriver.chrome.driver", "chromedriver");
    //SET OPTIONS
    ChromeOptions options = new ChromeOptions();
    //USE_HEADLESS_MODE
    options.addArguments("headless"); //Set Chrome Headless mode
    //DISABLE SHARED MEM
    options.addArguments("--disable-dev-shm-usage");
    //SET SCREEN SIZE
    //options.addArguments("window-size=1920x1080");
    options.addArguments("window-size=800x600");
    //options.addArguments("window-size=640x480");
    //Private
    options.addArguments("--incognito");
    //SET CAPs (Chrome Does Not Use Capabilities )
    //DesiredCapabilities capabilities = new DesiredCapabilities();
    //capabilities.setCapability("autoDismissAlerts", true);
    //capabilities.merge(options);
    //options.merge(capabilities);
    //Instantiate Web Driver
    WebDriver driver = new ChromeDriver(options);
    //RUN_TIME_START
    long start = System.currentTimeMillis();
    //SET TIMEOUT
    driver.manage().timeouts().implicitlyWait(10,TimeUnit.SECONDS);
    //URL
    System.out.println("CONNECTING TO URL");
    driver.navigate().to(URL);
    //PRINT_URL
    String urls = driver.getCurrentUrl();
    System.out.println("URL: " + urls);
    //Wait
    //WebElement DynamicElement = (new WebDriverWait(driver, 20)).until(ExpectedConditions.visibilityOfElementLocated(By.tagName("button")));
    //LOGIN
    driver.get("http://Nagios_Server/nagiosxi/login.php?redirect=/nagiosxi/index.php%3f&noauth=1");
    System.out.println("LOGIN: " + driver.getCurrentUrl());
    driver.findElement(By.id("usernameBox")).clear();
    driver.findElement(By.id("usernameBox")).sendKeys(UID);
    driver.findElement(By.id("passwordBox")).clear();
    driver.findElement(By.id("passwordBox")).sendKeys(PASSWD);
    driver.findElement(By.id("loginButton")).click();
    System.out.println("LOGIN: " + driver.getCurrentUrl());
    //GOTO CONFIGMANAGER
    driver.findElement(By.linkText("Configure")).click();
    System.out.println("CONFIG: " + driver.getCurrentUrl());
    driver.findElement(By.linkText("Core Config Manager")).click();
    System.out.println("CONFIG: " + driver.getCurrentUrl());
    driver.findElement(By.linkText("Hosts")).click();
    System.out.println("CONFIG: " + driver.getCurrentUrl());
    //SEARCH
    System.out.println("SEARCH: " + driver.getCurrentUrl());
    //DEBUGGING_FIND_OBJECT
    //ADD: WAIT FOR ELEMENT OR PAGE TO FULLY LOAD
    //DEFAULT 
    List<WebElement> elist = driver.findElements(By.xpath("//*[@type='text']"));
    for (WebElement e : elist) {
        System.out.println("value : " + e.getAttribute("id"));
    }
    List<WebElement> alist = driver.findElements(By.tagName("div"));
    for (WebElement a : alist) {
        System.out.println("value : " + a.getAttribute("id"));
    }
    //IFRAME
    List<WebElement> elements = driver.findElements(By.tagName("iframe"));
    for (WebElement iframe : elements) {
        System.out.println("IFRAME ID : " + iframe.getAttribute("id"));
        String frameName = iframe.getAttribute("id");
        driver.switchTo().frame(frameName);
            
        List<WebElement> iflist = driver.findElements(By.tagName("div"));
        for (WebElement a : iflist) {
           System.out.println(frameName + " (tagName) WebElement ID: " + a.getAttribute("id"));
        }
        List<WebElement> xflist = driver.findElements(By.xpath("//*[@type='text']"));
        for (WebElement x : xflist) {
            System.out.println(frameName + "(xpath) WebElement ID: " + x.getAttribute("id"));
                   x.clear();
                   x.sendKeys(hostName);
                   x.submit();
        }
        System.out.println("SEARCH: " + driver.getCurrentUrl());
        //REMOVE HOST
        System.out.println("REMOVE: " + driver.getCurrentUrl());
        driver.findElement(By.name("checked[]")).click();
        driver.findElement(By.xpath("//img[@alt='Delete']")).click(); //MUST DEAL WITH THE POP UP (immediately) after this click!!!
        //POPUP ()
        //System.out.println("POPUP: " + driver.getCurrentUrl());//ERROR: You an not do a nother driver action until you deal with pop up!!!
        System.out.println("POPUP: ");
        boolean done = false;
        while (!done) {
            try {
                driver.switchTo().alert().accept();
                done = true;
           } catch (UnhandledAlertException e) {
                System.out.println("try again");
           }
        }
        System.out.println("POPUP: " + driver.getCurrentUrl());
        System.out.println("REMOVE: " + driver.getCurrentUrl());
        driver.findElement(By.linkText("Apply Configuration")).click();
    }
    driver.switchTo().defaultContent();
    //LOGOUT
    System.out.println("LOGOUT: " + driver.getCurrentUrl());
    driver.findElement(By.linkText("Logout")).click();
    String logout = driver.getCurrentUrl();
    System.out.println("URL: " + logout);

    //CLEANUP
    driver.quit(); //REMOVE DRIVER FROM MEM
    //RUN_TIME_END
    long finish = System.currentTimeMillis();
    long timeElapsed = finish - start;
    System.out.println("DONE!");
    System.out.println("RUN_TIME (milliseconds): " + timeElapsed);
    System.exit(0);
   } 
}


