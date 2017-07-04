package youngtalents;

import android.app.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;


/**
 * Created by Simon on 30.06.2017.
 */

public class MainMenu extends Activity {


    Button btn_erstellen;
    Button btn_statistik;
    Button btn_ausloggen;
    TextView user_t;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mainmenu);


        btn_erstellen = (Button) findViewById(R.id.edittasks);
        btn_ausloggen = (Button) findViewById(R.id.logout);
        btn_statistik = (Button) findViewById(R.id.statistic);
        user_t = (TextView) findViewById(R.id.user);
        user_t.setText(SaveSharedPreference.getUserID(getApplicationContext()));



        btn_erstellen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent myIntent = new Intent(v.getContext(), Ersteller.class);
                startActivityForResult(myIntent, 0);

            }
        });

        btn_statistik.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                Intent myIntent = new Intent(v.getContext(), Statistics.class);
                startActivityForResult(myIntent, 0);


        }
        });

        btn_ausloggen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SaveSharedPreference.setUserName(getApplicationContext(), "");
                SaveSharedPreference.setUserID(getApplicationContext(), "");
                Intent myIntent = new Intent(v.getContext(), Logout.class);
                startActivityForResult(myIntent, 0);

            }
        });



    }
}
