import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.Select;

public class SeleniumConfig {
    public static void main(String[] args) throws InterruptedException {
        //Chrome driver
        System.setProperty("webdriver.chrome.driver","C:\\Users\\Karl\\Downloads\\chromedriver.exe");
        WebDriver driver = new ChromeDriver();
        //külasta LHV liisingu lehekülge
        driver.get("https://www.lhv.ee/liising");

        // täismõõdus brauseriaken
        driver.manage().window().maximize();

        //oota kuni leht avaneb
        Thread.sleep(2000);

        //accept cookies
        driver.findElement(By.id("acceptPirukas")).click();

        //teeme kindlaks, et oleme Liisingu vahelehel
        String title = driver.getTitle();
        System.out.println(title);

        // Soovin liisingut "juriidilise isikuna" - (eraisik on by default juba valitud)
        driver.findElement(By.xpath("//*[contains(text(), 'juriidilise isikuna')]")).click();

        // Liisingu tüüp "kasutusrent" - (kapitalirent on by default juba valitud)
        driver.findElement(By.xpath("//*[contains(text(), 'kasutusrent')]")).click();

        //Sõiduki uue hinna määramine
        WebElement autohind = driver.findElement(By.id("price"));
        autohind.clear();
        autohind.sendKeys("1000");

        // "Hind sisaldab käibemaksu" linnukese kastist eemaldamine (by default on linnuke olemas)
        driver.findElement(By.xpath("//*[contains(text(), 'Hind sisaldab')]")).click();

        //"Sissemakse %" textfieldi muutmine
        WebElement sissemaks = driver.findElement(By.id("initial_percentage"));
        sissemaks.clear();
        sissemaks.sendKeys("15");

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

        //Maksegraafiku avamine
        driver.findElement(By.cssSelector(".arrow.payment-graph-link")).click();

        // oota 5 sek
        Thread.sleep(5000);

        //sulgeb kogu testrakenduse
        driver.quit();
    }
}
