package youngtalents;

import android.app.Activity;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.widget.Button;


/**
 * Created by Samara on 19.06.2017.
 */



import android.view.View;
import android.widget.Button;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.widget.CheckBox;
import android.widget.TextView;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.parser.Parser;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;


public class Statistics extends Activity { //Deklaration der benötigten Layout Elemente

    TextView allanswers;
    TextView plusstat;
    TextView minusstat;
    TextView malstat;
    TextView geteiltstat;
    String ret;
    TextView user_t;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.statistics);
        TestClient client = new TestClient();
        String user;
        user = SaveSharedPreference.getUserID(getApplicationContext());
        user_t = (TextView) findViewById(R.id.user);
        user_t.setText(SaveSharedPreference.getUserID(getApplicationContext()));

        ret = client.getStatisticsFromServer(user);

        Document doc = Jsoup.parse(ret, "", Parser.xmlParser());
        allanswers = (TextView) findViewById(R.id.rightwrong);
        plusstat = (TextView) findViewById(R.id.plusstat);
        minusstat = (TextView) findViewById(R.id.minusstat);
        malstat = (TextView) findViewById(R.id.malstat);
        geteiltstat = (TextView) findViewById(R.id.geteiltstat);

        System.out.println(ret);
        for (Element e : doc.select("statistic")) {

            allanswers.setText("Insgesamt: " + e.select("richtig").text() + " von " + e.select("gesamt").text());
            plusstat.setText("Plus: " + e.select("plusrichtig").text() + " von " + e.select("plusgesamt").text());
            minusstat.setText("Minus: " + e.select("minusrichtig").text() + " von " + e.select("minusgesamt").text());
            malstat.setText("Mal: " + e.select("malrichtig").text() + " von " + e.select("malgesamt").text());
            geteiltstat.setText("Geteilt: " + e.select("geteiltrichtig").text() + " von " + e.select("geteiltgesamt").text());


        }
    }




    public boolean internetAvailable(){
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        return networkInfo != null && networkInfo.isConnectedOrConnecting();
    }
}