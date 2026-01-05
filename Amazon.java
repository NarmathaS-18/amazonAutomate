import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.*;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.time.Duration;
import org.apache.commons.io.FileUtils;

public class amazon {
    public static WebDriver driver;
    public static WebDriverWait wait;

    public static void main(String[] args) throws IOException {
        
        System.setProperty("webdriver.chrome.driver", "C:\\Users\\Narmatha\\OneDrive\\Documents\\chromedriver-win64\\chromedriver-win64//chromedriver.exe");
        driver = new ChromeDriver();
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        driver.manage().window().maximize();
        
        try {
           navigateToHomePage();
            searchProduct("shirt");
            clickFirstSearchResult();
            validateProductPage();
            takeScreenshot("screenshots/product_page.png");
        } finally {
		 driver.quit();
        }
    }
	public static void navigateToHomePage() {
        driver.get("https://www.amazon.com/");
       // driver.findElement(By.xpath("//button[normalize-space()='Continue shopping']")).click();
     
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("twotabsearchtextbox")));
	}

    public static void searchProduct(String product) {
        WebElement searchBox = driver.findElement(By.id("twotabsearchtextbox"));
        searchBox.sendKeys(product);
        searchBox.submit();
    }
   
    public static void clickFirstSearchResult() {
    	WebElement firstResult = wait.until( ExpectedConditions.elementToBeClickable(By.xpath("(//div[contains(@class,'product')]//a)[1]")));

        firstResult.click();
    }
    public static void validateProductPage() {
    	try {
    	wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("add-to-cart-button")));
    	} 
    	catch (Exception e) {
            // Fallback to name
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("submit.addToCart")));
        }
        System.out.println("Product page loaded successfully: Add to Cart button is visible");
    }
   
    public static void takeScreenshot(String filePath) throws IOException {
        // Create screenshots directory if it does not exist
        File screenshotDir = new File("screenshots");
        if (!screenshotDir.exists()) {
            screenshotDir.mkdirs();
        }
        TakesScreenshot ts = (TakesScreenshot) driver;
        File source = ts.getScreenshotAs(OutputType.FILE);
        File destination = new File(filePath);

        FileUtils.copyFile(source, destination);
        System.out.println("Screenshot saved at: " + destination.getAbsolutePath());
    }
}
    
