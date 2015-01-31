import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

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
                int shares = (int) Math.round(0.1 * cash);
                double longestHold = Double.MIN_VALUE;
                String secondName = nameToAsk;
                double longAskPrice = 0;
                Double max = Double.MIN_VALUE;

                String []names = holdings.keySet().toArray(new String[holdings.size()]);
                int count = 0;
                int START = 0;
                int END = names.length-1;
                Random random = new Random();
                int rand = getRandomInteger(START, END, random);
                System.out.println("random number " + rand + "length " +names.length);
                String s = nameToAsk;
                if(names.length > 0)
                    s =  names[rand];
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
                            if(s.equals(ticker) || testticker[i].highBid >= priceAndShare.get(1)) {
                                s = ticker;
                                count = i;
                            }
                        }

                    }
                }


                System.out.println("To ask on "+nameToAsk);
                System.out.println("price to ask: " + askPrice + "shares " + shares);
                System.out.println(ExchangeAPI.exchangeCommand("ASK " + nameToAsk + " " + askPrice + " " + shares));

                System.out.println("To ask on "+secondName);
                System.out.println("price to ask: " + longAskPrice + "shares " + shares);
                System.out.println(ExchangeAPI.exchangeCommand("ASK " + secondName + " " + longAskPrice + " " + shares));

//                try {
//                    Thread.sleep(500);
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }

                AvgBidAsk newticker[] = companyTickers.get(s).ticker;
                ArrayList<Double> priceAndShare = holdings.get(s);
                double ask = newticker[count].highBid;
//                ask += .01*ask;
//                if(ask >= priceAndShare.get(1)) {
                    System.out.println("To bid on " + s + " shares: " + shares);
                    System.out.println(ExchangeAPI.exchangeCommand("ASK " + s + " " + ask + " " + shares));
//                }

            }
            catch (IOException e) {
                System.out.println("asking: exited for company: " + nameToAsk);
            }
        }



    }

    private static int getRandomInteger(int aStart, int aEnd, Random aRandom){
        if (aStart > aEnd) {
            throw new IllegalArgumentException("Start cannot exceed End.");
        }
        //get the range, casting to long to avoid overflow problems
        long range = (long)aEnd - (long)aStart + 1;
        // compute a fraction of the range, 0 <= frac < range
        long fraction = (long)(range * aRandom.nextDouble());
        int randomNumber =  (int)(fraction + aStart);
        return randomNumber;
    }

}
