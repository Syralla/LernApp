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

/**
 * Created by Simon on 01.07.2017.
 */

public class Ersteller extends Activity {

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
    String ret;

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





        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if(checkkat() == true && checkste() == true){ //Nur wenn eine Kategorie und eine Stelle ausgewählt ist, passiert etwas.

                    if(internetAvailable()){

                        anforderungen = 11111111;

                        if(plus.isChecked() != true){
                            anforderungen = anforderungen - 1000000;
                        }
                        if(min.isChecked() != true){
                            anforderungen = anforderungen - 100000;
                        }
                        if(mul.isChecked() != true){
                            anforderungen = anforderungen - 10000;
                        }
                        if(div.isChecked() != true){
                            anforderungen = anforderungen - 1000;
                        }
                        if(eins.isChecked() != true){
                            anforderungen = anforderungen - 100;
                        }
                        if(zwei.isChecked() != true){
                            anforderungen = anforderungen - 10;
                        }
                        if(drei.isChecked() != true){
                            anforderungen = anforderungen - 1;
                        }



                        TestClient client = new TestClient();

                        ret = client.sendAufgabe(anforderungen);

                        XMLParser parser = new XMLParser();







                    }


                }
                else{
                    if(checkkat() == true){
                        txt.setText("Bitte wähle noch eine Anzahl von Stellen aus!");
                    }
                    else if(checkste() == true){
                        txt.setText("Bitte wähle noch eine Rechenart aus!");

                    }

                }

            }
        });






    }


    public  boolean checkkat(){ //Methode um zu checken ob mindestens eine Kategorie ausgewählt wurde



        if(plus.isChecked() == true || min.isChecked() == true || mul.isChecked() == true || div.isChecked() == true){
            return true;
        }
        else{
            return false;
        }
    }

    public  boolean checkste(){ // methode um zu checken ob mindestens eine Stellen Anzahl ausgewählt wurde



        if(eins.isChecked() == true || zwei.isChecked() == true || drei.isChecked() == true ){
            return true;
        }
        else{
            return false;
        }
    }

    public boolean internetAvailable(){
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        return networkInfo != null && networkInfo.isConnectedOrConnecting();
    }
}
