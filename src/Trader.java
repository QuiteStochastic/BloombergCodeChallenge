import java.io.IOException;

public class Trader {

    public static void main(String[] args){

        String info;
        try{
            info=ExchangeAPI.exchangeCommand("SECURITIES");
        }
        catch (IOException e){
            System.exit(-1);
        }




    }

}
