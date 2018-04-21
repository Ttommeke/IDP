package ruben.idpmonitoring;

import java.util.List;

import ruben.idpmonitoring.application.communication.ServerConnection;
import ruben.idpmonitoring.application.questions.Question;

public class Application {
    public static List<Question> questions;
    public static ServerConnection server_connection = new ServerConnection();

    public Application(){
    }

    public static void loadQuestions(){
        server_connection.getQuestions();
    }
}
