package jissen.e.jissennohito;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.hardware.usb.UsbDevice;
import android.hardware.usb.UsbManager;
import android.os.Bundle;
import android.widget.TextView;

import java.util.Collection;
import java.util.HashMap;

public class MainActivity extends Activity {
    private SensorManager mSensorManager;
    private Sensor mAccelerometer,mMagneticField;
    private SensorEventListener mSensorListener;

    private UsbDevice usbDevice;
//    private UsbManager mUsbManager;
    private static final String ACTION_USB_PERMISSION = "jissen.e.jissennohito.USB_PERMISSION";

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

//        mUsbManager = (UsbManager)getSystemService(Context.USB_SERVICE);
//        HashMap<String, UsbDevice> deviceList = mUsbManager.getDeviceList();
//        Collection<UsbDevice> doge = deviceList.values();TODO for debug
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

    private final BroadcastReceiver mUsbReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if(ACTION_USB_PERMISSION.equals(intent.getAction())){
                synchronized (this){
                    usbDevice = (UsbDevice)intent.getParcelableExtra(UsbManager.EXTRA_DEVICE);
                    if(intent.getBooleanExtra(UsbManager.EXTRA_PERMISSION_GRANTED, false)){
                        if(usbDevice != null){
                            //TODO start communication
                        }
                    }
                }
            }
            if(UsbManager.ACTION_USB_DEVICE_DETACHED.equals(intent.getAction())){
                if(usbDevice != null){
                    //TODO close communication
                }
            }
        }
    };

}
