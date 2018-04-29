package ruben.idpmonitoring;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.support.wearable.activity.WearableActivity;
import android.util.Log;
import android.widget.TextView;

public class MainActivity extends WearableActivity{

    private TextView txt_heartrate, txt_steps;
    private SensorManager sensor_manager;
    private Sensor sensor_heartrate, sensor_steps;
    private boolean debug = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        loadElements();

        Application.initialise(this);
        Application.startSensors();

        // Enables Always-on
        setAmbientEnabled();
    }

    @Override
    protected void onResume() {
        super.onResume();
        Application.startSensors();

        if(!Application.getSmartphoneConnection().isConnected()){
            Application.getSmartphoneConnection().retryConnection();
        }
    }

    @Override
    protected void onPause() {
        Application.stopSensors();
        super.onPause();
    }

    private void loadElements(){
        if(debug){
            this.txt_heartrate = (TextView) this.findViewById(R.id.txt_heartrate);
            this.txt_steps = (TextView) this.findViewById(R.id.txt_steps);
        }
    }
}
