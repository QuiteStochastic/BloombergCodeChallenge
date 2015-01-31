import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

public class Buyer extends Thread{

    HashMap<String, ArrayList<Double>> holdings;
    HashMap<String, Ticker> companyTickers = new HashMap<String, Ticker>();
    double cash;

    public Buyer(HashMap<String, ArrayList<Double>> holdings, HashMap<String, Ticker> companyTickers, double c){

        this.holdings=holdings;
        this.companyTickers=companyTickers;
        cash = c;
    }

    @Override
    public void run(){


        while (true) {
            String ticker = "AAPL";


            try {


                int index = 0;
                int count = 0;
                String []names = companyTickers.keySet().toArray(new String[companyTickers.size()]);
                int START = 0;
                int END = names.length-1;
                Random random = new Random();
                int rand = getRandomInteger(START, END, random);
                System.out.println("buyer random number " + rand + "length " +names.length);
                String s = ticker;
                if(names.length > 0)
                     s =  names[rand];
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
                            } else if (difference == min) {
                                if(testticker[i].bidTotalShares > testticker[index].bidTotalShares) {
                                    min = difference;
                                    index = i;
                                    ticker = name;
                                }
                            }
                            if(s.equals(name)) {
                                count = i;
                            }

                        }
                    }
                }


                AvgBidAsk bidticker[] = companyTickers.get(ticker).ticker;


                double priceToBuy = bidticker[index].lowAsk;
                int sharesToBuy = (int) Math.round(0.1 * cash);

                System.out.println("To bid on "+ticker + " shares: " + sharesToBuy);
                System.out.println(ExchangeAPI.exchangeCommand("BID " + ticker + " " + priceToBuy + " " + sharesToBuy));

                AvgBidAsk newticker[] = companyTickers.get(s).ticker;
                double bid = newticker[count].lowAsk;
                System.out.println("To bid on "+ s + " shares: " + sharesToBuy);
                System.out.println(ExchangeAPI.exchangeCommand("BID " + s + " " + bid + " " + sharesToBuy));

                if(holdings.containsKey(ticker)){
                    ArrayList<Double> oldValues=holdings.get(ticker);
                    oldValues.set(0,(oldValues.get(0)+priceToBuy)/2);
                    oldValues.set(1,oldValues.get(1)+sharesToBuy);
                    oldValues.set(2, oldValues.get(2)+1);
                }
                else{
                    ArrayList<Double> priceAndShares = new ArrayList<Double>(3);
                    priceAndShares.add(0, priceToBuy);
                    priceAndShares.add(1, (double)sharesToBuy);
                    priceAndShares.add(2, 1.0);
                    holdings.put(ticker, priceAndShares);

                }
                if(holdings.containsKey(s)){
                    ArrayList<Double> oldValues=holdings.get(s);
                    oldValues.set(0,(oldValues.get(0)+bid)/2);
                    oldValues.set(1,oldValues.get(1)+sharesToBuy);
                    oldValues.set(2, oldValues.get(2)+1);
                }
                else{
                    ArrayList<Double> priceAndShares = new ArrayList<Double>(3);
                    priceAndShares.add(0, bid);
                    priceAndShares.add(1, (double)sharesToBuy);
                    priceAndShares.add(2, 1.0);
                    holdings.put(s, priceAndShares);

                }


                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }


            }
            catch (IOException e) {
                System.out.println("trading: exited for company: " + ticker);
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
