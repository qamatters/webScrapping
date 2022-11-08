import org.apache.commons.lang3.StringUtils;
import org.jsoup.Jsoup;
import org.jsoup.internal.StringUtil;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class extractData1 {
    public static void main(String[] args) throws IOException {
        String[] funds = {"BRWJX-mfs-blended-research-growth-equity-fund", "MUSEX-mfs-blended-research-core-equity-fund",
        "BMSLX-mfs-blended-research-mid-cap-equity-fund", "BRSJX-mfs-blended-research-small-cap-equity-fund",
        "BRUHX-mfs-blended-research-value-equity-fund", "MRGRX-mfs-core-equity-fund",
        "EQNIX-mfs-equity-income-fund", "MFEIX-mfs-growth-fund", "UIVIX-mfs-intrinsic-value-fund"};


        for (String fund : funds) {
            String url = "https://www.mfs.com/en-us/individual-investor/product-strategies/mutual-funds/" + fund + "html#tab-performance/";
            System.out.println("url :" + url);
            Document doc = Jsoup.connect(url).get();
            Elements productDetailPerformanceTab = doc.getElementsByClass("product-detail__performance-tab js-performance-tab js-xx-collapse-handle");
            String alldata = String.valueOf(productDetailPerformanceTab.get(0));
            String value = StringUtils.substringBetween(alldata, "data-graphdata", "\">");
            String[] allArrays = StringUtils.substringsBetween(value, "data&quot;:", "}");
            List al = Arrays.asList(allArrays);
            System.out.println(al.get(2));
            System.out.println(al.get(3));
        }

    }
}
