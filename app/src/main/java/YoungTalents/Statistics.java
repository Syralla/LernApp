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

    TextView malstat;
    String ret;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.statistics);
        TestClient client = new TestClient();
        String user;
        user = SaveSharedPreference.getUserName(getApplicationContext());

        ret = client.getStatisticsFromServer(user);

        Document doc = Jsoup.parse(ret, "", Parser.xmlParser());
        malstat = (TextView) findViewById(R.id.malstat);

        for (Element e : doc.select("statistic")) {
            int zahl1 = 0;
            int zahl2 = 0;
            String op = "";
            malstat.setText("Du hast das als malstat" + e.select("malstat").text());


        }
    }




    public boolean internetAvailable(){
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        return networkInfo != null && networkInfo.isConnectedOrConnecting();
    }
}