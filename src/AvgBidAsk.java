public class AvgBidAsk {
    double avgBid;
    double avgAsk;


    public AvgBidAsk(double bid, double ask){
        avgBid=bid;
        avgAsk=ask;

    }

    public String toString(){

        return "["+avgBid+ " "+ avgAsk+"]";
    }

}
