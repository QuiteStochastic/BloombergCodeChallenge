public class AvgBidAsk {
    double avgBid;
    double avgAsk;

    double bidTotalShares;
    double askTotalShares;


    public AvgBidAsk(double bid, double ask, double totalBid, double totalAsk){
        avgBid=bid;
        avgAsk=ask;

        bidTotalShares=totalBid;
        askTotalShares=totalAsk;

    }

    public String toString(){

        return "["+avgBid+ " "+ avgAsk+"]";
    }

}
