//nagios_ui_add.java
import java.util.concurrent.TimeUnit;
import java.util.List;
import java.util.Iterator;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.WebDriverWait; //WebDriverWait
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
//
//CLASS
public class nagios_ui_add {
  public static void main(String[] args) {
    if (args.length > 2 ){
          System.out.println("templateName: "+ args[0]);
          System.out.println("hostName: "+ args[1]);
          System.out.println("IPADDR: "+ args[2]);
     } else{
        System.out.println("Usage: nagios_ui_add templateName hostName IPADDR");
        System.exit(0);
     }
    //VARS
    String URL = "http://Nagios_Server/nagiosxi/";
    String UID = "batman";
    String PASSWD = "123456";
    String templateName = args[0];
    String hostName = args[1];
    String IPADDR = args[2];
    //CHROME_DRIVER
    System.out.println("STARTING DRIVER: ");
    System.setProperty("webdriver.chrome.driver", "chromedriver");
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
    //COPY_TEMPLATE
    // ERROR: Caught exception [ERROR: Unsupported command [selectFrame | index=0 | ]]
    System.out.println("COPY: " + driver.getCurrentUrl());
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
            //
        List<WebElement> iflist = driver.findElements(By.tagName("div"));
        for (WebElement a : iflist) {
            System.out.println(frameName + " (tagName) WebElement ID: " + a.getAttribute("id"));
        }
        List<WebElement> xflist = driver.findElements(By.xpath("//*[@type='text']"));
        for (WebElement x : xflist) {
            System.out.println(frameName + "(xpath) WebElement ID: " + x.getAttribute("id"));
                   x.clear();
                   x.sendKeys(templateName);
                   x.submit();
        }
        //CLICK_COPY
        driver.findElement(By.xpath("//img[@alt='Copy']")).click();
        System.out.println("COPY: " + driver.getCurrentUrl());
        //CONFIG_HOST
        System.out.println("CONFIG_HOST: " + driver.getCurrentUrl());
        driver.findElement(By.linkText(templateName+"_copy_1")).click();
        driver.findElement(By.id("tfName")).click();
        driver.findElement(By.id("tfName")).clear();
        driver.findElement(By.id("tfName")).sendKeys(hostName);
        driver.findElement(By.id("tfFriendly")).click();
        driver.findElement(By.id("tfFriendly")).clear();
        driver.findElement(By.id("tfFriendly")).sendKeys(hostName);
        driver.findElement(By.id("tfAddress")).click();
        driver.findElement(By.id("tfAddress")).clear();
        driver.findElement(By.id("tfAddress")).sendKeys(IPADDR);
        driver.findElement(By.id("chbActive")).click();
        driver.findElement(By.id("subForm1")).click();
        driver.findElement(By.linkText("Apply Configuration")).click();
        System.out.println("CONFIG_HOST: " + driver.getCurrentUrl());
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


