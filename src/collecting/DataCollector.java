package collecting;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

public class DataCollector
{
    private String linkRoot;
    private WebDriver driver;
    private ArrayList<ArrayList<String>> trackList, singerList, streamList, peakList, prevList, streakList;
    public DataCollector(WebDriver driver)
    {
        linkRoot = "https://charts.spotify.com/charts/view/regional-global-daily/";
        this.driver = driver;

        trackList = new ArrayList<>();
        singerList = new ArrayList<>();
        streamList = new ArrayList<>();
        peakList = new ArrayList<>();
        prevList = new ArrayList<>();
        streakList = new ArrayList<>();
    }

    public ArrayList<ArrayList<String>> getTrackList() {return trackList;}
    public ArrayList<ArrayList<String>> getSingerList() {return singerList;}
    public ArrayList<ArrayList<String>> getStreamList() {return streamList;}
    public ArrayList<ArrayList<String>> getPeakList() {return peakList;}
    public ArrayList<ArrayList<String>> getPrevList() {return prevList;}
    public ArrayList<ArrayList<String>> getStreakList() {return streakList;}

    public void collect()
    {
        int[] days = {31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31};
        int year = 2017;

        try{Thread.sleep(1000);}
        catch (Exception e){e.printStackTrace();}

        while (year < 2023)
        {
            if (year == 2020) days[1] = 29;

            for (int i = 1; i <= 12; i++)
            {
                String month;
                if (i < 10) month = "0" + i;
                else month = Integer.toString(i);

                for (int j = 1; j <= days[i - 1]; j++)
                {
                    String day;
                    if (j < 10) day = "0" + j;
                    else day = Integer.toString(j);

                    driver.get(linkRoot + year + "-" + month + "-" + day);
                    dayCollector();
                }
            }

            if (days[1] == 29) days[1] = 28;

            year++;
        }
    }

    public void yearCollect(String year)
    {
        int[] days = {31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31};
        if (year.equals("2020")) days[1] = 29;

        try{Thread.sleep(1000);}
        catch (Exception e){e.printStackTrace();}

        for (int i = 1; i <= 12; i++)
        {
            String month;
            if (i < 10) month = "0" + i;
            else month = Integer.toString(i);

            for (int j = 1; j <= days[i - 1]; j++)
            {
                String day;
                if (j < 10) day = "0" + j;
                else day = Integer.toString(j);

                driver.get(linkRoot + year + "-" + month + "-" + day);
                dayCollector();
            }
        }
    }

    private void dayCollector()
    {
        Duration duration = Duration.ofSeconds(30);
        WebDriverWait driverWait = new WebDriverWait(driver, duration);
        driverWait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//span[@class='styled__StyledTruncatedTitle-sc-135veyd-22 kKOJRc']")));

        List<WebElement> tracks = driver.findElements(By.xpath("//span[@class='styled__StyledTruncatedTitle-sc-135veyd-22 kKOJRc']"));
        List<WebElement> singers = driver.findElements(By.xpath("//p[@class='Type__TypeElement-goli3j-0 lfGOlT']"));
        List<WebElement> streams = driver.findElements(By.xpath("//td[@class='TableCell__TableCellElement-sc-1nn7cfv-0 kJgiFu styled__RightTableCell-sc-135veyd-4 kGfYTK']"));
        List<WebElement> trios = driver.findElements(By.xpath("//td[@class='TableCell__TableCellElement-sc-1nn7cfv-0 dLdEGj styled__RightTableCell-sc-135veyd-4 kGfYTK']"));

        ArrayList<String> tempTracks = new ArrayList<>();
        ArrayList<String> tempSingers = new ArrayList<>();
        ArrayList<String> tempStreams = new ArrayList<>();
        ArrayList<String> tempPeaks = new ArrayList<>();
        ArrayList<String> tempPrevs = new ArrayList<>();
        ArrayList<String> tempStreaks = new ArrayList<>();

        for (WebElement track: tracks)
        {
            String raw = track.getText();
            raw = raw.replace(",", "");
            tempTracks.add(raw);
        }
        for (int i = 1; i < singers.size(); i++)
        {
            String raw = singers.get(i).getText();
            raw = raw.replace(",", ";");
            tempSingers.add(raw);
        }
        for (WebElement stream: streams)
        {
            String raw = stream.getText();
            raw = raw.replace(",", ".");
            tempStreams.add(raw);
        }
        for (int i = 0; i < trios.size(); i++)
        {
            if (i % 3 == 0) tempPeaks.add(trios.get(i).getText());
            else if (i % 3 == 1) tempPrevs.add(trios.get(i).getText());
            else tempStreaks.add(trios.get(i).getText());
        }

        trackList.add(tempTracks);
        singerList.add(tempSingers);
        streamList.add(tempStreams);
        peakList.add(tempPeaks);
        prevList.add(tempPrevs);
        streakList.add(tempStreaks);
    }

}
