package ruben.idpmonitoring.application.history;

import java.util.ArrayList;
import java.util.List;

public class History {
    private List<Record> records;

    public History(){
        this.records = new ArrayList<Record>();
    }

    public List<Record> getRecords(){
        return this.records;
    }

    public void addRecord(){

    }
}
