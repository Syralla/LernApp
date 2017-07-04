package youngtalents;

import android.app.Activity;
<<<<<<< HEAD

/**
 * Created by Samara on 19.06.2017.
 */

class Statistics extends Activity{
=======
import android.view.View;
import android.widget.Button;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;


public class Statistics extends Activity {

    Button btn;
    public String ret;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.statistics);

        System.out.println("Sende Statistik");
        Runnable r = new Runnable() {
            @Override
            public void run() {
                TestClient.ret = false;
                NetworkAction action = new NetworkAction() {

                    @Override
                    public void performAction(PrintWriter pw, BufferedReader br) throws IOException {
                        pw.println(4);
                        pw.flush();
                        pw.println();   //userid übergeben
                        String in = br.readLine();
                        ret = in;

                    }
                };

            }
        };
    }

>>>>>>> origin/master
}










    public boolean internetAvailable(){
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        return networkInfo != null && networkInfo.isConnectedOrConnecting();
    }
}