package youngtalents;




import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import java.io.IOException;

public class MainActivity extends AppCompatActivity {


    EditText user;
    EditText pasw;
    Button btn;
    Button reg;
    CheckBox logged;
    TextView fail;


    @Override
    protected void onCreate(Bundle savedInstanceState) { //Die onCreate Methode wird aufgerufen sobald die Activity aufgerufen wird. Also sobald diese App Seite verwendet wird.
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_layout); //Diese zeile legt das Layout der Activity fest

        if (SaveSharedPreference.getUserName(MainActivity.this).length() == 0) { //Hier wird geschaut ob ein Username abgespeichert ist, da dass bedeutet das ein user schon eigeloggt ist und das auch bleiben will

        } else {
            Intent myIntent = new Intent(getApplicationContext(), MainMenu.class); //wenn einer vorhanden ist wird eine neue Activity aufgerufen
            startActivityForResult(myIntent, 0);

        }
        System.out.println("Point one");


        logged = (CheckBox) findViewById(R.id.keepmeloggedin);
        user = (EditText) findViewById(R.id.username_field_r);
        pasw = (EditText) findViewById(R.id.password_field_r);
        btn = (Button) findViewById(R.id.login);
        reg = (Button) findViewById(R.id.register_button);
        fail = (TextView) findViewById(R.id.loginfail);


        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) { //Dieser Listener wird ausgeführt sobald der Loginbutton gedrückt wird
                if (internetAvailable()) {


                    FileBuilder builder = new FileBuilder(); //ein FileBuilder wird definiert um den Login darin in einen StringArray umzuwandeln
                    TestClient client = new TestClient(); //Ein Client wird definiert. In diesem Client wird ein Socket erstellt welcher für die Verbindung zum Server genutzt wird

                    try {
                        if (client.Login(builder.buildLoginAsStringArray(user.getText().toString(), pasw.getText().toString())) == true) { //Wenn der Login erfolgreich war wird geschaut ob der User eingeloggt bleiben will
                            if (logged.isChecked()) {


                                SaveSharedPreference.setUserName(getApplicationContext(), user.getText().toString()); //User abspeichern für logged in

                            }
                            SaveSharedPreference.setUserID(getApplicationContext(), user.getText().toString()); //Username abspeichern für anzeige
                            Intent myIntent = new Intent(v.getContext(), MainMenu.class);
                            startActivityForResult(myIntent, 0);

                        } else {


                            fail.setText("Dein Benutzername und dein Passwort passen nicht zusammen. Hast du dich vielleicht vertippt?"); //falls der Login nicht erfolgreich war wird diese Fehlermeldung angezeigt


                        }
                    } catch (IOException e) { //Da es zu einer Exception kommen kann die die App zum abstürzen bringen könnte muss diese hier abgefangen werden
                        e.printStackTrace();

                    }


                } else {

                }
            }
        });


        reg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) { //Sobald der User regisitriert werden will wird diese Activity aufgerufen
                Intent myIntent = new Intent(v.getContext(), Register.class);
                startActivityForResult(myIntent, 0);
            }
        });





    }



    public boolean internetAvailable() { //Diese Methode schaut ob eine Internet Verbindung vorhanden ist.. sonst wäre die App ja nicht benutzbar
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        return networkInfo != null && networkInfo.isConnectedOrConnecting();
    }



}




