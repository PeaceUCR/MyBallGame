package com.myball;

import android.content.Context;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.Toast;
import org.rajawali3d.Object3D;
import org.rajawali3d.animation.Animation3D;
import org.rajawali3d.animation.SplineTranslateAnimation3D;
import org.rajawali3d.bounds.IBoundingVolume;
import org.rajawali3d.curves.CompoundCurve3D;
import org.rajawali3d.curves.LinearBezierCurve3D;
import org.rajawali3d.lights.DirectionalLight;
import org.rajawali3d.materials.Material;
import org.rajawali3d.materials.methods.DiffuseMethod;
import org.rajawali3d.materials.textures.ATexture;
import org.rajawali3d.materials.textures.Texture;
import org.rajawali3d.math.vector.Vector3;
import org.rajawali3d.primitives.Sphere;
import org.rajawali3d.renderer.RajawaliRenderer;

/**
 * Created by Ping_He on 2016/3/16.
 */
public class AnimationRender extends RajawaliRenderer {

    private DirectionalLight mLight;
    public Context context;
    Object3D redSphere;
    Object3D yellowSphere;
    Animation3D redAnim, yAnim;
    // red ball is on the bottom, yellow ball is on the top
    //red rotation direction and speed
    float x=0;
    float y=0;
    float z=0;
    float r=4.5f;
    //ye rotaion direction and speed
    float x1=0;
    float y1=0;
    float z1=0;
    float r1 =3.5f;

    private boolean mSphereIntersect = false;

    String startdirection=null;
    String collisiondirection=null;

    public AnimationRender(Context context) {
        super(context);
        this.context = context;
        setFrameRate(60);
    }

    @Override
    protected void initScene() {
        mLight = new DirectionalLight(1f, .2f, -2.0f);
        mLight.setPower(2);
        getCurrentScene().addLight(mLight);
        getCurrentCamera().setPosition(0, 0, 10);

        redSphere = new Sphere(1, 24, 24);
        redSphere.setPosition(0, -3, 0);

        Material material = new Material();
        material.enableLighting(true);
        material.setDiffuseMethod(new DiffuseMethod.Lambert());
        material.setColor(0);
        Texture earthTexture = new Texture("earth", R.drawable.earthtruecolor_nasa_big);
        try{
            material.addTexture(earthTexture);
        } catch (ATexture.TextureException error){
            Log.d("DEBUG", "TEXTURE ERROR");
        }
        redSphere.setMaterial(material);
        getCurrentScene().addChild(redSphere);

        yellowSphere = new Sphere(1, 24, 24);
        yellowSphere.setPosition(0, 2, 0);

        Material material1 = new Material();
        material1.enableLighting(true);
        material1.setDiffuseMethod(new DiffuseMethod.Lambert());
        material1.setColor(0);
        Texture crinoTexture1 = new Texture("Crino", R.drawable.wuhe);
        try{
            material1.addTexture(crinoTexture1);
        } catch (ATexture.TextureException error){
            Log.d("DEBUG", "TEXTURE ERROR");
        }
        yellowSphere.setMaterial(material1);
        getCurrentScene().addChild(yellowSphere);
    }

    @Override
    public void onOffsetsChanged(float v, float v2, float v3, float v4, int i, int i2) {

    }

    @Override
    public void onTouchEvent(MotionEvent event) {

    }


