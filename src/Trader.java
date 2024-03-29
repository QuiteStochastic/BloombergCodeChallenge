import org.omg.PortableServer.THREAD_POLICY_ID;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;

public class Trader {


    static ArrayList<String>allCompanyTickerNames = new ArrayList<String>();
    static{

        String info = "";
        try
        {
            info = ExchangeAPI.exchangeCommand("SECURITIES");
        } catch (IOException e)
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        
        String[] infoArray = info.split(" ");
        for (int i = 1; i < infoArray.length; i += 4)
        {
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

    public static void main(String[] args){



        /*        String info;
        try{
            info=ExchangeAPI.exchangeCommand("SECURITIES");
        }
        catch (IOException e){
            System.exit(-1);
        }*/

        HashMap<String,Ticker> companyTickers = new HashMap<String, Ticker>();
        for(String s: allCompanyTickerNames){
            companyTickers.put(s,new Ticker(s));
            companyTickers.get(s).start();
        }



        RelativeMonitor monitor=new RelativeMonitor(companyTickers);

        HashMap<String, TradingThread> traders= new HashMap<String, TradingThread>();
        for(String s: allCompanyTickerNames){
            traders.put(s,new TradingThread(s, companyTickers.get(s),monitor));
            traders.get(s).start();
        }

/*
        while(true){

            AvgBidAsk testticker[]=companyTickers.get("AAPL").ticker;


            for(int i=0;i<testticker.length;i++){

                System.out.print(testticker[i]+" ");
            }

            System.out.println("\n\n");




            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }*/

    }

}

