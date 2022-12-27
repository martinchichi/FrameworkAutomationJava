import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

public class exampleTestNG {
    private WebDriver driver;
    // ========================================================================
    // LOCATORS
    // ========================================================================
    By samsungGalaxyS6Link = By.linkText("Samsung galaxy s6");
    By addToCartButton = By.xpath("//a[contains(text(),'Add to cart')]");
    By homeNavLink = By.xpath("/html[1]/body[1]/nav[1]/div[1]/div[1]/ul[1]/li[1]/a[1]");
    By nokiaLumia1520Link = By.linkText("Nokia lumia 1520");
    By cartNavLink = By.id("cartur");
    By listElements = By.xpath("//table/tbody/tr/td[3]");
    By totalPrice = By.id("totalp");

    // ========================================================================
    // CONFIGURATION OF THE NAVIGATOR CHROME TO ENABLE IT
    // ========================================================================
    @BeforeClass
    public void setUp() {
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        driver.manage().window().maximize();
    }

    // ========================================================================
    // AUTOMATED TEST CASES
    // ========================================================================
    @Test
    public void testCase() throws InterruptedException {
        driver.get("https://www.demoblaze.com/");
        // Implicit wait
        // driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
        // Explicit wait
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));
        // I add the first product to the cart
        wait.until(ExpectedConditions.elementToBeClickable(samsungGalaxyS6Link));
        WebElement samsungGalaxyS6 = driver.findElement(samsungGalaxyS6Link);
        samsungGalaxyS6.click();
        wait.until(ExpectedConditions.elementToBeClickable(addToCartButton));
        WebElement addButton = driver.findElement(addToCartButton);
        addButton.click();
        acceptAlert(driver);
        // I add the second product to the cart
        driver.findElement(homeNavLink).click();
        wait.until(ExpectedConditions.elementToBeClickable(nokiaLumia1520Link));
        driver.findElement(nokiaLumia1520Link).click();
        wait.until(ExpectedConditions.elementToBeClickable(addToCartButton));
        WebElement addButton2 = driver.findElement(addToCartButton);
        addButton2.click();
        acceptAlert(driver);
        // I navigate to the cart page
        driver.findElement(cartNavLink).click();
//        try {
//            Thread.sleep(2000);
//        } catch (InterruptedException e) {
//            throw new RuntimeException(e);
//        }
        wait.until(ExpectedConditions.elementToBeClickable(totalPrice));
        List<WebElement> pricesList = driver.findElements(listElements);
        int sumTotal = sumList(pricesList);
        int priceFinal = Integer.parseInt(driver.findElement(totalPrice).getText());
        Assert.assertTrue(sumTotal == priceFinal);
    }

    // ========================================================================
    // CLOSE THE NAVIGATOR CHROME
    // ========================================================================
    @AfterClass
    public void closeNavigator() {
        driver.close();
    }
    // ========================================================================
    // METHODS
    // ========================================================================
    public int sizeList(List<WebElement> elements) {
        List<String> auxList = new ArrayList<String>();
        for (WebElement e : elements) {
            auxList.add(e.getText());
        }
        return auxList.size() + 1;
    }

    public int sumList(List<WebElement> elements) {
        int sum = 0;
        List<Integer> auxList = new ArrayList<Integer>();
        for (WebElement e : elements) {
            auxList.add(Integer.parseInt(e.getText()));
        }
        for (int i : auxList) {
            sum += i;
        }
        return sum;
    }

    public boolean isAlertPresent() {
        boolean presentFlag = false;
        try {
            Alert alert = driver.switchTo().alert();
            presentFlag = true;
        } catch (NoAlertPresentException ex) {
            ex.printStackTrace();
        }
        return presentFlag;
    }

    public void acceptAlert(WebDriver driver) throws InterruptedException {
        int i = 0;
        while(i++ < 5) {
            boolean flag = isAlertPresent();
            if(flag) {
                driver.switchTo().alert().accept();
                break;
            }
            if(i == 5) {
                throw new java.lang.Error("Alert isn't display");
            } else {
                Thread.sleep(500);
                continue;
            }
        }
    }

}
