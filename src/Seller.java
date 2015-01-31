import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

public class Seller extends Thread{


    HashMap<String, ArrayList<Double>> holdings;
    HashMap<String, Ticker> companyTickers = new HashMap<String, Ticker>();
    double cash;

    public Seller(HashMap<String, ArrayList<Double>> holdings, HashMap<String, Ticker> companyTickers, double c){

        this.holdings=holdings;
        this.companyTickers=companyTickers;
        cash = c;
    }


    @Override
    public void run(){
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        while(true){



            String nameToAsk = (String) holdings.keySet().toArray()[0];

            try {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                double askPrice = 0;
                int index = 0;
                int shares = (int) Math.round(0.2 * cash);
                double longestHold = Double.MIN_VALUE;
                String secondName = nameToAsk;
                double longAskPrice = 0;
                Double max = Double.MIN_VALUE;
                for (String ticker: holdings.keySet()) {
                    ArrayList<Double> priceAndShare = holdings.get(ticker);

                    AvgBidAsk testticker[] = companyTickers.get(ticker).ticker;
                    for (int i = 0; i < testticker.length; i++) {
                        if (testticker[i] != null) {
                            double maxDiff = testticker[i].highBid - priceAndShare.get(0);
                            if (max < maxDiff) {
                                askPrice = testticker[i].highBid;
                                index = i;
                                nameToAsk = ticker;
                            }
                            if(longestHold < priceAndShare.get(2)) {
                                secondName = ticker;
                                longAskPrice = testticker[i].highBid;
                            }

                        }

                    }
                }

                System.out.println("To ask on "+nameToAsk);
                System.out.println("price to ask: " + askPrice + "shares " + shares);
                System.out.println(ExchangeAPI.exchangeCommand("BID " + nameToAsk + " " + askPrice + " " + shares));

                System.out.println("To ask on "+secondName);
                System.out.println("price to ask: " + longAskPrice + "shares " + shares);
                System.out.println(ExchangeAPI.exchangeCommand("BID " + secondName + " " + longAskPrice + " " + shares));

            }
            catch (IOException e) {
                System.out.println("asking: exited for company: " + nameToAsk);
            }
        }




    }

}
