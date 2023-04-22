package documenting;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class DayDocumenting
{
    private ArrayList<ArrayList<String>> tracks, singers, streams, peaks, prevs, streaks;

    public DayDocumenting(ArrayList<ArrayList<String>> tracks, ArrayList<ArrayList<String>> singers, ArrayList<ArrayList<String>> streams, ArrayList<ArrayList<String>> peaks, ArrayList<ArrayList<String>> prevs, ArrayList<ArrayList<String>> streaks)
    {
        this.tracks = tracks;
        this.singers = singers;
        this.streams = streams;
        this.peaks = peaks;
        this.prevs = prevs;
        this.streaks = streaks;
    }

    public void document()
    {
        int[] days = {31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31};

        int year = 2017;
        int loc = 0;

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

                    dayWriter(day, month, Integer.toString(year), loc);

                    loc++;
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

        int loc = 0;

        for (int i = 1; i <= 12; i++)
        {
            String month;
            if (i < 10) month = "0" + i;
            else month = Integer.toString(i);

            for (int j = 1; j <= days[i - 1]; j++) {
                String day;
                if (j < 10) day = "0" + j;
                else day = Integer.toString(j);

                dayWriter(day, month, year, loc);

                loc++;
            }
        }
    }

    private void dayWriter(String day, String month, String year, int at)
    {
        File file = new File("days/" + day + "-" + month + "-" + year + ".csv");

        try
        {
            FileWriter writer = new FileWriter(file);

            writer.write("Rank,Track,Singer(s),Stream,Peak,Prev,Streak\n");

            for (int i = 0; i < tracks.get(at).size(); i++)
            {
                String line =  Integer.toString(i+1) + "," + tracks.get(at).get(i) + "," + singers.get(at).get(i) + "," + streams.get(at).get(i) + "," + peaks.get(at).get(i) + "," + prevs.get(at).get(i) + "," + streaks.get(at).get(i);
                writer.write(line);

                if (i != tracks.get(at).size() - 1) writer.write("\n");
            }

            writer.close();
        }
        catch (IOException e){e.printStackTrace();}
    }

}
