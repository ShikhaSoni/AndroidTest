package com.apppartner.androidprogrammertest;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;


public class AnimationActivity extends ActionBarActivity implements View.OnTouchListener
{

    float x, y=0.0f;
    boolean moving =false;
    ImageView image;
    int count=0;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_animation);

        //getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        image=(ImageView)findViewById(R.id.imageView);
        image.setOnTouchListener(this);
        ImageButton btn=(ImageButton)findViewById(R.id.imageButton2);
        btn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                count++;
                //ImageView myView = (ImageView)splashDialog.findViewById(R.id.splashscreenImage);

                ObjectAnimator fadeOut = ObjectAnimator.ofFloat(image, "alpha",  1f, .3f);
                fadeOut.setDuration(2000);
                ObjectAnimator fadeIn = ObjectAnimator.ofFloat(image, "alpha", .3f, 1f);
                fadeIn.setDuration(2000);

                final AnimatorSet mAnimationSet = new AnimatorSet();

                mAnimationSet.play(fadeIn).after(fadeOut);

                mAnimationSet.addListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        super.onAnimationEnd(animation);
                        mAnimationSet.start();
                    }
                });
                if(count%2!=0){
                    mAnimationSet.start();
                }
                else{
                    mAnimationSet.end();
                }

            }
        });
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch(item.getItemId()){
            case android.R.id.home:
                Intent intent = new Intent(this,MainActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
        }
        return true;
    }

    @Override
    public boolean onTouch(View v, MotionEvent me){
        switch(me.getAction()){
            case MotionEvent.ACTION_DOWN:
                moving= true;
                break;
            case MotionEvent.ACTION_MOVE:
                if(moving){
                    x=me.getX();
                    y=me.getY();
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
