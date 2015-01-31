import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

public class Buyer extends Thread{

    HashMap<String, ArrayList<Double>> holdings;
    HashMap<String, Ticker> companyTickers = new HashMap<String, Ticker>();


    public Buyer(HashMap<String, ArrayList<Double>> holdings, HashMap<String, Ticker> companyTickers){

        this.holdings=holdings;
        this.companyTickers=companyTickers;
    }

    @Override
    public void run(){


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
                            } else if (difference == min) {
                                if(testticker[i].bidTotalShares > testticker[index].bidTotalShares) {
                                    min = difference;
                                    index = i;
                                    ticker = name;
                                }
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



                if(holdings.containsKey(ticker)){
                    ArrayList<Double> oldValues=holdings.get(ticker);
                    oldValues.set(0,(oldValues.get(0)+priceToBuy)/2);
                    oldValues.set(1,oldValues.get(1)+sharesToBuy);
                }
                else{
                    ArrayList<Double> priceAndShares = new ArrayList<Double>(2);
                    priceAndShares.add(0, priceToBuy);
                    priceAndShares.add(1, (double)sharesToBuy);
                    holdings.put(ticker, priceAndShares);

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

}
