package com.emotilog.app.emotilog;


        import android.content.Intent;
        import android.hardware.Sensor;
        import android.hardware.SensorEvent;
        import android.hardware.SensorEventListener;
        import android.hardware.SensorManager;
        import android.os.Bundle;
        import android.support.v7.app.AppCompatActivity;
        import android.util.Log;
        import android.view.View;
        import android.widget.Button;
        import android.widget.TextView;

/**
 * Created by Chen on 2017/11/20.
 * this activity is for using ACCELEROMETER SENSOR and return the score to daily entry
 */

public class Shaking extends AppCompatActivity {
    TextView st = null;
    Button reset = null;
    Button returnscore = null;
    SensorManager sensorManager = null;
    int highestscore=0;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.shaking);

        reset = (Button) findViewById(R.id.reset);
        st = (TextView) findViewById(R.id.shake_text);
        returnscore = (Button)findViewById(R.id.returntoentry);


        sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);

    }

    @Override
    protected void onPause() {//when not on this activity, stop listening
        super.onPause();
        sensorManager.unregisterListener(sensorEventListener);
    }

    @Override
    protected void onResume() {//back on this activity, start listening
        super.onResume();
        sensorManager.registerListener(sensorEventListener,
                sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER),
                SensorManager.SENSOR_DELAY_NORMAL);
    }

    public void resetonclick(View v){//click reset button, reset text view and hide the buttons
        st.setText("Shake again!");
        reset.setVisibility(View.INVISIBLE);
        returnscore.setVisibility(View.INVISIBLE);
        highestscore=0;
    }

    public void returnonclick(View v){//click return button, return the score
        Intent intent = new Intent();
        intent.putExtra("result", highestscore);
        setResult(RESULT_OK, intent);
        finish();
    }

    private SensorEventListener sensorEventListener = new SensorEventListener() {


        @Override
        public void onAccuracyChanged(Sensor sensor, int accuracy) {

        }

        @Override
        public void onSensorChanged(SensorEvent event) {//record the score if it's big enough

            int sensorType = event.sensor.getType();
            //values[0]:X，values[1]：Y，values[2]：Z
            float[] values = event.values;
            if (sensorType == Sensor.TYPE_ACCELEROMETER) {
                if ((Math.abs(values[0]) > 14 || Math.abs(values[1]) > 14 || Math
                        .abs(values[2]) > 14)) {
                    int score=(int)Math.sqrt(Math.pow(values[0],2)+Math.pow(values[1],2)+Math.pow(values[2],2));
                    if(score>highestscore){
                        highestscore=score;
                    }
                    st.setText("Your score is "+highestscore+" !");
                    reset.setVisibility(View.VISIBLE);
                    returnscore.setVisibility(View.VISIBLE);
                }

            }
        }
    };


}