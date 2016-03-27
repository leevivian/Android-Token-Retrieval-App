// Vivian Lee
// 3/1/16

package com.example.vivian.vlcsc413hw1;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;

// new imports

import android.widget.Button;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {

    int lineNumber = 0;
    public static String tokenStr = "token";
    public static String msgStr = "message";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        final TextView tv = (TextView) findViewById(R.id.main_textview);
        Button btnLine = (Button) findViewById(R.id.btnLine);

        btnLine.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tv.setText("\r\nI am line number " + lineNumber + "!\r\n" + tv.getText());
                lineNumber++;
            }
        });

        // Button utilizing Singleton class to retrieve a token from given url
        Button btnToken = (Button) findViewById(R.id.btnToken);
        btnToken.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String url = "http://sfsuswe.com/413/get_token/?username=vlee1&password=913459409";

                // Requests a string response from the provided URL.
                JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.GET, url, null,
                        new Response.Listener<JSONObject>() {
                            @Override
                            public void onResponse(JSONObject response) {

                                try {
                                    String token = response.toString();
                                    JSONObject jsonObjectToken = new JSONObject(token);
                                    tokenStr = jsonObjectToken.getString("token");
                                    token = ("\r\nThe token is: " + tokenStr + "\r\n");
                                    tv.setText(token + tv.getText());

                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            }
                        }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        tv.setText("\r\nOopsies! That didn't work!\r\n" + tv.getText());
                    }
                });
                Singleton.getsInstance(getApplicationContext()).addToRequestQueue(jsonObjReq);
            }
        });

        Button btnMsg = (Button) findViewById(R.id.btnMsg);
        btnMsg.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                String urlMsg = "http://sfsuswe.com/413/get_message?token=" + tokenStr;

                JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.GET, urlMsg, null,
                        new Response.Listener<JSONObject>() {
                            @Override
                            public void onResponse(JSONObject response) {
                                try {
                                    String msgResponse = response.toString();
                                    JSONObject jsonObjectMsg = new JSONObject(msgResponse);
                                    msgStr = jsonObjectMsg.getString("message");
                                    msgResponse = ("\r\nThe secret message is: " + msgStr + "\r\n");

                                    tv.setText(msgResponse + tv.getText());
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            }
                        }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        tv.setText("\r\nWhoopsies! Please get a token first!\r\n" + tv.getText());
                    }
                });
                Singleton.getsInstance(getApplicationContext()).addToRequestQueue(jsonObjReq);
            }
        });

        // Clears the application's screen and resets all variables
        Button clearButton = (Button) findViewById(R.id.clearButton);
        clearButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tv.setText("");
                lineNumber = 0;
                tokenStr = "";
                msgStr = "";
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


}

