import java.io.IOException;

public class Ticker extends Thread{

    final int tickerBufferSize=32;
    final String companyName;
    AvgBidAsk ticker[];
    private int arrayIndexCounter=0;


    long queryTimeRate =1000;


    public AvgBidAsk getCurrentAvgAskBid(){
        String info;
        try {
            info = ExchangeAPI.exchangeCommand("ORDERS "+companyName);
        } catch (IOException e) {
            System.out.println("ticker thread exited for company: "+ companyName);
            System.exit(-1);
            return null;
        }

        String[] infoArray = info.split(" ");
        double bidAvg = 0;
        double bidTotalShares = 0;
        double askAvg = 0;
        double askTotalShares = 0;

        if(!infoArray[0].equals("SECURITY_ORDERS_OUT")){
            System.out.println("did not assign a proper value to ticker, company: "+companyName);
            return null;
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

        return new AvgBidAsk(bidAvg,askAvg,bidTotalShares,askTotalShares);

        //return ticker[arrayIndexCounter];
    }


    public Ticker(String company){

        companyName=company;

        ticker=new AvgBidAsk[tickerBufferSize];
    }

    public double findGap(){

        //AvgBidAsk currentAverages=getCurrentAvgAskBid();

        String info;
        try {
            info = ExchangeAPI.exchangeCommand("ORDERS "+companyName);
        } catch (IOException e) {
            System.out.println("ticker thread exited for company: "+ companyName);
            System.exit(-1);
            return 0;
        }

        System.out.println("INFO, ticker: "+companyName+": "+info);

        String[] infoArray = info.split(" ");
        double bidAvg = 0;
        double bidTotalShares = 0;
        double askAvg = 0;
        double askTotalShares = 0;

        if(!infoArray[0].equals("SECURITY_ORDERS_OUT")){
            System.out.println("did not assign a proper value to ticker, company: "+companyName);
            return 0;
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

        return askAvg-bidAvg;
    }


    @Override
    public void run() {

/*
        while (true){
            String info;
            try {
                info = ExchangeAPI.exchangeCommand("ORDERS "+companyName);
            } catch (IOException e) {
                System.out.println("ticker thread exited for company: "+ companyName);
                System.exit(-1);
                return;
            }
            //System.out.println("INFO, ticker: "+companyName+": "+info);

            String[] infoArray = info.split(" ");
            double bidAvg = 0;
            double bidTotalShares = 0;
            double askAvg = 0;
            double askTotalShares = 0;

            if(!infoArray[0].equals("SECURITY_ORDERS_OUT")){
                System.out.println("did not assign a proper value to ticker, company: "+companyName);
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

            try {
                Thread.sleep(queryTimeRate);
            }
            catch (InterruptedException e){
                continue;
            }
        }*/
    }
}