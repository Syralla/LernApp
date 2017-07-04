package youngtalents;

import android.app.Activity;
import android.os.Bundle;

/**
 * Created by Simon on 01.07.2017.
 */


public class Aufgaben extends Activity {

    public String spez;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.task);
        spez = getIntent().getStringExtra("anforderungen");






    }


}
