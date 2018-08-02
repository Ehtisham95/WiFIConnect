package app.robo.com.wificonnect;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.net.UnknownHostException;
import java.util.Date;
import java.util.Timer;


public class MainActivity extends AppCompatActivity {

    EditText sendtext;

    /**
     * Called when the activity is first created.
     */
    private Socket socket;

    private static final int SERVERPORT = 8888;
    private static final String SERVER_IP = "192.168.4.1";


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

            new Thread(new ClientThread()).start();



        Button buttonSend = findViewById(R.id.send);



        buttonSend.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {

                try {
                    sendtext = findViewById(R.id.editText);
                    String str = sendtext.getText().toString();

                    Toast.makeText(MainActivity.this, str, Toast.LENGTH_LONG).show();

                    PrintWriter out = new PrintWriter(new BufferedWriter(
                            new OutputStreamWriter(socket.getOutputStream())),
                            true);

                    out.println(str);


                    out.flush();

                    new Thread(new ReceiveThread()).start();



                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    class ReceiveThread implements Runnable{
        @Override
        public void run() {
            try{

                String read;

                BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));

                read = String.valueOf(in.readLine());

                TextView rectext = findViewById(R.id.text2);
                rectext.setText(String.valueOf(read));

                socket.close();
            }
            catch(Exception e){
            e.printStackTrace();
            }
        }
    }

    class ClientThread implements Runnable {

        public void run() {
            try

            {
                InetAddress serverAddr = InetAddress.getByName(SERVER_IP);
                socket = new Socket(serverAddr, SERVERPORT);


            } catch (
                    UnknownHostException e1)

            {
                e1.printStackTrace();
            } catch (
                    IOException e1)

            {
                e1.printStackTrace();
            }

        }
    }





}




  /*  Button.OnClickListener buttonSendOnClickListener
            = new Button.OnClickListener(){

        @Override
        public void onClick(View arg0) {
            // TODO Auto-generated method stub
            Socket socket = null;
            DataOutputStream dataOutputStream = null;
            DataInputStream dataInputStream = null;

            try {
                socket = new Socket("192.168.4.1", 8888);

                dataOutputStream = new DataOutputStream(socket.getOutputStream());
                dataInputStream = new DataInputStream(socket.getInputStream());

                dataOutputStream.writeUTF(sendtext.getText().toString());
                rectext.setText(dataInputStream.readUTF());
            } catch (UnknownHostException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            finally{
                if (socket != null){
                    try {
                        socket.close();
                    } catch (IOException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                }

                if (dataOutputStream != null){
                    try {
                        dataOutputStream.close();
                    } catch (IOException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                }

                if (dataInputStream != null){
                    try {
                        dataInputStream.close();
                    } catch (IOException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                }
            }
        }};*/

