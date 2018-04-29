package ruben.idpmonitoring.application.history;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;

public class ObjectiveMeasurement {
    private DateTime time;
    private int heartrate;
    private int steps;

    public ObjectiveMeasurement(){

    }

    public ObjectiveMeasurement(int heartrate, int steps){
        this.time = new DateTime(DateTimeZone.UTC);
        this.heartrate = heartrate;
        this.steps = steps;
    }

    public ObjectiveMeasurement(DateTime time, int heartrate, int steps){
        this.time = time;
        this.heartrate = heartrate;
        this.steps = steps;
    }

    public void setTime(DateTime time){
        this.time = time;
    }

    public DateTime getTime(){
        return this.time;
    }

    public void setHeartrate(int heart_rate){
        this.heartrate = heart_rate;
    }

    public int getHeartrate(){
        return this.heartrate;
    }

    public void setSteps(int steps_taken){
        this.steps = steps_taken;
    }

    public int getSteps(){
        return this.steps;
    }

    public static ObjectiveMeasurement fromJson(String json){
        JsonParser parser = new JsonParser();
        JsonObject json_object = parser.parse(json).getAsJsonObject();

        ObjectiveMeasurement obj = new ObjectiveMeasurement(
                json_object.get("heartrate").getAsInt(),
                json_object.get("steps").getAsInt());
        return obj;
    }
}
