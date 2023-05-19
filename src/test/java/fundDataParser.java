import com.google.common.cache.AbstractCache;
import com.jayway.jsonpath.JsonPath;
import net.minidev.json.JSONArray;
import org.apache.commons.lang3.StringUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;
import java.sql.SQLOutput;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;

public class fundDataParser {
    public static void main(String[] args) throws IOException {
//        String url = "https://www.mfs.com/en-us/individual-investor/product-strategies/mutual-funds.html?tabname=performance";
        String url = "https://www.mfs.com/en-us/individual-investor/product-strategies/variable-insurance-portfolios.html?tabname=performance";
        Document doc;
        try {
            doc = Jsoup.connect(url).ignoreContentType(true).get();
        } catch (Exception e) {
            doc = Jsoup.connect(url).get();
        }
        String wholeData = String.valueOf(doc);
        String firstParse = StringUtils.substringBetween(wholeData, "productListingRow", "<!-- End of PLP JSON -->");
        String secondParse = StringUtils.substringBetween(firstParse, "data-json=\"", ",&quot;json&quot;:null}\"></div>");
        String json = secondParse.replaceAll("&quot;", "\"");
        String completeJson = json+ "}";
        JSONArray fundSymbols =JsonPath.read(completeJson, "$.productRows.categories[*].products[*].urlParameter");
        HashMap<String, LinkedList<String>> monthlyFundData = new LinkedHashMap<>();
        HashMap<String, LinkedList<String>> quarterlyFundData = new LinkedHashMap<>();
        for(Object fundSymbol : fundSymbols) {
            LinkedList<String> monthlyFundValues  = new LinkedList<>();
            LinkedList<String> quarterlyFundValues  = new LinkedList<>();
            String symbol = fundSymbol.toString();
            String symbolWithdoubleQuote = "\"" + symbol + "\"";

            String ytdPercentMonthly = JsonPath.read(completeJson, "$.productRows.categories[*].products[?(@.urlParameter=="+symbolWithdoubleQuote+")].performance.ytdPercentMonthly.withoutScValue").toString();
            String oneYearReturnMonthly = JsonPath.read(completeJson, "$.productRows.categories[*].products[?(@.urlParameter=="+symbolWithdoubleQuote+")].performance.oneYearReturnMonthly.withoutScValue").toString();
            String threeYearReturnMonthly = JsonPath.read(completeJson, "$.productRows.categories[*].products[?(@.urlParameter=="+symbolWithdoubleQuote+")].performance.threeYearReturnMonthly.withoutScValue").toString();
            String fiveYearReturnMonthly = JsonPath.read(completeJson, "$.productRows.categories[*].products[?(@.urlParameter=="+symbolWithdoubleQuote+")].performance.fiveYearReturnMonthly.withoutScValue").toString();
            String tenYearReturnMonthly = JsonPath.read(completeJson, "$.productRows.categories[*].products[?(@.urlParameter=="+symbolWithdoubleQuote+")].performance.tenYearReturnMonthly.withoutScValue").toString();
            String lifeReturnMonthly = JsonPath.read(completeJson, "$.productRows.categories[*].products[?(@.urlParameter=="+symbolWithdoubleQuote+")].performance.lifeReturnMonthly.withoutScValue").toString();

            monthlyFundValues.add(lifeReturnMonthly);
            monthlyFundValues.add(tenYearReturnMonthly);
            monthlyFundValues.add(fiveYearReturnMonthly);
            monthlyFundValues.add(threeYearReturnMonthly);
            monthlyFundValues.add(oneYearReturnMonthly);
            monthlyFundValues.add(ytdPercentMonthly);
            monthlyFundData.put(symbol,monthlyFundValues);

            String ytdPercentQuarterly = JsonPath.read(completeJson, "$.productRows.categories[*].products[?(@.urlParameter=="+symbolWithdoubleQuote+")].performance.ytdPercentQuarterly.withoutScValue").toString();
            String oneYearReturnQuarterly = JsonPath.read(completeJson, "$.productRows.categories[*].products[?(@.urlParameter=="+symbolWithdoubleQuote+")].performance.oneYearReturnQuarterly.withoutScValue").toString();
            String threeYearReturnQuarterly = JsonPath.read(completeJson, "$.productRows.categories[*].products[?(@.urlParameter=="+symbolWithdoubleQuote+")].performance.threeYearReturnQuarterly.withoutScValue").toString();
            String fiveYearReturnQuarterly = JsonPath.read(completeJson, "$.productRows.categories[*].products[?(@.urlParameter=="+symbolWithdoubleQuote+")].performance.fiveYearReturnQuarterly.withoutScValue").toString();
            String tenYearReturnQuarterly = JsonPath.read(completeJson, "$.productRows.categories[*].products[?(@.urlParameter=="+symbolWithdoubleQuote+")].performance.tenYearReturnQuarterly.withoutScValue").toString();
            String lifeReturnQuarterly = JsonPath.read(completeJson, "$.productRows.categories[*].products[?(@.urlParameter=="+symbolWithdoubleQuote+")].performance.lifeReturnQuarterly.withoutScValue").toString();

            quarterlyFundValues.add(lifeReturnQuarterly);
            quarterlyFundValues.add(tenYearReturnQuarterly);
            quarterlyFundValues.add(fiveYearReturnQuarterly);
            quarterlyFundValues.add(threeYearReturnQuarterly);
            quarterlyFundValues.add(oneYearReturnQuarterly);
            quarterlyFundValues.add(ytdPercentQuarterly);
            quarterlyFundData.put(symbol,quarterlyFundValues);

        }
        System.out.println("Monthly Fund Values");
        monthlyFundData.forEach((key, value) -> System.out.println("Symbol: "+ key + " values:"+ value));
        System.out.println("quarterly Fund Values");
        quarterlyFundData.forEach((key, value) -> System.out.println("Symbol: "+ key + " values:"+ value));
    }
}
