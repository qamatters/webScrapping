package Thread;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

import static pageHelper.getMutualFundData.getActualDataFromFundPageAsAlIst;


public class improvePerformance extends Thread {

    public void run() {
    }

    public static void main(String[] args) throws IOException {
        String[] urls = {"https://www.mfs.com/en-us/individual-investor/product-strategies/mutual-funds/MUSEX-mfs-blended-research-core-equity-fund.html",
                        "https://www.mfs.com/en-us/individual-investor/product-strategies/mutual-funds/BRWJX-mfs-blended-research-growth-equity-fund.html"};

        String[] UI = {"[8.0,11.58,8.26,7.8,-16.06,2.52], [null,12.56,9.42,7.66,-18.11,null], [8.0,11.58,8.26,7.8,-16.06,-16.06], [null,12.56,9.42,7.66,-18.11,-18.11]",
                "[10.38,null,8.34,5.81,-29.76,3.25], [null,null,10.96,7.79,-29.14,null], [10.38,null,8.34,5.81,-29.76,-29.76], [null,null,10.96,7.79,-29.14,-29.14]"};

        String[] services = {"[8.0,11.58,8.26,7.8,-16.06,2.52], [null,12.56,9.42,7.66,-18.11,null], [8.0,11.58,8.26,7.8,-16.06,-16.06], [null,12.56,9.42,7.66,-18.11,-18.11]",
         "[10.38,null,8.34,5.81,-29.76,3.25], [null,null,10.96,7.79,-29.14,null], [10.38,null,8.34,5.81,-29.76,-29.76], [null,null,10.96,7.79,-29.14,-29.14]"};
        String[] excel = {"[8.0,11.58,8.26,7.8,-16.06,2.52], [null,12.56,9.42,7.66,-18.11,null], [8.0,11.58,8.26,7.8,-16.06,-16.06], [null,12.56,9.42,7.66,-18.11,-18.11]",
                "[10.38,null,8.34,5.81,-29.76,3.25], [null,null,10.96,7.79,-29.14,null], [10.38,null,8.34,5.81,-29.76,-29.76], [null,null,10.96,7.79,-29.14,-29.14]"};
        List<String> valuesFromUI;
        List<String> valuesFromServices;

        Thread t1 = new Thread(() -> {
        for(String url: urls) {
            System.out.println("Running from :" + url);
            try {
                getActualDataFromFundPageAsAlIst(url);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
           }
        });

        Thread t2 = new Thread(() -> {
            for(String url: urls) {
                System.out.println("Running from :" + url);
                Arrays.asList(services);}
        });

        t1.start();
        t2.start();
    }
}
