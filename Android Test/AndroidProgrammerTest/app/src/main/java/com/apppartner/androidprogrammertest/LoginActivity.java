package com.apppartner.androidprogrammertest;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class LoginActivity extends ActionBarActivity
{
    String username;
    String password;
    ImageButton loginb;
    EditText passwordt;
    EditText usernamet;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
    }

    @Override
    public void onBackPressed()
    {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
    public void onLoginButtonPress(){
        loginb= (ImageButton)findViewById(R.id.imageButton);
        passwordt=(EditText)findViewById(R.id.Password);
        usernamet=(EditText)findViewById(R.id.Username);
        loginb.setOnClickListener(
                new View.OnClickListener()
                {
                    public void onClick(View view)
                    {
                        password= passwordt.getText().toString();
                        username= usernamet.getText().toString();
                    }
                    public void postData() {
                        // Create a new HttpClient and Post Header
                        HttpClient httpclient = new DefaultHttpClient();
                        HttpPost httppost = new HttpPost("http://dev.apppartner.com/AppPartnerProgrammerTest/scripts/login.php");

                        try {
                            // Add your data
                            List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
                            nameValuePairs.add(new BasicNameValuePair(username,password));

                            httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));

                            // Execute HTTP Post Request
                            HttpResponse response = httpclient.execute(httppost);

                        } catch (ClientProtocolException e) {
                            // TODO Auto-generated catch block
                        } catch (IOException e) {
                            // TODO Auto-generated catch block
                        }
                    }
                });

    }

}
