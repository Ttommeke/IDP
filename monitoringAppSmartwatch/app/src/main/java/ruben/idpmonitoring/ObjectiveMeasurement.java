package ruben.idpmonitoring;

public class ObjectiveMeasurement {
    private int heartrate;
    private int steps;

    public ObjectiveMeasurement(){

    }

    public ObjectiveMeasurement(int heartrate, int steps){
        this.heartrate = heartrate;
        this.steps = steps;
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
}

