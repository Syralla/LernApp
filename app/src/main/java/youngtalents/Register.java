package youngtalents;

import android.app.Activity;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

/**
 * Created by Simon on 28.06.2017.
 */

public class Register extends Activity{

    EditText user;
    EditText pasw;
    EditText email;
    Button btn;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.registration_layout);

        user = (EditText) findViewById(R.id.username_field_r);
        pasw = (EditText) findViewById(R.id.password_field_r);
        email = (EditText) findViewById(R.id.email_field_r);
        btn = (Button) findViewById(R.id.register_button);


        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(internetAvailable()){

                    FileBuilder builder = new FileBuilder();

                    TestClient client = new TestClient();

                    client.Register(builder.buildRegisterAsStringArray(user.getText().toString(), pasw.getText().toString(), email.getText().toString()));
                    Intent myIntent = new Intent(v.getContext(), MainActivity.class);
                    startActivityForResult(myIntent, 0);


                } else {
                    Toast.makeText(getApplicationContext(), "Internet ist nicht verfügbar.", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
    public boolean internetAvailable(){
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        return networkInfo != null && networkInfo.isConnectedOrConnecting();
    }

}
