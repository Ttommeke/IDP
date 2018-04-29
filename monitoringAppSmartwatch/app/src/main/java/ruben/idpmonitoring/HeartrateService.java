package ruben.idpmonitoring;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.util.Log;

public class HeartrateService implements SensorEventListener{
    private Sensor sensor;
    private int heartrate = 60; //default value to start with

    public HeartrateService(Sensor sensor){
        this.sensor = sensor;
    }

    public Sensor getSensor() {
        return sensor;
    }

    public void startSensor(SensorManager manager){
        manager.registerListener(this, this.sensor, SensorManager.SENSOR_DELAY_NORMAL);
    }

    public void stopSensor(SensorManager manager){
        manager.unregisterListener(this);
    }

    public int getHeartrate(){
        return this.heartrate;
    }

    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {
        switch(sensorEvent.sensor.getType()){
            case Sensor.TYPE_HEART_RATE:
                heartrate = (int)(0.5 * heartrate + 0.5 * (int)sensorEvent.values[0]);
                Log.d("MainActivity - Heartrate", Integer.toString(heartrate));
                break;
            default:
                Log.d("MainActivity", "Unknown sensor data received");
                break;
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }
}
