package pageHelper;

import org.apache.commons.lang3.StringUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.logging.LogEntries;
import org.openqa.selenium.logging.LogEntry;
import org.openqa.selenium.logging.LogType;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class getMutualFundData {

    public static WebDriver driver;
    public static final String chromeDriverPath = "webdriver.chrome.driver";

    public static void launchBrowser(String[] tabname) throws IOException {
        String chromeDriverPathForWindows = "src\\main\\resources\\chromedriver_chrome_109\\chromedriver.exe";
        System.setProperty(chromeDriverPath, chromeDriverPathForWindows);
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        int iteration = 0;
        for (String tab : tabname) {
            System.out.println(iteration + " Tab name is :" + tab);
            driver.navigate().to("https://www.mfs.com/en-us/individual-investor/product-strategies/mutual-funds.html?tabname=" + tab);
            getAllPerformanceTabData(tab);
            iteration++;
        }
    }

    public static void getNetWorkLogs() {
        //Get the network logs
        HashMap<String, String> issues = new HashMap<>();
        LogEntries logs = driver.manage().logs().get(LogType.BROWSER);
        //Iterate over the list of logs and look for error/warnings
        for (LogEntry entry : logs) {
            if (!entry.getMessage().contains("200")) {
                issues.put(entry.getLevel().getName(), entry.getMessage());
            }
        }
        System.out.println("Urls are: " + issues);
        System.out.println("total size is :" + issues.size());
    }

    public static void launchBrowserWithURL(String URL) throws InterruptedException {
        String chromeDriverPathForWindows = "src\\main\\resources\\chromedriver_chrome_110\\chromedriver.exe";
        System.setProperty(chromeDriverPath, chromeDriverPathForWindows);
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--ignore-certificate-errors");
        options.addArguments("--test-type");
        options.addArguments("test-type");
        options.addArguments("start-maximized");
        options.addArguments("--window-size=1920,1080");
        options.addArguments("--enable-precise-memory-info");
        options.addArguments("--disable-popup-blocking");
        options.addArguments("--disable-default-apps");
        options.addArguments("test-type=browser");
        options.addArguments("--incognito");
        options.addArguments("--no-sandbox");
        driver = new ChromeDriver(options);
        driver.manage().window().maximize();
        driver.navigate().to(URL);
        Thread.sleep(1000 * 5);
        System.out.println("title is :" + driver.getTitle());
    }

    public static void launchEdgeBrowserWithURL(String URL) throws InterruptedException {
        String driverPathForWindows = "src\\main\\resources\\edgeDriver\\msedgedriver.exe";
        System.setProperty("webdriver.edge.driver", driverPathForWindows);
        EdgeOptions edgeOptions = new EdgeOptions();
        edgeOptions.addArguments("user-data-dir=C:\\Users\\deepak.mathpal\\AppData\\Local\\Microsoft\\Edge\\User Data");
        edgeOptions.addArguments("profile-directory=Work");
        edgeOptions.addArguments("--start-maximized");
        driver = new EdgeDriver(edgeOptions);
        driver.manage().window().maximize();
        driver.navigate().to(URL);
        Thread.sleep(1000 * 5);
        System.out.println("title is :" + driver.getTitle());
    }


    public static ArrayList<String> getAllPerformanceTabData(String tabname) throws IOException {
        int iterationForCategory = 1;
        int iterationForFund = 1;
        ArrayList<String> fundLinks = new ArrayList<>();
        List<WebElement> allCategories = driver.findElements(By.xpath("//*[@id='tab-panel-" + tabname + "']/div[3]/table/tbody/tr[@class='category']"));

        System.out.println("----------------------------------------------------------------------------------");
        System.out.println("Total Categories are: " + allCategories.size());
        System.out.println("----------------------------------------------------------------------------------");

        for (WebElement category : allCategories) {
            System.out.println("----------------------------------------------------------------------------------");
            System.out.println(iterationForCategory + " : category name :" + category.getText());
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

    public static List<String> getActualDataFromFundPageAsAlIst(String url) throws IOException {
        String performanceTabURL = url + "#tab-performance/";

        System.out.println("----------------------------------------------------");
        System.out.println("Fund link is: " + url);
        System.out.println("----------------------------------------------------");

        Document doc = Jsoup.connect(performanceTabURL).maxBodySize(0).get();
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
        if (al.size() == 2) {
            System.out.println(al.get(1));
        }
        return al;

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
        if (al.size() == 2) {
            System.out.println(al.get(1));
        }

        System.out.println("----------------------------------------------------");

    }

    public static void getActualDataFromServices(String url) throws IOException {
        String fundName = StringUtils.substringBetween(url, "mutual-funds/", "-mfs");
        System.out.println("fund name is :" + fundName);
        String serviceURL = "https://services.mfs.com/MFSServices/products/v1/product/" + fundName + "/productdetails?productCode=MITIX&roleCode=inv,usinv&locationCode=us&locale=en_US&productLineCode=WEB_FAMILYFUNDS";

        String response = Jsoup.connect(serviceURL).timeout(1000 * 60 * 10).ignoreContentType(true).execute().body();


    }

}
