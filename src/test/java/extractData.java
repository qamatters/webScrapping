import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;

import static pageHelper.getMutualFundData.*;

public class extractData {
    public static void main(String[] args) throws IOException {
//        Document doc = Jsoup.connect("https://en.wikipedia.org/").get();
//        System.out.println(doc.title());
//        Elements newsHeadlines = doc.select("#mp-itn b a");
//        for (Element headline : newsHeadlines) {
//            System.out.println(headline.attr("title"));
//        }
        String[] tabsName = {"pricing", "holdings", "statistics", "ratings", "portfolioAnalysis", "prospectusAndReports"};
        Instant start = Instant.now();
        launchBrowser();
        getAllPerformanceTabData();
        Instant end = Instant.now();
        Duration timeElapsed = Duration.between(start, end);
        System.out.println("Total time in scrapping: " + timeElapsed.toMinutes() + "minutes");


    }
}