    @Override
    protected void onRender(long ellapsedRealtime, double deltaTime) {
        super.onRender(ellapsedRealtime, deltaTime);
        redSphere.rotate(x, y, z, r);
        yellowSphere.rotate(x1,y1,z1,r1);

        IBoundingVolume bbox =redSphere.getGeometry().getBoundingSphere();;
        bbox.transform(redSphere.getModelMatrix());

        IBoundingVolume bbox2 = yellowSphere.getGeometry().getBoundingSphere();
        bbox2.transform(yellowSphere.getModelMatrix());

        mSphereIntersect = bbox.intersectsWith(bbox2);


        // when collistion happens dynamic change the animation
        if (mSphereIntersect)
        {
            getCurrentScene().unregisterAnimation(redAnim);
            x=0;
            y=0;
            z=0;
            if(collisiondirection!=null)
            {
                if(collisiondirection.equals("middle"))
                {
                    CompoundCurve3D yellowBezierPath= new CompoundCurve3D();
                    yellowBezierPath.addCurve(new LinearBezierCurve3D(
                            yellowSphere.getPosition(), new Vector3(0, 6,
                            0)));

                    yAnim = new SplineTranslateAnimation3D(yellowBezierPath);
                    yAnim.setDurationMilliseconds(80000);

                    yAnim.setTransformable3D(yellowSphere);
                    getCurrentScene().registerAnimation(yAnim);
                    yAnim.play();
                    x1=1;

                }else if(collisiondirection.equals("right")){
                    CompoundCurve3D yellowBezierPath= new CompoundCurve3D();
                    yellowBezierPath.addCurve(new LinearBezierCurve3D(
                            yellowSphere.getPosition(), new Vector3(-2, 6,
                            0)));



                    yAnim = new SplineTranslateAnimation3D(yellowBezierPath);
                    yAnim.setDurationMilliseconds(160000);

                    yAnim.setTransformable3D(yellowSphere);
                    getCurrentScene().registerAnimation(yAnim);
                    yAnim.play();
                    x1=1;
                    y1=0.5f;

                    CompoundCurve3D redBezierPath = new CompoundCurve3D();
                    redBezierPath.addCurve(new LinearBezierCurve3D(
                            redSphere.getPosition(), new Vector3(8, 3,
                            0)));

                    //CompoundCurve3D yellowBezierPath = new CompoundCurve3D();

                    redAnim = new SplineTranslateAnimation3D(redBezierPath);
                    redAnim.setDurationMilliseconds(200000);
                    //redAnim.setRepeatMode(Animation.RepeatMode.REVERSE_INFINITE);
                    redAnim.setTransformable3D(redSphere);
                    getCurrentScene().registerAnimation(redAnim);
                    redAnim.play();
                    x=0.1f;
                    y=-1;
                    r=2;
                    r1=3.0f;

                }else if(collisiondirection.equals("left")){
                    CompoundCurve3D yellowBezierPath= new CompoundCurve3D();
                    yellowBezierPath.addCurve(new LinearBezierCurve3D(
                            yellowSphere.getPosition(), new Vector3(2, 6,
                            0)));



                    yAnim = new SplineTranslateAnimation3D(yellowBezierPath);
                    yAnim.setDurationMilliseconds(160000);

                    yAnim.setTransformable3D(yellowSphere);
                    getCurrentScene().registerAnimation(yAnim);
                    yAnim.play();
                    x1=1;
                    y1=-0.5f;

                    CompoundCurve3D redBezierPath = new CompoundCurve3D();
                    redBezierPath.addCurve(new LinearBezierCurve3D(
                            redSphere.getPosition(), new Vector3(-8, 3,
                            0)));

                    redAnim = new SplineTranslateAnimation3D(redBezierPath);
                    redAnim.setDurationMilliseconds(200000);

                    redAnim.setTransformable3D(redSphere);
                    getCurrentScene().registerAnimation(redAnim);
                    redAnim.play();
                    x=0.1f;
                    y=1;
                    r=2;
                    r1=3.0f;

                }
            }

        }
        // start rolling at begins
        if(startdirection!=null)
        {
            if(startdirection.equals("middle"))
            {
                collisiondirection =startdirection;
                CompoundCurve3D redBezierPath = new CompoundCurve3D();
                redBezierPath.addCurve(new LinearBezierCurve3D(
                        new Vector3(0, -3, 0), new Vector3(0, 3,
                        0)));


                redAnim = new SplineTranslateAnimation3D(redBezierPath);
                redAnim.setDurationMilliseconds(3000);
                //redAnim.setRepeatMode(Animation.RepeatMode.REVERSE_INFINITE);
                redAnim.setTransformable3D(redSphere);
                getCurrentScene().registerAnimation(redAnim);
                redAnim.play();
                x=1;
                startdirection=null;
            }else if(startdirection.equals("right")){
                r=5.5f;
                collisiondirection =startdirection;
                CompoundCurve3D redBezierPath = new CompoundCurve3D();
                redBezierPath.addCurve(new LinearBezierCurve3D(
                        new Vector3(0, -3, 0), new Vector3(2, 3,
                        0)));

                redAnim = new SplineTranslateAnimation3D(redBezierPath);
                redAnim.setDurationMilliseconds(2000);
                //redAnim.setRepeatMode(Animation.RepeatMode.REVERSE_INFINITE);
                redAnim.setTransformable3D(redSphere);
                getCurrentScene().registerAnimation(redAnim);
                redAnim.play();
                x=1;
                y=-0.4f;
                startdirection=null;
            }else if(startdirection.equals("left")){
                r=5.5f;
                collisiondirection =startdirection;
                CompoundCurve3D redBezierPath = new CompoundCurve3D();
                redBezierPath.addCurve(new LinearBezierCurve3D(
                        new Vector3(0, -3, 0), new Vector3(-2, 3,
                        0)));

                //CompoundCurve3D yellowBezierPath = new CompoundCurve3D();

                redAnim = new SplineTranslateAnimation3D(redBezierPath);
                redAnim.setDurationMilliseconds(2000);
                //redAnim.setRepeatMode(Animation.RepeatMode.REVERSE_INFINITE);
                redAnim.setTransformable3D(redSphere);
                getCurrentScene().registerAnimation(redAnim);
                redAnim.play();
                x=1;
                y=0.4f;
                startdirection=null;
            }
        }

    }
    // set direction of rolling
    public void setdirection(String s)
    {
        startdirection = s;
        Toast.makeText(context,s,Toast.LENGTH_SHORT).show();
    }
    // reset the situation
    public void reset()
    {
        collisiondirection=null;
        startdirection=null;
        //red rotation
        x=0;
        y=0;
        z=0;
        r=4.5f;
        //ye rotaion
        x1=0;
        y1=0;
        z1=0;
        r1=3.5f;

        getCurrentScene().clearAnimations();

        yellowSphere.setPosition(0, 2, 0);
        redSphere.setPosition(0, -3, 0);
    }

}

