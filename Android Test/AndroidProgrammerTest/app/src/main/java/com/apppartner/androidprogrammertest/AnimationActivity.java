package com.apppartner.androidprogrammertest;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;


public class AnimationActivity extends ActionBarActivity implements View.OnTouchListener
{

    float x, y=0.0f;
    boolean moving =false;
    ImageView image;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_animation);
        image=(ImageView)findViewById(R.id.imageView);
        image.setOnTouchListener(this);
        ImageButton btn=(ImageButton)findViewById(R.id.imageButton2);
        btn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {

            }
        });
    }

    @Override
    public boolean onTouch(View v, MotionEvent me){
        switch(me.getAction()){
            case MotionEvent.ACTION_DOWN:
                moving= true;
                break;
            case MotionEvent.ACTION_MOVE:
                if(moving){
                    x=me.getRawX()-v.getWidth();
                    y=me.getRawY()-v.getHeight();
                    v.setX(x);
                    v.setY(y);
                }
                break;
            case MotionEvent.ACTION_UP:
                moving = false;
                break;
        }
        return true;
    }
    @Override
    public void onBackPressed()
    {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}
