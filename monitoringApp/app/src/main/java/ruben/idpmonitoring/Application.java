package ruben.idpmonitoring;

import android.content.Context;
import android.content.Intent;

import java.util.List;

import ruben.idpmonitoring.activities.login.LoginActivity;
import ruben.idpmonitoring.application.User;
import ruben.idpmonitoring.application.communication.Callback;
import ruben.idpmonitoring.application.communication.ServerConnection;
import ruben.idpmonitoring.application.questions.Question;
import ruben.idpmonitoring.application.settings.Settings;

public class Application{
    private static ServerConnection server_connection;
    private static Settings settings;
    private static Context context;
    private static User user;
    private static boolean user_logged_in = false;

    public static void initialise(Context c){
        context = c;
        server_connection = new ServerConnection(context);
        settings = new Settings(context);
    }

    public static ServerConnection getServerConnection(){
        return server_connection;
    }

    public static Settings getSettings(){
        return settings;
    }

    public static void setUser(User u){
        user = u;
        user_logged_in = true;
    }

    public static User getUser(){
        return user;
    }

    public static void checkUserLoggedIn(){
        if(!user_logged_in){
            Intent intent = new Intent(context, LoginActivity.class);
            context.startActivity(intent);
        }
    }

    public static void logout(){
        settings.getSharedPreferencesEditor().putBoolean("user_logged_out", true).commit();

        user = null;
        user_logged_in = false;
        server_connection = null;

        Intent intent = new Intent(context, LoginActivity.class);
        context.startActivity(intent);
    }
}
