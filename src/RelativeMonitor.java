import java.util.HashMap;

public class RelativeMonitor {

    HashMap<String,Ticker> companyTickers = new HashMap<String, Ticker>();
    String narrowestGapCompany="";


    public RelativeMonitor(HashMap<String,Ticker> companyTickers){

        this.companyTickers=companyTickers;
    }


    public String getCompanyWithNarrowestGap(){

        double minimumGap= Double.MAX_VALUE;
        for(String s: companyTickers.keySet()){

            //AvgBidAsk a=companyTickers.get(s).getCurrentAvgAskBid();

            if(companyTickers.get(s).findGap() <= minimumGap){
                minimumGap=companyTickers.get(s).findGap();
                narrowestGapCompany=s;
            }


        }
        return narrowestGapCompany;
    }

}
