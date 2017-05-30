package de.derandroidpro.sendtexttoservertutorial;

import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;

public class MainActivity extends AppCompatActivity {

    EditText et;
    Button btn;
    TextView tv;
    int Art;


    final String scripturlstring = "http://simon-f.com/receive_script.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        System.out.println("Point one");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        tv = (TextView) findViewById(R.id.textView);
        btn = (Button) findViewById(R.id.button);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(internetAvailable()){

                    CheckBox Plus = (CheckBox) findViewById(R.id.PPlus);
                    CheckBox Minus = (CheckBox) findViewById(R.id.Minus);
                    CheckBox Mal = (CheckBox) findViewById(R.id.Mal);
                    CheckBox Geteilt = (CheckBox) findViewById(R.id.Geteilt);
                    CheckBox Eins = (CheckBox) findViewById(R.id.Ein);
                    CheckBox Zwei = (CheckBox) findViewById(R.id.Zwei);
                    CheckBox Drei = (CheckBox) findViewById(R.id.Drei);
                    Art = 10000000;

                    if ( Plus.isChecked()) {
                        Art = Art + 1000;

                    }

                    if ( Minus.isChecked()){
                        Art = Art + 10000;

                    }

                    if ( Mal.isChecked() == true){
                        Art = Art + 100000;

                    }

                    if ( Geteilt.isChecked() == true){
                        Art = Art + 1000000;

                    }

                    if ( Eins.isChecked() == true){
                        Art = Art + 1;

                    }

                    if ( Zwei.isChecked() == true){
                        Art = Art + 10;

                    }

                    if ( Drei.isChecked() == true){
                        Art = Art + 100;

                    }
                    Art = Art - 10000000;
                    sendToServer((""+ Art));
                } else {
                    Toast.makeText(getApplicationContext(), "Internet ist nicht verfügbar.", Toast.LENGTH_SHORT).show();
                }
            }
        });
        System.out.println("Point two");
    }

    public void sendToServer(final String text){




        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    String textparam = "text1=" + URLEncoder.encode(text, "UTF-8");

                    URL scripturl = new URL(scripturlstring);
                    HttpURLConnection connection = (HttpURLConnection) scripturl.openConnection();
                    connection.setDoOutput(true);
                    connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
                    connection.setFixedLengthStreamingMode(textparam.getBytes().length);

                    OutputStreamWriter contentWriter = new OutputStreamWriter(connection.getOutputStream());
                    contentWriter.write(textparam);
                    contentWriter.flush();
                    contentWriter.close();

                    InputStream answerInputStream = connection.getInputStream();
                    final String answer = getTextFromInputStream(answerInputStream);

                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            tv.setText(answer);
                        }
                    });
                    answerInputStream.close();
                    connection.disconnect();


                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();

    }

    public String getTextFromInputStream(InputStream is){
        BufferedReader reader = new BufferedReader(new InputStreamReader(is));
        StringBuilder stringBuilder = new StringBuilder();

        String aktuelleZeile;
        try {
            while ((aktuelleZeile = reader.readLine()) != null){
                stringBuilder.append(aktuelleZeile);
                stringBuilder.append("\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return stringBuilder.toString().trim();
    }

    public boolean internetAvailable(){
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        return networkInfo != null && networkInfo.isConnectedOrConnecting();
    }
}
