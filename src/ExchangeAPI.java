import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class ExchangeAPI {


    public static String exchangeCommand(String command) throws IOException{

        String host="codebb.cloudapp.net";
        int port=17429;
        String user="codeb404";
        String password="jinliemli";

        Socket socket = new Socket(host, port);
        PrintWriter pout = new PrintWriter(socket.getOutputStream());
        BufferedReader bin = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        pout.println(user + " " + password);

        pout.println(command);

   /*for (int i = 0; i < args.length; i++) {
        pout.println(args[i]);
    }*/
        pout.println("CLOSE_CONNECTION");
        pout.flush();
        String line = "";
    while ((line = bin.readLine()) != null) {
//        System.out.println(line);
    }
        pout.close();
        bin.close();

        return line;

    }

}
