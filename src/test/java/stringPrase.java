import consoleColors.ConsoleColors;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;
import java.util.Arrays;

public class stringPrase {
    public static void main(String[] args) throws IOException {
        String url = "https://www.mfs.com/en-us/individual-investor/product-strategies/mutual-funds.html?tabname=performance";
        Document doc;
        try {
            doc = Jsoup.connect(url).ignoreContentType(true).get();
        } catch (Exception e) {
            doc = Jsoup.connect(url).get();
        }
        String wholeData = String.valueOf(doc);
//        System.out.println("data from page: " + wholeData);

        String firstParse = wholeData.substring(wholeData.indexOf("fundNumber"), wholeData.lastIndexOf("stocks"));
        String regex = "/\\{&#34;|null|&quot;|Research\\&amp;lt;sup\\&amp;gt;&amp;reg;&amp;lt;&amp;#x2F;sup\\&amp;gt;/g";
        wholeData = firstParse.replaceAll(regex, "");
       String[] allSymbol = wholeData.split(",");

        for (int i =0; i< allSymbol.length; i++) {
            if (allSymbol[i].contains("urlParameter")) {
                System.out.println("------------------------------------------------------------------------------------------");
                System.out.println("symbol name is:" + allSymbol[i].substring(allSymbol[i].indexOf(":") + 1));
            }
            if (allSymbol[i].contains("ytdReturnQuarterly:{value:")) {
                System.out.println("Quarterly YTD year data is :" + allSymbol[i].substring(allSymbol[i].lastIndexOf(":") + 1));
                System.out.println("------------------------------------------------------------------------------------------");
            }
            if (allSymbol[i].contains("oneYearReturnQuarterly:{value:")) {
                System.out.println("Quarterly 1 year data is :" + allSymbol[i].substring(allSymbol[i].lastIndexOf(":") + 1));
            }
            if (allSymbol[i].contains("threeYearReturnQuarterly:{value:")) {
                System.out.println("Quarterly 3 year data is :" + allSymbol[i].substring(allSymbol[i].lastIndexOf(":") + 1));
            }
            if (allSymbol[i].contains("fiveYearReturnQuarterly:{value:")) {
                System.out.println("Quarterly 5 year data is :" + allSymbol[i].substring(allSymbol[i].lastIndexOf(":") + 1));
            }
            if (allSymbol[i].contains("tenYearReturnQuarterly:{value:")) {
                System.out.println("Quarterly 10  year data is :" + allSymbol[i].substring(allSymbol[i].lastIndexOf(":") + 1));
            }
            if (allSymbol[i].contains("lifeReturnQuarterly:{value:")) {
                System.out.println("Quarterly Life year data is :" + allSymbol[i].substring(allSymbol[i].lastIndexOf(":") + 1));
            }

            if (allSymbol[i].contains("lifeReturnMonthly:{value:")) {
                System.out.println("Monthly Life year data is :" + allSymbol[i].substring(allSymbol[i].lastIndexOf(":") + 1));
            }
            if (allSymbol[i].contains("tenYearReturnMonthly:{value:")) {
                System.out.println("Monthly 10  year data is :" + allSymbol[i].substring(allSymbol[i].lastIndexOf(":") + 1));
            }
            if (allSymbol[i].contains("fiveYearReturnMonthly:{value:")) {
                System.out.println("Monthly 5 year data is :" + allSymbol[i].substring(allSymbol[i].lastIndexOf(":") + 1));
            }
            if (allSymbol[i].contains("threeYearReturnMonthly:{value:")) {
                System.out.println("Monthly 3 year data is :" + allSymbol[i].substring(allSymbol[i].lastIndexOf(":") + 1));
            }
            if (allSymbol[i].contains("oneYearReturnMonthly:{value:")) {
                System.out.println("Monthly 1 year data is :" + allSymbol[i].substring(allSymbol[i].lastIndexOf(":") + 1));
            }
            if (allSymbol[i].contains("{ytdPercentMonthly:{value:")) {
                System.out.println("Monthly YTD year data is :" + allSymbol[i + 1].substring(allSymbol[i + 1].lastIndexOf(":") + 1));
            }

            if (allSymbol[i].contains("portfolioCommencement:[")) {
                String year = allSymbol[i].substring(allSymbol[i].lastIndexOf("[") + 3);
                String month = allSymbol[i + 1];
                String date = allSymbol[i + 2].replace("]", "");
                System.out.println("Inception year :" + month + "/" + date + "/" + year);
            }
//            System.out.println(allSymbol);
        }
    }
}
