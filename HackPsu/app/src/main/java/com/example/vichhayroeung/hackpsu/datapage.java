package com.example.vichhayroeung.hackpsu;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

import org.java_websocket.client.WebSocketClient;

import de.tavendo.autobahn.WebSocketConnection;
import de.tavendo.autobahn.WebSocketException;
import de.tavendo.autobahn.WebSocketHandler;

public class datapage extends AppCompatActivity {
    private String TAG = datapage.class.getSimpleName();
    private ListView lv;
    ArrayList<HashMap<String, String>> contactList;

    private WebSocketClient mWebSocketClient;
    private final WebSocketConnection mConnection = new WebSocketConnection();
    public String link = "ws://tumesh@192.168.0.107:8080/";
    //public String link = "ws://tumesh@10.0.0.170:8080/";

    public String payloadData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_datapage);
    }

    public void backButton(View view) {
        Log.d("Self", "This is my messagePic");
        Intent intent = new Intent(datapage.this, MainActivity.class);
        startActivity(intent);
    }

    public void loadButton(View view) {
        Log.d("Self", "This is my message");
        connectWebSocket(link+"json");
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
                    payloadData = payload;
                }

                @Override
                public void onClose(int code, String reason) {
                    Log.d("onClose", "Connection lost.");
                }
            });
        } catch (WebSocketException e) {

            Log.d("catchWeb", e.toString());
        }


        contactList = new ArrayList<>();
        lv = (ListView) findViewById(R.id.list);
        new GetContacts().execute();
    }

    private class GetContacts extends AsyncTask<Void, Void, Void> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            Toast.makeText(datapage.this,"Json Data is downloading",Toast.LENGTH_LONG).show();
        }

        @Override
        protected Void doInBackground(Void... arg0) {
            //HttpHandler sh = new HttpHandler();
            // Making a request to url and getting response
            //String url = "http://api.androidhive.info/contacts/";
            //String jsonStr = sh.makeServiceCall(url);

        //        Log.d("Self", "Can't convert json string");


            //Log.e(TAG, "Response from url: " + jsonStr);
            if (payloadData != null) {
                try {
                    JSONObject jsonObj = new JSONObject(payloadData);
                    // Getting JSON Array node
                    JSONArray contacts = jsonObj.getJSONArray("contacts");

                    // looping through All Contacts
                    for (int i = 0; i < contacts.length(); i++) {
                        JSONObject c = contacts.getJSONObject(i);
                        String date = c.getString("date");
                        String time = c.getString("time");
                        String level = c.getString("level");

                        // tmp hash map for single contact
                        HashMap<String, String> contact = new HashMap<>();

                        // adding each child node to HashMap key => value
                        contact.put("date", date);
                        contact.put("time", time);
                        contact.put("level", level);

                        // adding contact to contact list
                        contactList.add(contact);
                    }
                } catch (final JSONException e) {
                    Log.e(TAG, "Json parsing error: " + e.getMessage());
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(getApplicationContext(),
                                    "Json parsing error: " + e.getMessage(),
                                    Toast.LENGTH_LONG).show();
                        }
                    });
                }

            } else {
                Log.e(TAG, "Couldn't get json from server.");
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(getApplicationContext(),
                                "Couldn't get json from server. Check LogCat for possible errors!",
                                Toast.LENGTH_LONG).show();
                    }
                });
            }

            return null;
        }


        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
            ListAdapter adapter = new SimpleAdapter(datapage.this, contactList,
                    R.layout.list_item, new String[]{ "date", "time", "level"},
                    new int[]{R.id.date, R.id.time, R.id.level});
            lv.setAdapter(adapter);
        }
    }
}
