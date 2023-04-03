package Thread;

import consoleColors.ConsoleColors;

import java.io.IOException;
import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;


import static Thread.withThread.getURLFromExcel;
import static pageHelper.getMutualFundData.getActualDataFromFundPageAsAlIst;

public class withoutThread {

    public static void main(String[] args) throws IOException {
        ArrayList<String> urls = getURLFromExcel();
        Instant start = Instant.now();
       for(String url: urls) {
           getActualDataFromFundPageAsAlIst(url);
       }
        Instant end = Instant.now();
        Duration timeElapsed = Duration.between(start, end);

        System.out.println("------------------------------------------------------------------------------------------");
        System.out.println("Total time in scrapping without thread concept is : : " + ConsoleColors.RED_BOLD   + timeElapsed.toSeconds() + " seconds" + ConsoleColors.RESET);
        System.out.println("------------------------------------------------------------------------------------------");
    }

}
