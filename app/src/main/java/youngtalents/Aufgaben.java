package youngtalents;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.parser.Parser;

public class Aufgaben extends Activity {

    public static int z1;
    public static int z2;
    public static String oper;
    public static String task_davor;
    public static String answer_davor;
    public static boolean task_davor_b;
    public String spez;
    TextView task;
    TextView user_t;
    TextView correct;
    TextView false_a;
    Button menu;
    Button check;
    EditText erg;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.task);
        spez = "" + getIntent().getStringExtra("anforderungen");
        user_t = (TextView) findViewById(R.id.user);
        user_t.setText(SaveSharedPreference.getUserID(getApplicationContext()));
        menu = (Button) findViewById(R.id.backtomenu);
        check = (Button) findViewById(R.id.button);
        task = (TextView) findViewById(R.id.task);
        System.out.println(spez);
        task.setText(firstTask(spez));
        erg = (EditText) findViewById(R.id.result);
        correct = (TextView) findViewById(R.id.correctanswer);
        false_a = (TextView) findViewById(R.id.falseanswer);


        check.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if (erg.getText().toString() != null) {

                    Aufgaben.task_davor = task.getText().toString();
                    Aufgaben.answer_davor = erg.getText().toString();
                    checkanswer(spez, SaveSharedPreference.getUserID(getApplicationContext()));
                    task.setText(nextTask(spez));
                    if (task_davor_b) { //wenn die Antwort davor korrekt war, wird es hier im grünen Text angezeigt
                        false_a.setText("");
                        correct.setText(task_davor + " =  " + answer_davor);

                    } else {
                        correct.setText("");
                        false_a.setText(task_davor + " =  " + answer_davor);

                    }
                    erg.setText("");
                }
            }
        });


        menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent myIntent = new Intent(v.getContext(), MainMenu.class);
                startActivityForResult(myIntent, 0);

            }
        });


    }

    public String firstTask(String spez) {

        TestClient c = new TestClient();

        String task = c.getTask(spez);

        String ret = "";

        Document doc = Jsoup.parse(task, "", Parser.xmlParser());
        System.out.println(doc.select("aufgabe").size());
        for (Element e : doc.select("aufgabe")) {
            int zahl1 = 0;
            int zahl2 = 0;
            String op = "";

            zahl1 = Integer.parseInt(e.select("zahl1").text());
            zahl2 = Integer.parseInt(e.select("zahl2").text());
            op = e.select("op").text();
            Aufgaben.z1 = zahl1;
            Aufgaben.z2 = zahl2;
            Aufgaben.oper = op;

            switch (op) {
                case "plus":
                    ret = zahl1 + "  +  " + zahl2;
                    break;
                case "minus":
                    ret = zahl1 + "  -  " + zahl2;
                    break;
                case "mult":
                    ret = zahl1 + "  x  " + zahl2;
                    break;
                case "div":
                    ret = zahl1 + "  /  " + zahl2;

                    break;
            }

        }

        return ret;

    }

    public String nextTask(String spez) {

        TestClient c = new TestClient();

        String task = c.getTask(spez);

        String ret = "";

        Document doc = Jsoup.parse(task, "", Parser.xmlParser());
        System.out.println(doc.select("aufgabe").size());
        for (Element e : doc.select("aufgabe")) {
            int zahl1 = 0;
            int zahl2 = 0;
            String op = "";

            zahl1 = Integer.parseInt(e.select("zahl1").text());
            zahl2 = Integer.parseInt(e.select("zahl2").text());
            op = e.select("op").text();
            Aufgaben.z1 = zahl1;
            Aufgaben.z2 = zahl2;
            Aufgaben.oper = op;

            switch (op) {
                case "plus":

                    ret = zahl1 + "  +  " + zahl2;
                    break;
                case "minus":
                    ret = zahl1 + "  -  " + zahl2;
                    break;
                case "mult":
                    ret = zahl1 + "  x  " + zahl2;
                    break;
                case "div":
                    ret = zahl1 + "  /  " + zahl2;

                    break;
            }
        }


        return ret;
    }

    public void checkanswer(String spez, String user) {
        boolean ret = false;
        int er;
        String err;

        switch (oper) {
            case "plus":
                er = Aufgaben.z1 + Aufgaben.z2;
                spez = "11000000";

                System.out.println(erg.getText().toString());
                if ((er + "").equals(erg.getText().toString())) {
                    ret = true;
                }
                break;
            case "minus":
                er = Aufgaben.z1 - Aufgaben.z2;
                spez = "10100000";
                if ((er + "").equals(erg.getText().toString())) {
                    ret = true;
                }
                break;
            case "mult":
                er = Aufgaben.z1 * Aufgaben.z2;
                spez = "10010000";
                if ((er + "").equals(erg.getText().toString())) {
                    ret = true;
                }
                break;
            case "div":
                er = Aufgaben.z1 / Aufgaben.z2;
                spez = "10001000";
                if ((er + "").equals(erg.getText().toString())) {
                    ret = true;
                }
                break;

        }
        Aufgaben.task_davor_b = ret;
        System.out.println(ret);
        TestClient c = new TestClient();

        c.check(spez, ret, user);


    }


}
