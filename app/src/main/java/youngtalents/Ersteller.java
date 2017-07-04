package youngtalents;

import android.app.Activity;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;



public class Ersteller extends Activity { //DIese Activity dient dazu, dass der User entscheiden kann, was für Aufgaben er bearbeiten will

    //Deklaration der benötigten Layout Elemente
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

    TextView user_t;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btn = (Button) findViewById(R.id.button);
        plus = (CheckBox) findViewById(R.id.PPlus);
        min = (CheckBox) findViewById(R.id.Minus);
        mul = (CheckBox) findViewById(R.id.Mal);
        div = (CheckBox) findViewById(R.id.Geteilt);
        eins = (CheckBox) findViewById(R.id.Ein);
        zwei = (CheckBox) findViewById(R.id.Zwei);
        drei = (CheckBox) findViewById(R.id.Drei);
        txt = (TextView) findViewById(R.id.checkbox_view);
        user_t = (TextView) findViewById(R.id.user);
        user_t.setText(SaveSharedPreference.getUserID(getApplicationContext())); //Wieder wird der Username gesetzt auf das klar ist, wer eingeloggt ist


        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if (checkkat() == true && checkste() == true) { //Nur wenn eine Kategorie und eine Stelle ausgewählt ist, passiert etwas.

                    if (internetAvailable()) {

                        anforderungen = 11111111;

                        if (plus.isChecked() != true) {                     //Hier wird der Schlüssel der zum Kategorisieren der Aufgaben benutzt wird erstellt
                                                                            //Dieser Schlüssel ist 9 Stellig. Die erste Stelle ist immer eine 1 um einen festen Startpunkt zu haben
                                                                            //Die nächsten 4 Stellen geben die Rechenart an und die letzten 3 Stellen geben die Stellenanzahl der Aufgabe an
                                                                            //  Beispiel : 11001010 bedeutet, dass die Aufgabe entweder plus oder geteilt seind darf und das sie zweistellig ist
                            anforderungen = anforderungen - 1000000;
                        }
                        if (min.isChecked() != true) {
                            anforderungen = anforderungen - 100000;
                        }
                        if (mul.isChecked() != true) {
                            anforderungen = anforderungen - 10000;
                        }
                        if (div.isChecked() != true) {
                            anforderungen = anforderungen - 1000;
                        }
                        if (eins.isChecked() != true) {
                            anforderungen = anforderungen - 100;
                        }
                        if (zwei.isChecked() != true) {
                            anforderungen = anforderungen - 10;
                        }
                        if (drei.isChecked() != true) {
                            anforderungen = anforderungen - 1;
                        }


                        Intent myIntent = new Intent(v.getContext(), Aufgaben.class);
                        myIntent.putExtra("anforderungen", ("" + anforderungen)); //Hier wird beim aufrufen der neuen Activität ein Parameter mitgeben um diesen in der neuen Aktivität verfügbar zu machen
                        startActivity(myIntent);


                    }


                } else {
                    if (checkkat() == true) { //falls nicht mindestens eine Stelle und eine Rechenart angeklickt ist wird das hier abgefangen
                        txt.setText("Bitte wähle noch eine Anzahl von Stellen aus!");
                    } else if (checkste() == true) {
                        txt.setText("Bitte wähle noch eine Rechenart aus!");

                    }

                }

            }
        });


    }


    public boolean checkkat() { //Methode um zu checken ob mindestens eine Kategorie ausgewählt wurde


        return plus.isChecked() == true || min.isChecked() == true || mul.isChecked() == true || div.isChecked() == true;
    }

    public boolean checkste() { // methode um zu checken ob mindestens eine Stellen Anzahl ausgewählt wurde


        return eins.isChecked() == true || zwei.isChecked() == true || drei.isChecked() == true;
    }

    public boolean internetAvailable() {
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        return networkInfo != null && networkInfo.isConnectedOrConnecting();
    }
}
