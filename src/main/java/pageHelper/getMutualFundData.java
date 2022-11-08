package pageHelper;

import org.apache.commons.lang3.StringUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class getMutualFundData {

    public static WebDriver driver;
    public static final String chromeDriverPath = "webdriver.chrome.driver";

    public static void launchBrowser() {
        String chromeDriverPathForWindows = "src\\main\\resources\\chromedriver_chrome_107\\chromedriver.exe";
        System.setProperty(chromeDriverPath, chromeDriverPathForWindows);
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.navigate().to("https://www.mfs.com/en-us/individual-investor/product-strategies/mutual-funds.html?tabname=performance");
    }



    public static ArrayList<String> getAllPerformanceTabData() throws IOException {
        int iterationForCategory= 1;
        int iterationForFund =1;
        ArrayList<String> fundLinks = new ArrayList<>();
        List<WebElement> allCategories = driver.findElements(By.xpath("//*[@id='tab-panel-performance']/div[3]/table/tbody/tr[@class='category']"));

        System.out.println("----------------------------------------------------------------------------------");
        System.out.println("Total Categories are: " + allCategories.size());
        System.out.println("----------------------------------------------------------------------------------");

        for (WebElement category : allCategories) {
            System.out.println("----------------------------------------------------------------------------------");
            System.out.println(iterationForCategory+ " : category name :" + category.getText());
            System.out.println("----------------------------------------------------------------------------------");
            List<WebElement> allFundNames = category.findElements(By.xpath("following-sibling::tr/td[@class='productName']"));

            System.out.println("----------------------------------------------------------------------------------");
            System.out.println("Total fund names are: " + allFundNames.size());
            System.out.println("----------------------------------------------------------------------------------");

            for (WebElement fundName : allFundNames) {
                System.out.println(iterationForFund + " : fund name:" + fundName.getText());
                String fundLink = fundName.findElement(By.tagName("a")).getAttribute("href");
                fundLinks.add(fundLink);
                getActualDataFromFundPage(fundLink);
                iterationForFund++;
            }
            iterationForCategory++;
        }
        return fundLinks;
    }

    public static void getActualDataFromFundPage(String url) throws IOException {
        System.out.println("----------------------------------------------------");
        System.out.println("Fund link is: " + url);
        System.out.println("----------------------------------------------------");
        String performanceTabURL = url + "#tab-performance/";
        Document doc = Jsoup.connect(performanceTabURL).get();
        Elements productDetailPerformanceTab = doc.getElementsByClass("product-detail__performance-tab js-performance-tab js-xx-collapse-handle");
        String alldata = String.valueOf(productDetailPerformanceTab.get(0));
        String value = StringUtils.substringBetween(alldata, "data-graphdata", "\">");
        String[] allArrays = StringUtils.substringsBetween(value, "data&quot;:", "}");
        List<String> al = Arrays.asList(allArrays);
        System.out.println("----------------------------------------------------");
        if (al.size() >= 3) {
            System.out.println(al.get(2));
        }
        if (al.size() >= 4) {
            System.out.println(al.get(3));
        }
        if(al.size() ==2) {
            System.out.println(al.get(1));
        }

        System.out.println("----------------------------------------------------");

    }

}
