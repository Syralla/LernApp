package youngtalents;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

/**
 * Created by Simon on 01.07.2017.
 */


public class Aufgaben extends Activity {

    public String spez;
    TextView user_t;
    Button menu;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.task);
        spez = getIntent().getStringExtra("anforderungen");
        user_t = (TextView) findViewById(R.id.user);
        user_t.setText(SaveSharedPreference.getUserID(getApplicationContext()));
        menu = (Button) findViewById(R.id.backtomenu);

        menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent myIntent = new Intent(v.getContext(), MainMenu.class);
                startActivityForResult(myIntent, 0);

            }
        });






    }


}
