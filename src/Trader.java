import java.io.IOException;
import java.util.ArrayList;
import java.util.DoubleSummaryStatistics;

public class Trader {


    static ArrayList<String> allCompanyTickerNames=new ArrayList<String>();
    {
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


    }

    public static void main(String[] args){



/*        String info;
        try{
            info=ExchangeAPI.exchangeCommand("SECURITIES");
        }
        catch (IOException e){
            System.exit(-1);
        }*/


        Ticker aaplTicker=new Ticker("AAPL");
        aaplTicker.start();


        while(true){

            for(int i=0;i<aaplTicker.ticker.length;i++){

                System.out.print(aaplTicker.ticker[i]+" ");
            }

            System.out.println("\n\n");

            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }

    }

}

