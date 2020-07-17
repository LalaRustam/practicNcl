package step_definitions;



import cucumber.api.PendingException;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.Assert;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;

import java.util.List;
import java.util.concurrent.TimeUnit;

public class ShoreExcursionSteps {

    WebDriver driver;

   @Before
   public void setUp(){


       System.out.println("Setting up WebDriver in BeforeClass...");
       WebDriverManager.chromedriver().setup();
       driver = new ChromeDriver();
       driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
       driver.manage().window().maximize();


   }


    @Given("a Guest")
    public void a_Guest() {
       System.out.println("Opening WebDriver");
        driver.get("https://www.ncl.com");
    }

    @Given("I am on Homepage")
    public void i_am_on_Homepage() throws InterruptedException {
       Thread.sleep(500);
       String actual = driver.getTitle();
       String expected = "Cruises & Cruise Deals | Plan Your Cruise Vacation | NCL";
        System.out.println("user is on page: "+actual);
        Assert.assertEquals(actual,expected);
    }

    @And("^I navigated to \"([^\"]*)\" page$")
    public void iNavigatedToPage(String text) throws Throwable {
    //pop up some times displays
      try {
          Alert alert = driver.switchTo().alert();
          Thread.sleep(300);
          alert.dismiss();
      }catch (Exception e){
          System.out.println("Alert pop up");
      }

// hover over element
        Actions act = new Actions(driver);
        act.moveToElement(driver.findElement
                (By.xpath("//*[@class='linkNav'][contains(text(),'Explore')]"))).build().perform();
        Thread.sleep(500 );
        driver.findElement(By.xpath("//a[contains(text(),'"+text+"')]")).click();

    }
    @When("^I search for destination \"([^\"]*)\"$")
    public void iSearchForDestination(String text) throws Throwable {
       Thread.sleep(5000);
       driver.findElement(By.xpath("//a[@class='chosen-single']//b")).click();
       Thread.sleep(5000);
       driver.findElement(By.xpath("//li[contains(text(),'"+text+"')]")).click();
       Thread.sleep(5000);
       driver.findElement(By.xpath("//button[contains(text(),'FIND EXCURSIONS')]")).click();

    }




    @Then("Shore Excursions page is present")
    public void shore_Excursions_page_is_present() {
        String actual = driver.getTitle();
        String expected = "Alaska Cruises | Shore Excursions | Norwegian Cruise Line";
        System.out.println("user is on page: "+actual);
        Assert.assertEquals(actual,expected);
    }


    @And("^Results are filtered by \"([^\"]*)\"$")
    public void resultsAreFilteredBy(String expected) throws Throwable {
      Thread.sleep(2000);
     String auctal =  driver.findElement(By.xpath("//*[@title='Alaska Cruises']/span")).getText();
      Assert.assertEquals(auctal,expected);

    }




    @And("^Filter By Ports are only belong to \"([^\"]*)\" and \"([^\"]*)\"$")
    public void filterByPortsAreOnlyBelongToAnd(String alaska, String BColumbia) throws Throwable {
        Thread.sleep(200);
        driver.findElement(By.xpath("//a[@title='Port']")).click();

        List<WebElement> port1 = driver.findElements(By.xpath("//*[@class='option'][contains(text(),'"+alaska+"')]"));
        List<WebElement> port2 = driver.findElements(By.xpath("//*[@class='option'][contains(text(),'"+BColumbia+"')]"));
        port1.remove(0);

        for(WebElement el : port1){
            Thread.sleep(200);
            el.click();
        }

        for(WebElement el : port2){
            Thread.sleep(200);
            el.click();
        }

        driver.findElement(By.xpath("(//a[contains(text(),'Update Filter')])[2]")).click();
        Thread.sleep(2000);
        List<WebElement> allPorts = driver.findElements(By.xpath("//a[@class='items-link']"));

        for(WebElement el : allPorts){
            Assert.assertTrue(el.isEnabled());
            Assert.assertTrue(el.isDisplayed());
            System.out.println(el.getText());
        }

        }


        @After
    public void tearDown() {
       System.out.println("closing the Driver");
        driver.quit();

    }

    }

