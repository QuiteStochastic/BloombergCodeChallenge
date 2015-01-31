import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;

public class Trader {

    //price, shares
    static HashMap<String, ArrayList<Double>> holdings = new HashMap<String, ArrayList<Double>>();

    static ArrayList<String> allCompanyTickerNames = new ArrayList<String>();

    static {

        String info = "";
        try {
            info = ExchangeAPI.exchangeCommand("SECURITIES");
        } catch (IOException e) {


            e.printStackTrace();
        }

        String[] infoArray = info.split(" ");
        for (int i = 1; i < infoArray.length; i += 4) {
            allCompanyTickerNames.add(infoArray[i]);
        }
        
        /*
         * 
         * SECURITIES_OUT AAPL 810122.6164183568 3.0E-4 0.007 ATVI 988902.5998149839 3.0E-4 0.004 
         * CPNO 709426.8230083308 2.5E-4 0.005 EA 1011515.7442461837 7.0E-4 0.001 FB 1022458.6672787062 3.5E-4 0.005 
         * GOOG 1000269.6620134508 6.0E-4 0.003 MSFT 1029349.0534613427 2.0E-4 0.005 SNY 1162561.3223960286 8.0E-4 0.005 
         * TSLA 909912.0848054241 3.0E-4 0.002 TWTR 926763.9239317774 7.0E-4 0.002 
         */

        /*      
        allCompanyTickerNames=new ArrayList<String>();
        allCompanyTickerNames.add("AAPL");
        allCompanyTickerNames.add("ATVI");
        allCompanyTickerNames.add("EA");
        allCompanyTickerNames.add("FB");
        allCompanyTickerNames.add("GOOG");
        allCompanyTickerNames.add("MSFT");
        allCompanyTickerNames.add("SBUX");
        allCompanyTickerNames.add("SNY");
        allCompanyTickerNames.add("TSLA");
        allCompanyTickerNames.add("TWTR");
         */
        /*

         */


    }

    public static void main(String[] args) {


        HashMap<String, Ticker> companyTickers = new HashMap<String, Ticker>();
        for (String s : allCompanyTickerNames) {
            companyTickers.put(s, new Ticker(s));
            companyTickers.get(s).start();
        }


        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        while (true) {
            String ticker = "AAPL";


            try {

                int index = 0;
                double min = Double.MAX_VALUE;
                for (String name : companyTickers.keySet()) {
                    AvgBidAsk testticker[] = companyTickers.get(name).ticker;
                    for (int i = 0; i < testticker.length; i++) {
                        if (testticker[i] != null) {
                            double difference = testticker[i].avgAsk - testticker[i].avgBid;
                            if (difference < min) {
                                min = difference;
                                index = i;
                                ticker = name;
                            }
                        }
                    }
                }


                AvgBidAsk bidticker[] = companyTickers.get(ticker).ticker;

                String cash = ExchangeAPI.exchangeCommand("MY_CASH");
                String[] cashArray = cash.split(" ");


                double priceToBuy = bidticker[index].lowAsk;
                int sharesToBuy = 10;

                System.out.println("To bid on "+ticker);
                System.out.println(ExchangeAPI.exchangeCommand("BID " + ticker + " " + priceToBuy + " " + sharesToBuy));
                ArrayList<Double> priceAndShares = new ArrayList<Double>(2);
                priceAndShares.add(0, priceToBuy);
                priceAndShares.add(1, (double)sharesToBuy);



                holdings.put(ticker, priceAndShares);


                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }


            }
            catch (IOException e) {
                System.out.println("trading: exited for company: " + ticker);
                System.exit(-1);
                return;
            }

        }

    }
}

