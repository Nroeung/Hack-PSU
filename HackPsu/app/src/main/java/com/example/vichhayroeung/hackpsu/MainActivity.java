package com.example.vichhayroeung.hackpsu;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.app.Activity;
import android.app.Fragment;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import org.java_websocket.client.WebSocketClient;
import org.java_websocket.handshake.ServerHandshake;

import java.net.URI;
import java.net.URISyntaxException;

import de.tavendo.autobahn.WebSocketConnection;
import de.tavendo.autobahn.WebSocketException;
import de.tavendo.autobahn.WebSocketHandler;



public class MainActivity extends AppCompatActivity {
    private WebSocketClient mWebSocketClient;
    private final WebSocketConnection mConnection = new WebSocketConnection();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
    public void selfDestruct1(View view) {
        Log.d("Self", "This is my message");
        connectWebSocket();

    }
    private void connectWebSocket() {

        final String wsuri = "ws://tumesh@192.168.0.103:8080/text";

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

}
