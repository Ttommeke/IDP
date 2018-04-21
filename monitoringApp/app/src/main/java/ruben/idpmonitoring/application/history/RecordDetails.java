package ruben.idpmonitoring.application.history;

import java.util.List;

public class RecordDetails {
    private List<ObjectiveMeasurement> objective_measurements;
    private List<SubjectiveMeasurement> subjective_measurements;

    public List<ObjectiveMeasurement> getObjectiveMeasurements() {
         return this.objective_measurements;
    }

    public List<SubjectiveMeasurement> getSubjectiveMeasurements() {
        return this.subjective_measurements;
    }
}
