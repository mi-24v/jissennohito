package jissen.e.jissennohito;

import android.app.Activity;
import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import java.util.Arrays;

import io.reactivex.Observer;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class MainActivity extends Activity {
    private SensorManager mSensorManager;
    private Sensor mAccelerometer, mMagneticField;
    private SensorEventListener mSensorListener;

    private ApiService service = ApiUtils.build().create(ApiService.class);

    /**
     * rotation data set
     */
    private RotationData rotationData = new RotationData();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        mAccelerometer = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        mMagneticField = mSensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD);
        mSensorListener = new SensorEventListener() {
            @Override
            public void onSensorChanged(SensorEvent event) {
                //精度が悪いときは捨てる
                if (event.accuracy <= SensorManager.SENSOR_STATUS_UNRELIABLE) return;
                //取得
                switch (event.sensor.getType()) {
                    case Sensor.TYPE_MAGNETIC_FIELD:
                        rotationData.setGeomagnetic(event.values.clone());
                        break;
                    case Sensor.TYPE_ACCELEROMETER:
                        rotationData.setAcceleration(event.values.clone());
                        break;
                }
                if (rotationData.getGeomagnetic() != null && rotationData.getAcceleration() != null) {
                    float[] rotationMatrix = new float[9],
                            remappedRotationMatrix = new float[9];
                    SensorManager.getRotationMatrix(rotationMatrix, null, rotationData.getAcceleration(), rotationData.getGeomagnetic());
                    SensorManager.remapCoordinateSystem(rotationMatrix,
                            SensorManager.AXIS_X,
                            SensorManager.AXIS_Y,
                            remappedRotationMatrix);
                    SensorManager.getOrientation(remappedRotationMatrix, rotationData.orientations);
                    //convert into degree
                    rotationData.orientations = new float[]{
                            (float) Math.toDegrees(rotationData.orientations[0]),
                            (float) Math.toDegrees(rotationData.orientations[1]),
                            (float) Math.toDegrees(rotationData.orientations[2])};

                    TextView x_axis = (TextView) findViewById(R.id.x_axis_text),
                            y_axis = (TextView) findViewById(R.id.y_axis_text),
                            z_axis = (TextView) findViewById(R.id.z_axis_text),
                            zRotation = (TextView) findViewById(R.id.z_rotation_text);
                    z_axis.setText(String.valueOf(rotationData.orientations[0]));
                    x_axis.setText(String.valueOf(rotationData.orientations[1]));
                    y_axis.setText(String.valueOf(rotationData.orientations[2]));
                    zRotation.setRotation(rotationData.orientations[0]);
                    callSendDataApi(rotationData);
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

    private void callSendDataApi(RotationData rData) {
        RequestRotationData requestRotationData = new RequestRotationData();
        requestRotationData.setRotationData(rData);

        service.postRotationData(requestRotationData)
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.trampoline())
                .subscribe(new Observer<Void>() {
                               @Override
                               public void onSubscribe(@NonNull Disposable disposable) {
                               }

                               @Override
                               public void onNext(@NonNull Void aVoid) {
                                   Log.i(MainActivity.class.getName(), "SUCCESS");
                               }

                               @Override
                               public void onError(@NonNull Throwable throwable) {
                                   Log.i(MainActivity.class.getName(), "FAILED: " + Arrays.toString(throwable.getStackTrace()));
                               }

                               @Override
                               public void onComplete() {
                               }
                           }
                );
    }

    //TODO けすかも
//    private final BroadcastReceiver mUsbReceiver = new BroadcastReceiver() {
//        @Override
//        public void onReceive(Context context, Intent intent) {
//            if (ACTION_USB_PERMISSION.equals(intent.getAction())) {
//                synchronized (this) {
//                    usbDevice = (UsbDevice) intent.getParcelableExtra(UsbManager.EXTRA_DEVICE);
//                    if (intent.getBooleanExtra(UsbManager.EXTRA_PERMISSION_GRANTED, false)) {
//                        if (usbDevice != null) {
//                            //TODO start communication
//                        }
//                    }
//                }
//            }
//            if (UsbManager.ACTION_USB_DEVICE_DETACHED.equals(intent.getAction())) {
//                if (usbDevice != null) {
//                    //TODO close communication
//                }
//            }
//        }
//    };

}
