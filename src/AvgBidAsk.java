public class AvgBidAsk {
    double avgBid;
    double avgAsk;
    double lowAsk;
    double highBid;
    double bidTotalShares;
    double askTotalShares;


    public AvgBidAsk(double bid, double ask, double totalBid, double totalAsk, double low, double buy){
        avgBid=bid;
        avgAsk=ask;
        lowAsk = low;
        highBid = buy;
        bidTotalShares=totalBid;
        askTotalShares=totalAsk;

    }

    public String toString(){

        return "["+avgBid+ " "+ avgAsk+"]";
    }

}
