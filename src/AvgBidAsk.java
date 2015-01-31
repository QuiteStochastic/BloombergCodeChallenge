public class AvgBidAsk {
    double avgBid;
    double avgAsk;
    double lowAsk;

    double bidTotalShares;
    double askTotalShares;


    public AvgBidAsk(double bid, double ask, double totalBid, double totalAsk, double low){
        avgBid=bid;
        avgAsk=ask;
        lowAsk = low;
        bidTotalShares=totalBid;
        askTotalShares=totalAsk;

    }

    public String toString(){

        return "["+avgBid+ " "+ avgAsk+"]";
    }

}
