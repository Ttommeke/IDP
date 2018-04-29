package ruben.idpmonitoring;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.util.Log;

public class HeartrateService implements SensorEventListener{
    private Sensor sensor;

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

    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {
        String msg;
        switch(sensorEvent.sensor.getType()){
            case Sensor.TYPE_HEART_RATE:
                msg = "" + (int)sensorEvent.values[0];
                Log.d("MainActivity - Heartrate", msg);
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
