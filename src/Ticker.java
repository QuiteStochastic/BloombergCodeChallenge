import java.io.IOException;

public class Ticker extends Thread{

    final int tickerBufferSize=32;
    final String companyName;
    AvgBidAsk ticker[];

    long queryTimeGap =10000;


    public Ticker(String company){

        companyName=company;

        ticker=new AvgBidAsk[tickerBufferSize];
    }

    @Override
    public void run() {

        int arrayIndexCounter=0;

        while (true){
            String info;
            try {
                info = ExchangeAPI.exchangeCommand("ORDERS "+companyName);
            } catch (IOException e) {
                System.out.println("ticker thread exited for company: "+ companyName);
                System.exit(-1);
                return;
            }
//            System.out.println("INFO, ticker: "+companyName+": "+info);

            String[] infoArray = info.split(" ");
            double bidAvg = 0;
            double bidTotalShares = 0;
            double askAvg = 0;
            double askTotalShares = 0;

            if(!infoArray[0].equals("SECURITY_ORDERS_OUT")){
                continue;
            }

            for (int i = 1; i < infoArray.length; i += 4) {
                if (infoArray[i].equals("BID")) {
                    bidAvg = bidAvg + Double.parseDouble(infoArray[i + 2]) * Double.parseDouble(infoArray[i + 3]);
                    bidTotalShares = bidTotalShares + Double.parseDouble(infoArray[i + 3]);
                } else if (infoArray[i].equals("ASK")) {
                    askAvg = askAvg + Double.parseDouble(infoArray[i + 2]) * Double.parseDouble(infoArray[i + 3]);
                    askTotalShares = askTotalShares + Double.parseDouble(infoArray[i + 3]);

                }
            }

            bidAvg = bidAvg / bidTotalShares;
            askAvg = askAvg / askTotalShares;

            ticker[arrayIndexCounter] = new AvgBidAsk(bidAvg, askAvg,bidTotalShares, askTotalShares);

            if(arrayIndexCounter>=tickerBufferSize-1){
                arrayIndexCounter=0;
            }
            else{
                arrayIndexCounter++;
            }

            try
            {
                Thread.sleep(queryTimeGap);
            }
            catch (InterruptedException e){
                continue;
            }
        }
    }
}