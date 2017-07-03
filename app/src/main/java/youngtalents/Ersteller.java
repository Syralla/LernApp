package youngtalents;

import android.app.Activity;
import android.content.Intent;
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
        txt = (TextView) findViewById(R.id.aufgabenpaket_view);




        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if(checkkat() == true && checkste() == true){ //Nur wenn eine Kategorie und eine Stelle ausgewählt ist, passiert etwas.

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
}
