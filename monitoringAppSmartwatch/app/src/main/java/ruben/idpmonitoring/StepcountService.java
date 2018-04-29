package ruben.idpmonitoring;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.util.Log;

public class StepcountService implements SensorEventListener {
    private Sensor sensor;
    private int steps;

    public StepcountService(Sensor sensor){
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

    public int getSteps(){
        return this.steps;
    }

    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {
        switch(sensorEvent.sensor.getType()){
            case Sensor.TYPE_STEP_COUNTER:
                steps = (int)sensorEvent.values[0];
                Log.d("MainActivity - Step Counter", Integer.toString(steps));
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
