package com.example.vichhayroeung.hackpsu;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import org.java_websocket.client.WebSocketClient;

import de.tavendo.autobahn.WebSocketConnection;
import de.tavendo.autobahn.WebSocketException;
import de.tavendo.autobahn.WebSocketHandler;

public class lights extends AppCompatActivity {

    private WebSocketClient mWebSocketClient;
    private final WebSocketConnection mConnection = new WebSocketConnection();
    public String link = "ws://tumesh@192.168.0.107:8080/";
    //public String link = "ws://tumesh@10.0.0.170:8080/";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lights);
    }
    public void onButton(View view) {
        Log.d("Self", "This is my message");
        connectWebSocket(link+"test");
    }

    public void offButton(View view) {
        Log.d("Self", "This is my message");
        connectWebSocket(link+"test2");
    }


    private void connectWebSocket(String url) {
        final String wsuri = url;
        try {
            mConnection.connect(wsuri, new WebSocketHandler() {

                @Override
                public void onOpen() {
                    Log.d("onOpen", "Status: Connected to " + wsuri);
                    mConnection.sendTextMessage("Hello, world!");
                }

                @Override
                public void onTextMessage(String payload) {
                    Log.d("onText", "Got echo: " + payload);
                }

                @Override
                public void onClose(int code, String reason) {
                    Log.d("onClose", "Connection lost.");
                }
            });
        } catch (WebSocketException e) {

            Log.d("catchWeb", e.toString());
        }
    }

    public void backButton(View view) {
        Log.d("Self", "This is my messagePic");
        Intent intent = new Intent(lights.this, MainActivity.class);
        startActivity(intent);
    }

}
