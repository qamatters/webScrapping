import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.io.IOException;
import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;

import static pageHelper.getMutualFundData.*;

public class extractData {
    public static void main(String[] args) throws IOException {
        String[] tabsName = {"performance", "pricing", "holdings", "statistics", "ratings", "portfolioAnalysis", "prospectusAndReports"};
        Instant start = Instant.now();
        launchBrowser(tabsName);
        Instant end = Instant.now();
        Duration timeElapsed = Duration.between(start, end);
        System.out.println("Total time in scrapping: " + timeElapsed.toMinutes() + " minutes");

    }
}
