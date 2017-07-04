package youngtalents;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.List;


public class TestClient {

    public static boolean ret = false;
    public static String tasks = "";
    public static String stat = "";

    private String serverIP = "simon-f.com";// Ip-Adresse des Servers
    private int port = 8080;    //Port des Servers - 8080 weil die Uni diesen Port nicht sperrt

    public TestClient() {
        // TODO Auto-generated constructor stub
    }

    /**
     * Verbindung zu Server aufbauen �ber (Server)Socket mit Port und IP
     *
     * @param action
     */
    public void connectToServer(NetworkAction action) {

        Socket so = null;
        PrintWriter pw = null;
        BufferedReader br = null;

        try {

            so = new Socket(serverIP, port);
            so.setSoTimeout(10000); //Timeouttimer wird hier hochgestellt, falls Antworten des Servers länger brauchen schließt sich der Socket nicht zu früh

            pw = new PrintWriter(so.getOutputStream()); //Definition des PrintWriters (Sendet Sachen zum Socketserver)
            br = new BufferedReader(new InputStreamReader(so.getInputStream())); //Definition des BufferedReaders (Empfängt sachen vom SocketServer)

            action.performAction(pw, br);

            pw.flush();

        } catch (IOException e) {
            e.printStackTrace();
        } finally {         //Am Ende werden hier alle offenen sachen geschlossen
            try {
                if (so != null) {
                    so.close();
                }

            } catch (IOException e) {
                // ignore
            }

            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                    //ignore
                }
            }

            if (pw != null) {
                pw.close();
            }


        }

    }

    /**
     * �ber Interface Aufgaben erstellen (action) und in String List abspeichern,
     * danach connectToServer --> L�cke schlie�en
     */



    public String getStatisticsFromServer(final String user) { //Methode um die Statistik Daten für einen User auszulesen


        Runnable r = new Runnable() {

            @Override
            public void run() {
                TestClient.stat = "";
                NetworkAction action = new NetworkAction() {

                    @Override
                    public void performAction(PrintWriter pw, BufferedReader br) throws IOException {
                        pw.println(4);//heist get Statistic
                        pw.flush();     //flush das es abgeschickt wird
                        pw.println(user); //der User wird zum Socketservergeschickt
                        pw.flush();

                        String in = br.readLine(); // Die Statistikdaten werden empfangen
                        TestClient.stat = in;


                    }
                };
                connectToServer(action);    // Interface in "L�cke" speichern
                System.out.println(TestClient.ret);


            }

        };

        Thread t = new Thread(r);
        t.start();
        while (t.isAlive()) { //Der thread wird so hier offengehalten auf das der Socketserver die Antwort schicken kann (falls er länger braucht)

        }

        return TestClient.stat;
    }


    public boolean Login(final List<String> line) throws IOException {

        System.out.println("Sende Login");


        Runnable r = new Runnable() {

            @Override
            public void run() {
                TestClient.ret = false;
                NetworkAction action = new NetworkAction() {

                    @Override
                    public void performAction(PrintWriter pw, BufferedReader br) throws IOException {
                        pw.println(2);//heist sende Login
                        pw.flush();
                        pw.println(line);
                        pw.flush();
                        String in = br.readLine();

                        if (in.contains("true") == true) {

                            TestClient.ret = true;



                        } else {

                        }


                    }
                };
                connectToServer(action);    // Interface in "L�cke" speichern



            }

        };

        Thread t = new Thread(r);
        t.start();
        while (t.isAlive()) {

        }

        return TestClient.ret;
    }


    public void Register(final List<String> line) {

        System.out.println("Sende Login");

        Runnable r = new Runnable() {

            @Override
            public void run() {
                NetworkAction action = new NetworkAction() {

                    @Override
                    public void performAction(PrintWriter pw, BufferedReader br) { //Diese Methode braucht keinen return vom SocketServer


                        pw.println(3);//heist sende Register
                        pw.flush();
                        pw.println(line);


                    }
                };

                connectToServer(action);    // Interface in "L�cke" speichern
            }
        };

        Thread t = new Thread(r);
        t.start();

    }

    public String getTask(final String spez) {
        System.out.println("Sende spez");

        Runnable r = new Runnable() {

            @Override
            public void run() {
                NetworkAction action = new NetworkAction() {

                    @Override
                    public void performAction(PrintWriter pw, BufferedReader br) throws IOException {


                        pw.println(0);//heist hole Aufgabe
                        pw.flush();
                        pw.println(spez);
                        pw.flush();


                        String in = br.readLine();

                        TestClient.tasks = in;


                    }
                };

                connectToServer(action);    // Interface in "L�cke" speichern
            }
        };

        Thread t = new Thread(r);
        t.start();
        while (t.isAlive()) {

        }


        return TestClient.tasks;
    }


    public void check(final String spez, final boolean check, final String user) {   //Methode um dem Server die Daten zuschicken das eine Aufgabe richtig/falsch beantwortet wurde
        System.out.println("Sende spez");
        final String xml = "<?xml version=\"1.0\" encoding=\"utf-8\"?>" + "<check><spez>" + spez + "</spez><c>" + check + "</c><user>" + user + "</user></check>"; //Die XML wird hier zusammengestellt und dann als String zu Server geschickt
        System.out.println(xml);

        Runnable r = new Runnable() {

            @Override
            public void run() {
                NetworkAction action = new NetworkAction() {

                    @Override
                    public void performAction(PrintWriter pw, BufferedReader br) throws IOException {


                        pw.println(1);//sende Ergebnisse
                        pw.flush();
                        pw.println(xml);
                        pw.flush();


                    }
                };

                connectToServer(action);    // Interface in "L�cke" speichern
            }
        };

        Thread t = new Thread(r);
        t.start();


    }
}
