import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;
import java.util.List;
import java.util.concurrent.TimeUnit;
import org.openqa.selenium.JavascriptExecutor;
import static org.testng.AssertJUnit.assertEquals;

public class CalculatorTest {
    public static void main(String[] args) throws InterruptedException {

        System.setProperty("webdriver.chrome.driver", "/Users/Karl/Downloads/chromedriver.exe");
        WebDriver driver = new ChromeDriver();
        driver.get("https://www.lhv.ee/et/liising#kalkulaator");
        driver.manage().window().maximize();

        //accept cookies
        driver.findElement(By.id("acceptPirukas")).click();

        //teeme kindlaks, et oleme Liisingu vahelehel
        String actualTitle = driver.getTitle();
        String expectedTitle = "Liising · LHV";
        assertEquals(expectedTitle,actualTitle);

        //Kerime kalkukaatori akna juurde
        String linkText = "Näidiskuumakse";
        driver.findElement(By.linkText(linkText));
        JavascriptExecutor jsExecutor = (JavascriptExecutor) driver;
        String script = "arguments[0].scrollIntoView(true);";
        jsExecutor.executeScript(script, driver.findElement(By.linkText(linkText)));

        //Sõiduki uue hinna määramine
        WebElement autohind = driver.findElement(By.id("price"));
        autohind.clear();
        autohind.sendKeys("20000");

        //"Sissemakse %" textfieldi muutmine
        WebElement sissemaks = driver.findElement(By.id("initial_percentage"));
        sissemaks.clear();
        sissemaks.sendKeys("15");
        // Sissemakse eurodes kontroll - (sõiduki hind * sissemakse %) / 100 = sissemakse €
        WebElement paymentEuros = driver.findElement(By.id("initial"));
        String presentedNumberTxt = paymentEuros.getAttribute("value");
        int presentedNumberInt = Integer.parseInt(presentedNumberTxt);
        int expectedNumber = 3000;
        if (presentedNumberInt == expectedNumber){
            System.out.println("sissemaks eurodes on arvutatud korrektselt: " + presentedNumberInt + "€" );
        } else {
            System.out.println("sissemaks on vale, õige arv €: " + expectedNumber + ", kuvatud arv €: " + presentedNumberTxt );
        }

        //liisingu periood - valime liisinguperioodiks 2 aastat ja 6 kuud
        Select liisingAasta = new Select(driver.findElement(By.name("years")));
        liisingAasta.selectByValue("24");
        Select liisingKuu = new Select(driver.findElement(By.name("months")));
        liisingKuu.selectByValue("6");

        // Intressi % muutmine
        WebElement intress = driver.findElement(By.id("interest_rate"));
        intress.clear();
        intress.sendKeys("8");

        // jääkväärtuse % muutmine
        WebElement jaakvaartus = driver.findElement(By.id("reminder_percentage"));
        jaakvaartus.clear();
        jaakvaartus.sendKeys("20");

        //jääkväärtuse summa kontroll
        WebElement reminder = driver.findElement(By.id("reminder"));
        String presentedReminder = reminder.getAttribute("value");
        int presentedReminderInt = Integer.parseInt(presentedReminder);
        int expectedReminder = 4000;
        if (presentedReminderInt == expectedReminder){
            System.out.println("Jääkväärtuse summa eurodes on: " + presentedReminderInt);
        } else {
            System.out.println("Vigane jääkväärtuse summa");
        }

        TimeUnit.SECONDS.sleep(2);

        //Maksegraafiku avamine
        WebElement maksegraafik = driver.findElement(By.cssSelector("a.arrow.payment-graph-link.margin-top-20"));
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("arguments[0].click();", maksegraafik);
        //Kontrollime, kas maksegraafik avaneb
        WebElement maksegraafikOpen = driver.findElement(By.className("text-center"));
        if (maksegraafikOpen.isDisplayed()) {
            System.out.println("Oled maksegraafiku lehel");
        } else {
            System.out.println("Maksegraafiku lehte ei õnnestunud avada");
        }

        //table rows
        List<WebElement> links = driver.findElements(By.cssSelector("tr"));
        System.out.println(links.size());

        //Kogu testrakenduse sulgemine
        driver.quit();
    }
}
