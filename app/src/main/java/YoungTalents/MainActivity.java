package youngtalents;

/*
Todo:

- Vom CLient zum Server schicken das er eine Aufgabe will
- Aufgaben bearbeiten mit neuzuführ vom Server

- Statistik an den Server schicken (Kategorieschlüssel + richtig/falsch)
- Statistisk in der Datenbank Benutzerspezifisch abspeichern
- Statistik vom Server an den Client schicken und darstellen




 */


import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import android.content.Intent;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends AppCompatActivity {

    EditText user;
    EditText pasw;
    Button btn;
    Button reg;
    CheckBox logged;
    TextView fail;

    String xml;



    final String scripturlstring = "http://simon-f.com/receive_script.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_layout);

        if(SaveSharedPreference.getUserName(MainActivity.this).length() == 0)
        {

        }
        else
        {
            Intent myIntent = new Intent(getApplicationContext(), MainMenu.class);
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
            public void onClick(View v) {
                if(internetAvailable()){
                    System.out.println("Clicked");
                /*

                    CheckBox plusBox = (CheckBox) findViewById(R.id.PPlus);
                    CheckBox minusBox = (CheckBox) findViewById(R.id.Minus);
                    CheckBox malBox = (CheckBox) findViewById(R.id.Mal);
                    CheckBox geteiltBox = (CheckBox) findViewById(R.id.Geteilt);
                    CheckBox einsBox = (CheckBox) findViewById(R.id.Ein);
                    CheckBox zweiBox = (CheckBox) findViewById(R.id.Zwei);
                    CheckBox dreiBox = (CheckBox) findViewById(R.id.Drei);

                    boolean plus = plusBox.isChecked();
                    boolean minus = minusBox.isChecked();
                    boolean geteilt = geteiltBox.isChecked();
                    boolean mal = malBox.isChecked();


                    Aufgabe[] aufgaben = generateAufgabe(plus,minus,mal,geteilt);

                    FileBuilder builder = new FileBuilder();

                    for(Aufgabe aufgabe : aufgaben) {
                        builder.addAufgabe(aufgabe);

                    }
                    */
                    System.out.println(user.getText().toString());

                    FileBuilder builder = new FileBuilder();


                    TestClient client = new TestClient();
                    //client.sendAufgabe(builder);
                    try {
                       if(client.Login(builder.buildLoginAsStringArray(user.getText().toString(), pasw.getText().toString())) == true){
                           if(logged.isChecked()) {


                               SaveSharedPreference.setUserName(getApplicationContext(), user.getText().toString()); //User abspeichern für logged in

                           }
                           SaveSharedPreference.setUserID(getApplicationContext(), user.getText().toString()); //Username abspeichern für anzeige
                           Intent myIntent = new Intent(v.getContext(), MainMenu.class);
                           startActivityForResult(myIntent, 0);

                      }
                       else{


                           fail.setText("Dein Benutzername und dein Passwort passen nicht zusammen. Hast du dich vielleicht vertippt?");



                       }
                    } catch (IOException e) {
                        e.printStackTrace();

                    }


                } else {

                }
            }
        });


                reg.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent myIntent = new Intent(v.getContext(), Register.class);
                        startActivityForResult(myIntent, 0);
                    }
                });



        System.out.println("Point two");


    }
/*
    public void sendToServer(final String text){

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    System.out.println("Button pressed");

                    String textparam = "text1=" + URLEncoder.encode(text, "UTF-8");

                    URL scripturl = new URL(scripturlstring);
                    HttpURLConnection connection = (HttpURLConnection) scripturl.openConnection();
                    connection.setDoOutput(true);
                    connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
                    connection.setFixedLengthStreamingMode(textparam.getBytes().length);

                    OutputStreamWriter contentWriter = new OutputStreamWriter(connection.getOutputStream());
                    contentWriter.write(textparam);
                    contentWriter.flush();
                    contentWriter.close();

                    InputStream answerInputStream = connection.getInputStream();
                    final String answer = getTextFromInputStream(answerInputStream);
                    xml = answer;
                    final String aa = "aaaa";
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            tv.setText(aa);
                        }
                    });


                    answerInputStream.close();
                    connection.disconnect();

                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }
    */
    public String getTextFromInputStream(InputStream is){
        BufferedReader reader = new BufferedReader(new InputStreamReader(is));
        StringBuilder stringBuilder = new StringBuilder();

        String aktuelleZeile;
        try {
            while ((aktuelleZeile = reader.readLine()) != null){
                stringBuilder.append(aktuelleZeile);
                stringBuilder.append("\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return stringBuilder.toString().trim();
    }

    public boolean internetAvailable(){
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        return networkInfo != null && networkInfo.isConnectedOrConnecting();
    }







    //<editor-fold desc="Description">
    /*
    public void createTask(String xml) throws IOException {

        String FILENAME = "Mappe1";


            FileOutputStream fos = null;

            fos = openFileOutput(FILENAME, MODE_PRIVATE);

            fos.write(xml.getBytes());

            fos.close();


        FileInputStream fis = null;

        fis = openFileInput(FILENAME);


        BufferedReader reader = new BufferedReader(new InputStreamReader(fis));
        final StringBuilder out = new StringBuilder();
        String line;
        while ((line = reader.readLine()) != null) {
            out.append(line);   // add everything to StringBuilder
            // here you can have your logic of comparison.
            if(line.toString().equals("")) {
                // do something
            }

        }


        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                test.setText(out);
            }
        });

        }
    //</editor-fold>


    private Aufgabe[] generateAufgabe(boolean plus, boolean minus, boolean mal, boolean geteilt) {

        Aufgabe[] aufgaben = new Aufgabe[20];

        List<Operations> ops = new ArrayList<Operations>();

        if(plus) {
            ops.add(Operations.ADD);
        }

        if(minus) {
            ops.add(Operations.SUB);
        }

        if(mal) {
            ops.add(Operations.MUL);
        }

        if(geteilt) {
            ops.add(Operations.DIV);
        }

        for(int i = 0; i < 20; i++) {

            int op = ( (int) (Math.random() * ops.size()));

            Operations finalOp = ops.get(op);

            aufgaben[i] = new Aufgabe((int)(Math.random() * 100),(int)(Math.random() * 100), finalOp);

        }


        return aufgaben;
    }







        /*
        try {


            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();


            //optional, but recommended
            //read this - http://stackoverflow.com/questions/13786607/normalization-in-dom-parsing-with-java-how-does-it-work
            xml.getDocumentElement().normalize();



            NodeList nList = xml.getElementsByTagName("aufgabe");



            for (int temp = 0; temp < nList.getLength(); temp++) {

                Node nNode = nList.item(temp);

                if (nNode.getNodeType() == Node.ELEMENT_NODE) {

                    Element eElement = (Element) nNode;




                    System.out.println("Staff id : " + eElement.getAttribute("id"));
                    System.out.println("First Name : " + eElement.getElementsByTagName("firstname").item(0).getTextContent());
                    System.out.println("Last Name : " + eElement.getElementsByTagName("lastname").item(0).getTextContent());
                    System.out.println("Nick Name : " + eElement.getElementsByTagName("nickname").item(0).getTextContent());
                    System.out.println("Salary : " + eElement.getElementsByTagName("salary").item(0).getTextContent());

                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    */


//#################################### popup login fail #########################################











}




