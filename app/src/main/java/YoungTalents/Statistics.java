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
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;


public class Statistics extends Activity { //Deklaration der benötigten Layout Elemente
    Button btn;
    CheckBox plus;
    CheckBox min;
    CheckBox mul;
    CheckBox div;
    CheckBox eins;
    CheckBox zwei;
    CheckBox drei;
    TextView txt;
    int anforderungen;
    String ret;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.statistics);








    }




    public boolean internetAvailable(){
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        return networkInfo != null && networkInfo.isConnectedOrConnecting();
    }
}