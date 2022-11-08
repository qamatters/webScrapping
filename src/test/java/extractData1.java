import consoleColors.ConsoleColors;
import org.apache.commons.lang3.StringUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.time.Duration;
import java.time.Instant;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;

public class extractData1 {
    public static void main(String[] args) throws IOException {
        String[] funds = {"BRWJX-mfs-blended-research-growth-equity-fund", "MUSEX-mfs-blended-research-core-equity-fund",
                "BMSLX-mfs-blended-research-mid-cap-equity-fund", "BRSJX-mfs-blended-research-small-cap-equity-fund"};

        LinkedHashMap<String, String> Hmap = new LinkedHashMap<>();
        Document doc ;
        Instant startTime = Instant.now();
        int counter =1;
        for (String fund : funds) {
            String url = "https://www.mfs.com/en-us/individual-investor/product-strategies/mutual-funds/" + fund + ".html#tab-performance/";
            System.out.println("----------------------------------------------------------------------------------------------------------------------------");
            System.out.println(counter + " fund url is :" + url);
            System.out.println("----------------------------------------------------------------------------------------------------------------------------");
            Instant startTimeForHittingURL = Instant.now();
            try {
                doc = Jsoup.connect(url).ignoreContentType(true).get();
            } catch (Exception e) {
                doc = Jsoup.connect(url).get();
            }
            Instant endTimeForGettingResponse = Instant.now();
            Duration timeElapsedForGettingJSOUPResponse = Duration.between(startTimeForHittingURL, endTimeForGettingResponse);
            System.out.println("Total time spent fetching data from JSOUP for " + counter + " fund " + fund + " is : " + ConsoleColors.GREEN_BOLD + timeElapsedForGettingJSOUPResponse.toSeconds() + " seconds"+ConsoleColors.RESET);
            Elements productDetailPerformanceTab = doc.getElementsByClass("product-detail__performance-tab js-performance-tab js-xx-collapse-handle");
            String alldata = String.valueOf(productDetailPerformanceTab.get(0));
            String value = StringUtils.substringBetween(alldata, "data-graphdata", "\">");
            String[] allArrays = StringUtils.substringsBetween(value, "data&quot;:", "}");
            List al = Arrays.asList(allArrays);
//            System.out.println(" all data :" + al);
            if (al.isEmpty()) {
                System.out.println("------------------There is no data ----------------------------");
                Hmap.put(url, "NA");
            } else {
                System.out.println(ConsoleColors.BLUE_BOLD + "----------------Quarterly Data--------------------------------------"+ConsoleColors.RESET);
                for (int i = 0; i < al.size() / 2; i++) {
                    System.out.println(al.get(i));
                    Hmap.put(url, (String) al.get(i));
                }
                System.out.println(ConsoleColors.PURPLE_BOLD + "----------------Monthly Data-----------------------------------------"+ConsoleColors.RESET);
                for (int i = al.size() / 2; i < al.size(); i++) {
                    System.out.println(al.get(i));
                    Hmap.put(url, (String) al.get(i));
                }
            }
            counter = counter+1;
        }

        Instant endTimeFoTotalExecution = Instant.now();
        Duration totalTimeElapsed = Duration.between(startTime, endTimeFoTotalExecution);


        System.out.println("------------------------------------------------------------------------------------------------------");
        System.out.println(ConsoleColors.GREEN + "Total time spent fetching all data for all funds from UI is: " + totalTimeElapsed.toSeconds() + " seconds "+ ConsoleColors.RESET);
        System.out.println("------------------------------------------------------------------------------------------------------");
//        Hmap.forEach((k, v) -> System.out.println(k +" :" +  v));
    }
}
