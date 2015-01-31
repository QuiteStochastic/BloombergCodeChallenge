import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

public class Seller extends Thread{


    HashMap<String, ArrayList<Double>> holdings;
    HashMap<String, Ticker> companyTickers = new HashMap<String, Ticker>();


    public Seller(HashMap<String, ArrayList<Double>> holdings, HashMap<String, Ticker> companyTickers){

        this.holdings=holdings;
        this.companyTickers=companyTickers;
    }


    @Override
    public void run(){
        while(true){


            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            String nameToAsk = (String) holdings.keySet().toArray()[0];

            try {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                double askPrice = 0;
                int index = 0;
                int shares = 10;

                for (String ticker: holdings.keySet()) {
                    ArrayList<Double> priceAndShare = holdings.get(ticker);
                    AvgBidAsk testticker[] = companyTickers.get(ticker).ticker;
                    for (int i = 0; i < testticker.length; i++) {
                        if (testticker[i] != null) {
                            if (priceAndShare.get(0) < testticker[i].highBid) {
                                askPrice = testticker[i].highBid;
                                index = i;
                                nameToAsk = ticker;
                            }

                        }
                    }
                }

                System.out.println("To ask on "+nameToAsk);
                System.out.println("price to ask: " + askPrice + "shares " + shares);
                System.out.println(ExchangeAPI.exchangeCommand("BID " + nameToAsk + " " + askPrice + " " + shares));

            }
            catch (IOException e) {
                System.out.println("asking: exited for company: " + nameToAsk);
            }
        }




    }

}
