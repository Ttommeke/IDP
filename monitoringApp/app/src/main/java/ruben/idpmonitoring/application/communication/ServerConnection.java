package ruben.idpmonitoring.application.communication;

import android.content.Context;

import com.google.gson.JsonObject;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONArray;
import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;
import cz.msebera.android.httpclient.HttpEntity;
import cz.msebera.android.httpclient.entity.StringEntity;
import cz.msebera.android.httpclient.message.BasicHeader;
import cz.msebera.android.httpclient.protocol.HTTP;
import ruben.idpmonitoring.Application;

public class ServerConnection {
    private static final String BASE_URL = "http://192.168.0.142:80";
    private AsyncHttpClient client;
    private Context context;

    public ServerConnection(Context context){
        this.client = new AsyncHttpClient();
        this.context = context;
    }

    public void setTokenHeader(String token) {
        this.client.removeHeader("token");
        this.client.addHeader("token", token);
    }

    public void login(String email, String password, Callback callback){
        JSONObject json_params = new JSONObject();
        StringEntity entity = null;
        try{
            json_params.put("email", email);
            json_params.put("password", password);
            entity = new StringEntity(json_params.toString());
            entity.setContentType(new BasicHeader(HTTP.CONTENT_TYPE, "application/json"));
        } catch (Exception ex){
            callback.taskCompleted(999, null);
        }

        post("/api/accountservice/login", entity, callback);
    }

    public void updateDeviceId(String deviceId, Callback callback){
        JSONObject json_params = new JSONObject();
        StringEntity entity = null;
        try {
            json_params.put("deviceId", deviceId);
            entity = new StringEntity(json_params.toString());
            entity.setContentType(new BasicHeader(HTTP.CONTENT_TYPE, "application/json"));
        } catch(Exception ex){
            callback.taskCompleted(999, null);
        }
        put("/api/notificationservice/updatemyquestiondeviceid", entity, callback);
    }

    public void createUser(String firstname, String lastname, String email, String password, Callback callback){
        JSONObject json_params = new JSONObject();
        StringEntity entity = null;
        try{
            json_params.put("firstName", firstname);
            json_params.put("lastName", lastname);
            json_params.put("email", email);
            json_params.put("password", password);
            entity = new StringEntity(json_params.toString());
            entity.setContentType(new BasicHeader(HTTP.CONTENT_TYPE, "application/json"));
        } catch (Exception ex){
            callback.taskCompleted(999, null);
        }

        post("/api/accountservice/create", entity, callback);
    }

    public void getUserById(String id, Callback callback){
        get("/api/accountservice/getUserById/" + id, callback);
    }

    public void getAllUsers(Callback callback){
        get("/api/accountservice/getAllUsers", callback);
    }

    public void editUser(String key, String value, Callback callback){
        JSONObject json_params = new JSONObject();
        StringEntity entity = null;
        try {
            json_params.put("id", Application.getUser().getId());
            json_params.put(key, value);
            entity = new StringEntity(json_params.toString());
            entity.setContentType(new BasicHeader(HTTP.CONTENT_TYPE, "application/json"));
        } catch (Exception ex){
            callback.taskCompleted(999, null);
        }
        put("/api/accountservice/editUser", entity, callback);
    }

    public void postfitness(String id, int heartrate, int steps, String date, Callback callback){
        JSONObject json_params = new JSONObject();
        StringEntity entity = null;
        try {
            json_params.put("accountId", id);
            json_params.put("heartRate", heartrate);
            json_params.put("steps", steps);
            json_params.put("measurementDate", date);
            entity = new StringEntity(json_params.toString());
            entity.setContentType(new BasicHeader(HTTP.CONTENT_TYPE, "application/json"));
        } catch(Exception ex){
            callback.taskCompleted(999, null);
        }
        post("/fitnessservice/postfitness", entity, callback);
    }

    public void getFitnessOfPerson(String id, Callback callback){
        get("/api/fitnessservice/getfitnessofperson/" + id, callback);
    }

    public void postAnswer(String id, String answer_on_question_id, Callback callback){
        JSONObject json_params = new JSONObject();
        StringEntity entity = null;
        try {
            json_params.put("accountId", id);
            json_params.put("PossibleAnswerOnQuestionId", answer_on_question_id);
            entity = new StringEntity(json_params.toString());
            entity.setContentType(new BasicHeader(HTTP.CONTENT_TYPE, "application/json"));
        } catch(Exception ex){
            callback.taskCompleted(999, null);
        }
        post("/api/questionservice/postAnswer", entity, callback);
    }

    public void getQuestions(Callback callback){
        get("/api/questionservice/getquestions", callback);
    }

    public void getAnswersOfPerson(String id, Callback callback){
        get("/api/getanswersofperson/" + id, callback);
    }

    private void post(String url, HttpEntity entity, final Callback callback){
        client.post(this.context, BASE_URL + url, entity, "application/json", new JsonHttpResponseHandler(){
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                callback.taskCompleted(statusCode, response.toString());
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                callback.taskCompleted(statusCode, errorResponse.toString());
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONArray response) {
                callback.taskCompleted(statusCode, response.toString());
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONArray errorResponse) {
                callback.taskCompleted(statusCode, errorResponse.toString());
            }
        });
    }

    public void get(String url, final Callback callback) {
        client.get(this.context,BASE_URL + url, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                callback.taskCompleted(statusCode, response.toString());
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                callback.taskCompleted(statusCode, errorResponse.toString());
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONArray response) {
                callback.taskCompleted(statusCode, response.toString());
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONArray response) {
                callback.taskCompleted(statusCode, response.toString());
            }
        });
    }

    private void put(String url, HttpEntity entity, final Callback callback){
        client.put(this.context,BASE_URL + url, entity, "application/json", new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                callback.taskCompleted(statusCode, response.toString());
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                callback.taskCompleted(statusCode, errorResponse.toString());
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONArray response) {
                callback.taskCompleted(statusCode, response.toString());
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONArray errorResponse) {
                callback.taskCompleted(statusCode, errorResponse.toString());
            }
        });
    }
}
