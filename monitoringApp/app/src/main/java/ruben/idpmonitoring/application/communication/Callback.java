package ruben.idpmonitoring.application.communication;

public interface Callback {
    void taskCompleted(int status_code, String json);
}
