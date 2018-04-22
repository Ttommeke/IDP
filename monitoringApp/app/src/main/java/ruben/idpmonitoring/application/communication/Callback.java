package ruben.idpmonitoring.application.communication;

public interface Callback {
    public void taskCompleted(int status_code, String results);
}
