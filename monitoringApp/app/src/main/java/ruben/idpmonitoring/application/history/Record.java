package ruben.idpmonitoring.application.history;

import org.joda.time.DateTime;

public class Record {
    private DateTime date;
    private int avg_happiness;
    private int avg_fatigue;
    private int avg_activity;
    private RecordDetails details;

    public DateTime getDate(){
        return this.date;
    }

    public int getAvgHappiness(){
        return this.avg_happiness;
    }

    public int getAvgFatigue(){
        return this.avg_fatigue;
    }

    public int getAvgActivity(){
        return this.avg_activity;
    }

    public RecordDetails getDetails() {
        return this.details;
    }
}
