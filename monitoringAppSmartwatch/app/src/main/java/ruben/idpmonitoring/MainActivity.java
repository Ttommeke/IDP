package ruben.idpmonitoring;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.support.wearable.activity.WearableActivity;
import android.util.Log;
import android.widget.TextView;

public class MainActivity extends WearableActivity implements SensorEventListener{

    private TextView txt_heartrate, txt_steps;
    private SensorManager sensor_manager;
    private Sensor sensor_heartrate, sensor_steps;
    private boolean debug = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        loadElements();
        startSensors();

        Application.initialise(this);

        // Enables Always-on
        setAmbientEnabled();
    }

    private void loadElements(){
        if(debug){
            this.txt_heartrate = (TextView) this.findViewById(R.id.txt_heartrate);
            this.txt_steps = (TextView) this.findViewById(R.id.txt_steps);
        }
    }

    private void startSensors(){
        this.sensor_manager = ((SensorManager) getSystemService(SENSOR_SERVICE));
        this.sensor_heartrate = sensor_manager.getDefaultSensor(Sensor.TYPE_HEART_RATE);
        this.sensor_steps = sensor_manager.getDefaultSensor(Sensor.TYPE_STEP_COUNTER);
        this.sensor_manager.registerListener(this, sensor_heartrate, SensorManager.SENSOR_DELAY_NORMAL);
        this.sensor_manager.registerListener(this, sensor_steps, SensorManager.SENSOR_DELAY_NORMAL);
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {
        Log.d("MainActivity", "onAccuracyChanged - accuracy: " + i);
    }

    public void onSensorChanged(SensorEvent event) {
        String msg;
        switch(event.sensor.getType()){
            case Sensor.TYPE_HEART_RATE:
                msg = "" + (int)event.values[0];
                if(debug){
                    this.txt_heartrate.setText(msg);
                }
                Log.d("MainActivity - Heartrate", msg);
                break;
            case Sensor.TYPE_STEP_COUNTER:
                msg = "" + (int)event.values[0];
                if(debug){
                    this.txt_steps.setText(msg);
                }
                Log.d("MainActivity - Step Counter", msg);
                break;
            default:
                Log.d("MainActivity", "Unknown sensor data received");
                break;
        }
    }
}
