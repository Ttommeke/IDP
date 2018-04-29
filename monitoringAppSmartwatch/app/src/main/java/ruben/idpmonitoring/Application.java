package ruben.idpmonitoring;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorManager;

public class Application {
    private static SmartphoneConnection smartphone_connection;
    private static Context context;
    private static SensorManager sensor_manager;
    private static HeartrateService heartrate_service;
    private static StepcountService step_count_service;

    public static void initialise(Context c){
        context = c;
        smartphone_connection = new SmartphoneConnection(context);
        sensor_manager = (SensorManager) context.getSystemService(Context.SENSOR_SERVICE);
        heartrate_service = new HeartrateService(sensor_manager.getDefaultSensor(Sensor.TYPE_HEART_RATE));
        step_count_service = new StepcountService(sensor_manager.getDefaultSensor(Sensor.TYPE_STEP_COUNTER));
    }

    public static void startSensors(){
        heartrate_service.startSensor(sensor_manager);
        step_count_service.startSensor(sensor_manager);
    }

    public static void stopSensors(){

    }
}
