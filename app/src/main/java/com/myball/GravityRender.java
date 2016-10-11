package com.myball;

import android.content.Context;
import android.util.Log;
import android.view.MotionEvent;

import org.rajawali3d.lights.DirectionalLight;
import org.rajawali3d.materials.Material;
import org.rajawali3d.materials.methods.DiffuseMethod;
import org.rajawali3d.materials.textures.ATexture;
import org.rajawali3d.materials.textures.Texture;
import org.rajawali3d.primitives.Sphere;
import org.rajawali3d.renderer.RajawaliRenderer;

/**
 * Created by Ping_He on 2016/3/16.
 */
public class GravityRender extends RajawaliRenderer {
    // the direction parameters of moving
    int x=0;
    int y=0;
    int z=0;
    // the location parameters of ball
    public float offsetx=0f;
    public float offsety=0f;

    // the speed changes
    float changex;
    float changey;
    // to flag of whether start moving
    public static volatile boolean flag = false;

    public Context context;

    private DirectionalLight directionalLight;
    private Sphere earthSphere;



    public GravityRender(Context context) {
        super(context);
        this.context = context;
        setFrameRate(60);

    }

    public void initScene(){

        directionalLight = new DirectionalLight(1f, .2f, -2.0f);
        directionalLight.setColor(1.0f, 1.0f, 1.0f);
        directionalLight.setPower(2);
        getCurrentScene().addLight(directionalLight);

        Material material = new Material();
        material.enableLighting(true);
        material.setDiffuseMethod(new DiffuseMethod.Lambert());
        material.setColor(0);


        Texture earthTexture = new Texture("Moon", R.drawable.earthtruecolor_nasa_big);
        try{
            material.addTexture(earthTexture);
        } catch (ATexture.TextureException error){
            Log.d("DEBUG", "TEXTURE ERROR");
        }

        earthSphere = new Sphere(0.6f, 24, 24);
        earthSphere.setMaterial(material);
        getCurrentScene().addChild(earthSphere);
        getCurrentCamera().setZ(4.2f);

        //use camera to show the position change!

    }

    // this method will be running repeatedly when the program is running
    @Override
    public void onRender(final long elapsedTime, final double deltaTime) {
        super.onRender(elapsedTime, deltaTime);

        if(GravityActivity.pitchvalue>-80){
            //updated the rotating
            earthSphere.rotate(x, y, z, 2.0);
        }
        // set the updated postion
        getCurrentCamera().setX(offsetx);
        getCurrentCamera().setY(offsety);
    }
    // set the rotate direction
    public void setxyz(int x,int y,int z){
        this.x=x;
        this.y=y;
        this.z=z;
    }

    public void onTouchEvent(MotionEvent event){
    }

    public void onOffsetsChanged(float x, float y, float z, float w, int i, int j){
    }

    // set the rolling direction
    public  void setOffsetxy(int x1, int y1)
    {

        flag=false;

        changex=y1;
        changey=x1;

        flag=true;


        if(String.valueOf(t.getState()).equals("NEW")){
            t.start();
        }

    }
    // using the thread to update the position when rolling
    Thread t =new Thread(){
        public void run(){
            while (flag) {

                if(GravityActivity.pitchvalue>-80)
                {
                    offsetx=offsetx+changex/250;
                    offsety=offsety-changey/250;
                }

                try{
                    Thread.sleep(5);
                }catch (Exception e){
                    e.printStackTrace();
                }

            }
        }
    };
}

