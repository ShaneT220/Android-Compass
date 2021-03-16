package com.example.compassapp;

import android.app.Activity;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;
import android.widget.TextView;

import android.os.Bundle;

public class MainActivity extends Activity implements SensorEventListener {
    //Compass picture
    private ImageView image;

    //Record the compass picture angle turned
    private float currentDegree = 0f;

    //device sensor manager
    private SensorManager mSensorManager;

    TextView tvHeading;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Compass image
        image = (ImageView) findViewById(R.id.imageViewCompass);

        //TextView that will tell the user what degree there at
        tvHeading = (TextView) findViewById(R.id.tvHeading);

        //initialize you android device sensor capabilities
        mSensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
    }

    protected void onResume(){
        super.onResume();

        //for the system orientation sensor registered listeners
        mSensorManager.registerListener(this, mSensorManager.getDefaultSensor(Sensor.TYPE_ORIENTATION),
                SensorManager.SENSOR_DELAY_GAME);
    }

    protected void onPause(){
        super.onPause();

        //to stop the listener and save battery
        mSensorManager.unregisterListener(this);
    }


    @Override
    public void onSensorChanged(SensorEvent event) {
        //get the angle around the z-axis rotated
        float degree = Math.round(event.values[0]);

        tvHeading.setText("Heading " + Float.toString(degree) + " degrees");

        //Create a rotation animation (reverse turn degree degrees)
        RotateAnimation ra = new RotateAnimation(
                currentDegree,
                -degree,
                Animation.RELATIVE_TO_SELF, 0.5f,
                Animation.RELATIVE_TO_SELF, 0.5f);

        //how long the animation will take place
        ra.setDuration(210);

        //set the animation after the end of the resevation status
        ra.setFillAfter(true);

        //Start the animation
        image.startAnimation(ra);
        currentDegree = -degree;
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }
}