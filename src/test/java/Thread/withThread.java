package Thread;

import consoleColors.ConsoleColors;

import java.io.IOException;
import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static pageHelper.getMutualFundData.getActualDataFromFundPageAsAlIst;


public class withThread {
    /*
    This program creates a list of tasks that need to be executed asynchronously. It also creates a CountDownLatch which is a timer that
    counts down from the number of tasks in the list. Then, it creates a thread pool with a size equal to the number of tasks in
    the list. For each task in the list, it runs the task in the thread pool. Once all tasks have been executed, it waits for the
    CountDownLatch to reach zero, indicating that all tasks have been completed. Finally, it shuts down the thread pool.
    */

    public static void main(String[] args) throws InterruptedException {
       // Get the URLS
        ArrayList<String> urls = getURLFromExcel();
        /*CountDownLatch is like a timer that counts down from a certain number. You can use it when you want to wait until something
        completes before you start doing something else. For example, if you are playing a game with your friends, and you want to
        wait until everyone is ready before you start, you can use a CountDownLatch. You can set the timer to count down until everyone
         is ready, and when it reaches zero, you can start the game.
         */
        CountDownLatch countDownLatch = new CountDownLatch(urls.size());
        // This is what the individual task will do work
        ArrayList<Runnable> tasks = new ArrayList<>();
        for (String url : urls) {
            tasks.add(() -> {
                System.out.println(" Executing Task for " + url);
                try {
                    getActualDataFromFundPageAsAlIst(url); // hitting UI and fetching data from UI for each URL
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                countDownLatch.countDown();
            });
        }

        /* creates a thread pool with a size equal to the number of tasks in
        the list. For each task in the list, it runs the task in the thread pool.
        */
        ExecutorService executor = Executors.newFixedThreadPool(tasks.size());
        /*
        Threadpool is a group of threads that you can use to run tasks. It's like having multiple workers that can do different tasks at the same time.
        For example, if you need to build a house, you could have one worker doing the roof, another doing the walls, and a third doing the
        foundation. Threadpools work the same way, but instead of building a house, they can help you run tasks faster.
         */
        Instant start = Instant.now();

        for (Runnable task : tasks) {
            executor.execute(task);
        }
        countDownLatch.await();
        executor.shutdown();

        System.out.println("All task executed");
        Instant end = Instant.now();
        Duration timeElapsed = Duration.between(start, end);

        System.out.println("------------------------------------------------------------------------------------------");
        System.out.println("Total time in scrapping with thread concept:: : " + ConsoleColors.GREEN_BOLD   + timeElapsed.toSeconds() + " seconds" + ConsoleColors.RESET);
        System.out.println("------------------------------------------------------------------------------------------");
    }

    static ArrayList<String> getURLFromExcel() {
        ArrayList<String> urls = new ArrayList<>();
        urls.add("https://www.mfs.com/en-us/individual-investor/product-strategies/mutual-funds/MUSEX-mfs-blended-research-core-equity-fund.html");
        urls.add("https://www.mfs.com/en-us/individual-investor/product-strategies/mutual-funds/BMSLX-mfs-blended-research-mid-cap-equity-fund.html");
        urls.add("https://www.mfs.com/en-us/individual-investor/product-strategies/mutual-funds/BRSJX-mfs-blended-research-small-cap-equity-fund.html");
        urls.add("https://www.mfs.com/en-us/individual-investor/product-strategies/mutual-funds/BRUHX-mfs-blended-research-value-equity-fund.html");
        urls.add("https://www.mfs.com/en-us/individual-investor/product-strategies/mutual-funds/MRGRX-mfs-core-equity-fund.html");
        return urls;
    }
}


