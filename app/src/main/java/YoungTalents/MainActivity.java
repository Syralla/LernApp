package YoungTalents;


import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.ObjectOutputStream;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;

import java.io.StringReader;
import java.io.StringWriter;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.xml.sax.InputSource;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.w3c.dom.Node;
import org.w3c.dom.Element;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

import static android.content.Context.MODE_PRIVATE;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.json.JSONException;
import org.json.JSONObject;





public class MainActivity extends AppCompatActivity {

    EditText et;
    Button btn;
    TextView tv;
    int Art;
    String xml;
    TextView test;


    final String scripturlstring = "http://simon-f.com/receive_script.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        System.out.println("Point one");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tv = (TextView) findViewById(R.id.textView);
        test = (TextView) findViewById(R.id.test);
        btn = (Button) findViewById(R.id.button);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(internetAvailable()){

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

                    TestClient client = new TestClient();
                    client.sendAufgabe(builder);



                } else {
                    Toast.makeText(getApplicationContext(), "Internet ist nicht verfügbar.", Toast.LENGTH_SHORT).show();
                }
            }
        });
        System.out.println("Point two");
    }

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

                    try {
                        createTask(xml);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
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

    private static String convertDocumentToString(Document doc) {
        TransformerFactory tf = TransformerFactory.newInstance();
        Transformer transformer;
        try {
            transformer = tf.newTransformer();
            // below code to remove XML declaration
            // transformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "yes");
            StringWriter writer = new StringWriter();
            transformer.transform(new DOMSource(doc), new StreamResult(writer));
            String output = writer.getBuffer().toString();
            return output;
        } catch (TransformerException e) {
            e.printStackTrace();
        }

        return null;
    }

    private static Document convertStringToDocument(String xmlStr) {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder;
        try
        {
            builder = factory.newDocumentBuilder();
            Document doc = builder.parse( new InputSource( new StringReader( xmlStr ) ) );
            return doc;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }



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

    private Aufgabe[] generateAufgabe(boolean plus, boolean minus, boolean mal, boolean geteilt) {

        Aufgabe[] aufgaben = new Aufgabe[5];

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

        for(int i = 0; i < 5; i++) {

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


// ########################################Login###################################################################




}




