package com.myball;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.GestureDetector;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;

import org.rajawali3d.surface.RajawaliTextureView;

/**
 * Created by Ping_He on 2016/3/16.
 */
public class AnimationActivity extends ActionBarActivity {

    AnimationRender renderer;
    public RajawaliTextureView rajawaliTexture;
    private GestureDetector gestureDetector;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_animation);

        rajawaliTexture = (RajawaliTextureView) findViewById(R.id.rajawali_texture1);
        renderer = new AnimationRender(this);
        rajawaliTexture.setSurfaceRenderer(renderer);
        gestureDetector = new GestureDetector(this,onGestureListener);


    }
    // detect the gesture direction
    GestureDetector.OnGestureListener onGestureListener =
            new GestureDetector.SimpleOnGestureListener() {
                @Override
                public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX,
                                       float velocityY) {
                    float x = e2.getX() - e1.getX();
                    float y = e2.getY() - e1.getY();

                    Log.e("SSS",""+x);
                    if (x >80) {
                        renderer.setdirection("right");
                    } else if (x < -80) {
                        renderer.setdirection("left");
                    }else{
                        renderer.setdirection("middle");
                    }
                    return true;
                }
            };

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle presses on the action bar items
        switch (item.getItemId()) {
            case R.id.reset:
                renderer.reset();
                break;
            case R.id.back:
                Intent i = new Intent(AnimationActivity.this,GravityActivity.class);
                startActivity(i);
                this.finish();
        }
        return true;
    }

    @Override
    public boolean onTouchEvent(MotionEvent e) {
        // MotionEvent reports input details from the touch screen
        // and other input controls. In this case, we are only
        // interested in events where the touch position changed.
        return gestureDetector.onTouchEvent(e);


    }



}
