import java.io.IOException;

public class TradingThread extends Thread{

    final String companyName;
    final Ticker ticker;
    final RelativeMonitor monitor;

    public TradingThread(String companyName, Ticker ticker, RelativeMonitor monitor){
        this.companyName=companyName;
        this.ticker=ticker;
        this.monitor=monitor;

    }

    @Override
    public void run() {

        if(monitor.getCompanyWithNarrowestGap().equals(companyName)){
            try {
                String cash = ExchangeAPI.exchangeCommand("MY_CASH");
                String cashArray []=cash.split(" ");
                double cashdouble=Double.parseDouble(cashArray[1]);
                ExchangeAPI.exchangeCommand("BID "+ companyName + " " + ticker.getCurrentAvgAskBid().avgAsk
                        + " " + Math.floor(cashdouble *0.01 ));
            } catch (IOException e) {
                System.out.println("bid exited for company: "+ companyName);
                System.exit(-1);
                return;
            }
        }


    }
}
