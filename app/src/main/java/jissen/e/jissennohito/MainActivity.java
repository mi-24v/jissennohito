package jissen.e.jissennohito;

import android.app.Activity;
import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.widget.TextView;

public class MainActivity extends Activity {
    private SensorManager mSensorManager;
    private Sensor mAccelerometer,mMagneticField;
    private SensorEventListener mSensorListener;

    private float[] rotationMatrix = new float[9];
    private float[] gravity = new float[3];
    private float[] geomagnetic = new float[3];
    private float[] attitude = new float[3];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mSensorManager = (SensorManager)getSystemService(Context.SENSOR_SERVICE);
        mAccelerometer = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        mMagneticField = mSensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD);
        mSensorListener = new SensorEventListener() {
            @Override
            public void onSensorChanged(SensorEvent event) {
                switch (event.sensor.getType()){
                    case Sensor.TYPE_ACCELEROMETER:
                        geomagnetic = event.values.clone();
                        break;
                    case Sensor.TYPE_MAGNETIC_FIELD:
                        gravity = event.values.clone();
                        break;
                }
                if (geomagnetic != null && gravity != null) {
                    SensorManager.getRotationMatrix(rotationMatrix, null, gravity, geomagnetic);
                    SensorManager.getOrientation(rotationMatrix, attitude);

                    TextView x_axis = (TextView)findViewById(R.id.x_axis_text),
                            y_axis = (TextView)findViewById(R.id.y_axis_text),
                            z_axis = (TextView)findViewById(R.id.z_axis_text);
//                            scalarRotation = (TextView)findViewById(R.id.scalar_rotation_text);
                    x_axis.setText(String.valueOf(Math.toDegrees(attitude[1])));
                    y_axis.setText(String.valueOf(Math.toDegrees(attitude[2])));
                    z_axis.setText(String.valueOf(Math.toDegrees(attitude[0])));
//                    scalarRotation.setText(String.valueOf(event.values[3]));
                }
            }

            @Override
            public void onAccuracyChanged(Sensor sensor, int accuracy) {

            }
        };
    }

    @Override
    protected void onResume() {
        super.onResume();
        mSensorManager.registerListener(mSensorListener, mAccelerometer, SensorManager.SENSOR_DELAY_NORMAL);
        mSensorManager.registerListener(mSensorListener, mMagneticField, SensorManager.SENSOR_DELAY_NORMAL);
    }

    @Override
    protected void onPause() {
        super.onPause();
        mSensorManager.unregisterListener(mSensorListener);
    }
}
