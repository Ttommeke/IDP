package ruben.idpmonitoring;

import android.content.Context;

import java.util.List;

import ruben.idpmonitoring.application.communication.Callback;
import ruben.idpmonitoring.application.communication.ServerConnection;
import ruben.idpmonitoring.application.questions.Question;
import ruben.idpmonitoring.application.settings.Settings;

public class Application{
    private static ServerConnection server_connection;
    private static Settings settings;
    private static Context context;

    public static void initialise(Context c){
        context = c;
        server_connection = new ServerConnection();
        settings = new Settings(context);
    }

    public static ServerConnection getServerConnection(){
        return server_connection;
    }

    public static Settings getSettings(){
        return settings;
    }
}
