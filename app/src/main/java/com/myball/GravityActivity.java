package com.myball;

import android.app.Activity;
import android.content.Intent;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;

import org.rajawali3d.surface.RajawaliTextureView;

/**
 * Created by Ping_He on 2016/3/17.
 */
public class GravityActivity extends ActionBarActivity implements Orientation.Listener{
    // initial position
    public static int x=0;
    public static int y=0;
    public static int z=0;
    // the degree between  gravity and the vertical direction of the screen
    public static float pitchvalue =0;

    GravityRender renderer;
    public RajawaliTextureView rajawaliTexture;
    private Orientation mOrientation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        rajawaliTexture = (RajawaliTextureView) findViewById(R.id.rajawali_texture);
        renderer = new GravityRender(this);
        rajawaliTexture.setSurfaceRenderer(renderer);
        mOrientation = new Orientation((SensorManager) getSystemService(Activity.SENSOR_SERVICE),
                getWindow().getWindowManager());
    }

    @Override
    protected void onResume() {
        super.onResume();
        mOrientation.startListening(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        mOrientation.stopListening();
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle presses on the action bar items
        switch (item.getItemId()) {
            case R.id.reset:
                renderer.offsetx=0;
                renderer.offsety=0;
                break;
            case R.id.back:
                Intent i = new Intent(GravityActivity.this,AnimationActivity.class);
                startActivity(i);
                this.finish();
        }
        return true;
    }

    /* the direction of rotate and value
            case R.id.up:
                x= 1;
                y=0;
                z=0;
                renderer.setxyz(x,y,z);
                renderer.setOffsetxy(x, y);
                break;

            case R.id.down:
                x=-1;
                y=0;
                z=0;
                renderer.setxyz(x,y,z);
                renderer.setOffsetxy(x,y);
                break;

            case R.id.left:
                x=0;
                y=1;
                z=0;
                renderer.setxyz(x,y,z);
                renderer.setOffsetxy(x,y);
                break;

            case R.id.right:
                x=0;
                y=-1;
                z=0;
                renderer.setxyz(x,y,z);
                renderer.setOffsetxy(x,y);
                break;
            case R.id.upleft:
                x=1;
                y=1;
                z=0;
                renderer.setxyz(x,y,z);
                renderer.setOffsetxy(x, y);
                break;
            case R.id.downleft:
                x=-1;
                y=1;
                z=0;
                renderer.setxyz(x,y,z);
                renderer.setOffsetxy(x, y);
                break;
            case R.id.upright:
                x=1;
                y=-1;
                z=0;
                renderer.setxyz(x,y,z);
                renderer.setOffsetxy(x,y);
                break;
            case R.id.downright:
                x=-1;
                y=-1;
                z=0;
                renderer.setxyz(x,y,z);
                renderer.setOffsetxy(x,y);
                break;

    */
    @Override
    public void onOrientationChanged(float pitch, float roll) {

        pitchvalue=pitch;

        // convert gravity direction to the rolling direction
        if(roll>-22.5&&roll<22.5)
        {
            x=-1;
            y=0;
            z=0;
            renderer.setxyz(x,y,z);
            renderer.setOffsetxy(x,y);
        }else if(roll>=22.5&&roll<=67.5){
            x=-1;
            y=1;
            z=0;
            renderer.setxyz(x,y,z);
            renderer.setOffsetxy(x,y);
        }else if(roll>67.5&&roll<112.5){
            x=0;
            y=1;
            z=0;
            renderer.setxyz(x,y,z);
            renderer.setOffsetxy(x,y);
        }else if(roll>=112.5&&roll<=157.5){
            x=1;
            y=1;
            z=0;
            renderer.setxyz(x,y,z);
            renderer.setOffsetxy(x,y);
        }else if(roll>157.5||roll<-157.5){
            x= 1;
            y=0;
            z=0;
            renderer.setxyz(x,y,z);
            renderer.setOffsetxy(x, y);
        }else if(roll>=-67.5&&roll<=-22.5){
            x=-1;
            y=-1;
            z=0;
            renderer.setxyz(x,y,z);
            renderer.setOffsetxy(x, y);
        }else if(roll>-112.5&&roll<-67.5){
            x=0;
            y=-1;
            z=0;
            renderer.setxyz(x,y,z);
            renderer.setOffsetxy(x,y);
        }else if(roll>=-157.5&&roll<=-112.5){
            x=1;
            y=-1;
            z=0;
            renderer.setxyz(x,y,z);
            renderer.setOffsetxy(x,y);
        }
    }
}
