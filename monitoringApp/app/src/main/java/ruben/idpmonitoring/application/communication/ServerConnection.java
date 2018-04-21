package ruben.idpmonitoring.application.communication;

import android.telecom.Call;

import com.google.gson.Gson;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import cz.msebera.android.httpclient.Header;

public class ServerConnection {
    private static final String BASE_URL = "http://192.168.0.142:80";
    private static AsyncHttpClient client;

    public ServerConnection(){
        this.client = new AsyncHttpClient();
    }

    public static void postObject(Object obj, Callback callback){
        RequestParams params = new RequestParams();
        params.put("OBJECT", new Gson().toJson(obj));
        post("/postobject", params, obj, callback);
    }

    public static void login(String email, String password, Callback callback){
        RequestParams params = new RequestParams();
        params.put("email", email);
        params.put("password", password);
        post("/api/accountservice/login", params, null, callback);
    }

    public static void createUser(String firstname, String lastname, String email, String password, Callback callback){
        RequestParams params = new RequestParams();
        params.put("firstName", firstname);
        params.put("lastName", lastname);
        params.put("email", email);
        params.put("password", password);
        post("/api/accountservice/create", params, null, callback);
    }

    public static void getUserById(String id, Callback callback){
        RequestParams params = new RequestParams();
        get("/api/accountservice/getUserById/" + id, params, null, callback);
    }

    public static void getAllUsers(Callback callback){
        RequestParams params = new RequestParams();
        get("/api/accountservice/getAllUsers", params, null, callback);
    }

    public static void editUser(Callback callback){
        RequestParams params = new RequestParams();
        put("/api/accountservice/editUser", params, null, callback);
    }

    public static void postfitness(String id, int heartrate, int steps, String date, Callback callback){
        RequestParams params = new RequestParams();
        params.put("accountId", id);
        params.put("heartRate", heartrate);
        params.put("steps", steps);
        params.put("measurementDate", date);
        post("/fitnessservice/postfitness", params, null, callback);
    }

    public static void getFitnessOfPerson(String id, Callback callback){
        RequestParams params = new RequestParams();
        get("/api/fitnessservice/getfitnessofperson/" + id, params, null, callback);
    }

    public static void postAnswer(String id, String answer_on_question_id, Callback callback){
        RequestParams params = new RequestParams();
        params.put("accountId", id);
        params.put("PossibleAnswerOnQuestionId", answer_on_question_id);
        post("/questionservice/postAnswer", params, null, callback);
    }

    public static void getQuestions(Callback callback){
        RequestParams params = new RequestParams();
        get("/api/questionservice/getquestions", params, null, callback);
    }

    public static void getAnswersOfPerson(String id, Callback callback){
        RequestParams params = new RequestParams();
        get("/api/getanswersofperson/getquestions/" + id, params, null, callback);
    }

    private static void post(String url, RequestParams params, Object param, final Callback callback){
        client.post(BASE_URL + url, params, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                callback.taskCompleted(new String(responseBody));
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                if(statusCode == 0){
                    //Connection timed out: not connection / server not started
                }
            }
        });
    }

    public static void get(String url, RequestParams params, Object param, final Callback callback) {
        client.get(BASE_URL + url, params, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                callback.taskCompleted(new String(responseBody));
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                if(statusCode == 0){
                    //Connection timed out: not connection / server not started
                }
            }
        });
    }

    private static void put(String url, RequestParams params, Object param, final Callback callback){
        client.put(BASE_URL + url, params, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                callback.taskCompleted(new String(responseBody));
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                if(statusCode == 0){
                    //Connection timed out: not connection / server not started
                }
            }
        });
    }
}
