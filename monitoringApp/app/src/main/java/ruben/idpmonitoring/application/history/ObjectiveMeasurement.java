package ruben.idpmonitoring.application.history;

import org.joda.time.DateTime;

public class ObjectiveMeasurement {
    private DateTime time;
    private int heart_rate;
    private int steps_taken;

    public ObjectiveMeasurement(){

    }

    public ObjectiveMeasurement(DateTime time, int heart_rate, int steps_taken){
        this.time = time;
        this.heart_rate = heart_rate;
        this.steps_taken = steps_taken;
    }

    public void setTime(DateTime time){
        this.time = time;
    }

    public DateTime getTime(){
        return this.time;
    }

    public void setHeartRate(int heart_rate){
        this.heart_rate = heart_rate;
    }

    public int getHeartRate(){
        return this.heart_rate;
    }

    public void setStepsTaken(int steps_taken){
        this.steps_taken = steps_taken;
    }

    public int getStepsTaken(){
        return this.steps_taken;
    }
}
