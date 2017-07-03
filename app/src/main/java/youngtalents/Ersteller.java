package youngtalents;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;

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

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);




        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {







            }
        });






    }
}
