import collecting.DataCollector;
import collecting.LogIn;
import documenting.DayDocumenting;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

public class Main
{
    public static void main(String[] args)
    {
        System.setProperty("webdriver.gecko.driver", "geckodriver");
        WebDriver driver = new FirefoxDriver();

        new LogIn(driver, "sadikefe69@gmail.com", "password_here").openAccess();

        DataCollector collector = new DataCollector(driver);
        collector.yearCollect("2019");
        new DayDocumenting(collector.getTrackList(), collector.getSingerList(), collector.getStreamList(), collector.getPeakList(), collector.getPrevList(), collector.getStreakList()).yearCollect("2019");
    }
}