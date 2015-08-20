package com.apppartner.androidprogrammertest.adapters;
import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Bitmap;

import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.apppartner.androidprogrammertest.R;
import com.apppartner.androidprogrammertest.models.ChatData;
import com.apppartner.androidprogrammertest.util.CircleTransform;
import com.squareup.picasso.Picasso;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;;

import java.util.List;

import android.graphics.Canvas;

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

        if( convertView == null ) {
            convertView = inflater.inflate(R.layout.cell_chat, parent, false);
        }
        chatCell.usernameTextView = (TextView) convertView.findViewById(R.id.usernameTextView);
        chatCell.messageTextView = (TextView) convertView.findViewById(R.id.messageTextView);
        chatCell.displayPicture=(ImageView)convertView.findViewById(R.id.imageView2);
        ChatData chatData = getItem(position);

        chatCell.usernameTextView.setText(chatData.username);
        chatCell.messageTextView.setText(chatData.message);
        Picasso.with(getContext()).load(chatData.avatarURL).transform(new CircleTransform()).into(chatCell.displayPicture);

        TextView userNameTextView = (TextView)convertView.findViewById(R.id.usernameTextView);
        TextView messageTextView = (TextView)convertView.findViewById(R.id.messageTextView);

        userNameTextView.setTypeface(Typeface.createFromAsset(getContext().getAssets(),"Jelloween - Machinato.ttf"));
        messageTextView.setTypeface(Typeface.createFromAsset(getContext().getAssets(),"Jelloween - Machinato Light.ttf"));


        return convertView;

    }
    private static class ChatCell
    {
        TextView usernameTextView;
        TextView messageTextView;
        ImageView displayPicture;
    }
}

