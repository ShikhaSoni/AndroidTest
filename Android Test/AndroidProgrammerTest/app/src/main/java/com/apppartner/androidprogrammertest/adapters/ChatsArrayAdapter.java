package com.apppartner.androidprogrammertest.adapters;

import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.apppartner.androidprogrammertest.R;
import com.apppartner.androidprogrammertest.models.ChatData;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.BufferedHttpEntity;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

/**
 * Created on 12/23/14.
 *
 * @author Thomas Colligan
 */
public class ChatsArrayAdapter extends ArrayAdapter<ChatData>
{
    HttpGet httpget;
    HttpClient httpClient;
    HttpResponse httpResponse;
    private ImageView downloadedImg;
    private ProgressDialog simpleWaitDialog;
    Bitmap bmp;

    public ChatsArrayAdapter(Context context, List<ChatData> objects)
    {
        super(context, 0, objects);
    }

    public View getView(int position, View convertView, ViewGroup parent)
    {
        ChatCell chatCell = new ChatCell();
        LayoutInflater inflater = LayoutInflater.from(getContext());
        convertView = inflater.inflate(R.layout.cell_chat, parent, false);

        chatCell.usernameTextView = (TextView) convertView.findViewById(R.id.usernameTextView);
        chatCell.messageTextView = (TextView) convertView.findViewById(R.id.messageTextView);
        chatCell.displayPicture=(ImageView)convertView.findViewById(R.id.imageView2);
        ChatData chatData = getItem(position);

        chatCell.usernameTextView.setText(chatData.username);
        chatCell.messageTextView.setText(chatData.message);
        chatCell.displayPicture.setImageBitmap(getImage(chatData.avatarURL));

        return convertView;
    }
    public Bitmap getImage(String url){
            httpClient= new DefaultHttpClient();
            httpget=new HttpGet(url);
            System.out.println(url);
            new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try{
                            httpResponse=httpClient.execute(httpget);
                            HttpEntity responseEntity = httpResponse.getEntity();
                            BufferedHttpEntity httpEntity = null;
                            try {
                                httpEntity = new BufferedHttpEntity(responseEntity);
                            } catch (IOException e1) {
                                // TODO Auto-generated catch block
                                System.out.println("The error occured at place 2");
                                e1.printStackTrace();
                            }
                            InputStream imageStream = null;
                            try {
                                imageStream = httpEntity.getContent();
                            } catch (IOException e) {
                                // TODO Auto-generated catch block
                                System.out.println("The error occured at place 3");
                                e.printStackTrace();
                            }
                            bmp = BitmapFactory.decodeStream(imageStream);
                        }
                        catch(Exception e){
                            System.out.println(e);
                            System.out.println("The error occured at place 1");
                            //alert box of image not loading
                        }
                    }
                }).start();
            return bmp;
        }
    private static class ChatCell
    {
        TextView usernameTextView;
        TextView messageTextView;
        ImageView displayPicture;
    }
    }

