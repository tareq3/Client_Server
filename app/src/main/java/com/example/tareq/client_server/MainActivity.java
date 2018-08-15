package com.example.tareq.client_server;

import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;

public class MainActivity extends AppCompatActivity {
    EditText editText;
    private static final String hostname = "192.168.0.103";
    private static final int portnumber = 8275;
    public static final String debugString = "debug";

    private Socket socket = null;
    Button b1, b2;
    EditText editText2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        b2 = (Button) findViewById(R.id.button2);
        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        me();
                    }
                }).start();

            }
        });
        b1 = (Button) findViewById(R.id.button);
        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendMessage(v);
                editText2 = (EditText) findViewById(R.id.editText2);
                editText2.setText(null);
            }
        });
        new Thread(new Runnable() {
            @Override
            public void run() {


                //RecieveMessage();

            }
        }).start();

    }


    void me() {

        editText = (EditText) findViewById(R.id.editText);
        String host = editText.getText().toString();
        try {
            Log.i(debugString, " Attempting to Connect");
            socket = new Socket(host, portnumber);
            Log.i(debugString, "Connected");

            MainActivity.this.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Toast.makeText(MainActivity.this, "Connected", Toast.LENGTH_SHORT).show();

                }
            });


            final BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
            bufferedWriter.write("this is message from client");
            bufferedWriter.newLine();
            bufferedWriter.flush();


         final   BufferedReader   bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));

           String a;
            while ((a=bufferedReader.readLine())!=null)
            Log.i(debugString," hello"+a);
        } catch (IOException e) {

        }
    }

    void sendMessage(View view) {
        editText2 = (EditText) findViewById(R.id.editText2);

        BufferedWriter bufferedWriter = null;
        try {
            bufferedWriter = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
            bufferedWriter.write(editText2.getText().toString());
            bufferedWriter.newLine();
            bufferedWriter.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    void RecieveMessage() {



          //  final BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));

            MainActivity.this.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    editText2= (EditText) findViewById(R.id.editText2);
                    try {
                        editText2.setText("from Server");
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });

            }
}