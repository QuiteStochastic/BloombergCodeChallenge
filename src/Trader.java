import java.util.ArrayList;
import java.util.HashMap;

public class Trader {


    static ArrayList<String>allCompanyTickerNames;
    static {
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

        /*

aapl
atvi
ea
fb
goog
msft
sbux
sny
tsla
twtr

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

        }

    }

}

