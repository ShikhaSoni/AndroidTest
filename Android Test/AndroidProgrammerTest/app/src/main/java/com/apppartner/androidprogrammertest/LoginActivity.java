package com.apppartner.androidprogrammertest;

import android.app.ActionBar;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Typeface;
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
import org.apache.http.client.ResponseHandler;
import org.apache.http.impl.client.BasicResponseHandler;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import android.widget.TextView;
import android.widget.Toast;

public class LoginActivity extends ActionBarActivity
{
    ImageButton loginb;
    EditText passwordt;
    EditText usernamet;
    ProgressDialog dialog = null;
    HttpResponse response;
    HttpClient httpclient;
    HttpPost httppost;
    List<NameValuePair> nameValuePairs;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Typeface typ=Typeface.createFromAsset(getAssets(), "Jelloween - Machinato.ttf");
        EditText ed1=(EditText)findViewById(R.id.Username);
        ed1.setTypeface(typ);
        EditText ed2=(EditText)findViewById(R.id.Password);
        ed1.setTypeface(typ);
        loginb= (ImageButton)findViewById(R.id.imageButton);
        loginb.setOnClickListener(
                new View.OnClickListener() {
                    public void onClick(View view) {
                        System.out.println("Button pressed");
                        passwordt = (EditText) findViewById(R.id.Password);
                        usernamet = (EditText) findViewById(R.id.Username);
                        System.out.println(usernamet + " : " + passwordt);
                        dialog = ProgressDialog.show(LoginActivity.this, "",
                                "Validating user...", true);
                        new Thread(new Runnable() {
                            public void run() {
                                login();
                            }
                        }).start();
                    }
                });

      /*  ActionBar actionBar = getActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);*/
    }

    @Override
    public void onBackPressed()
    {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
    void login(){
        try{
            httpclient=new DefaultHttpClient();
            httppost= new HttpPost("http://dev.apppartner.com/AppPartnerProgrammerTest/scripts/login.php");
            nameValuePairs = new ArrayList<NameValuePair>(2);
            nameValuePairs.add(new BasicNameValuePair("username",usernamet.getText().toString().trim()));
            nameValuePairs.add(new BasicNameValuePair("password",passwordt.getText().toString().trim()));
            httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
            //Execute HTTP Post Request
            response=httpclient.execute(httppost);
            //extract the time taken for the api call

            //Response alert Dialogue
            ResponseHandler<String> responseHandler = new BasicResponseHandler();
            final String response = httpclient.execute(httppost, responseHandler);
            System.out.println("Response : " + response);

            if(response.equalsIgnoreCase("{\"code\":\"Success\",\"message\":\"Login Successful!\"}")){
                runOnUiThread(new Runnable() {
                    public void run() {
                        Toast.makeText(LoginActivity.this,"Login Success", Toast.LENGTH_SHORT).show();
                    }
                });

                startActivity(new Intent(LoginActivity.this, MainActivity.class));
            }else{
                showAlert();
            }

        }catch(Exception e){
            dialog.dismiss();
            System.out.println("Exception : " + e.getMessage());
        }
    }
    public void showAlert(){
        LoginActivity.this.runOnUiThread(new Runnable() {
            public void run() {
                AlertDialog.Builder builder = new AlertDialog.Builder(LoginActivity.this);
                builder.setTitle("Login Error.");
                builder.setMessage("User not Found.")
                        .setCancelable(false)
                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                            }
                        });
                AlertDialog alert = builder.create();
                alert.show();
            }
        });
    }
}
