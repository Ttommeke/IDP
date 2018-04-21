package ruben.idpmonitoring.application.communication;

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

    public static void postObject(Object obj){
        RequestParams params = new RequestParams();
        params.put("OBJECT", new Gson().toJson(obj));
        post("/postobject", params, obj);
    }

    public static void getQuestions(){
        RequestParams params = new RequestParams();
        get("/api/questionservice/getquestions", params);
    }

    private static void post(String url, RequestParams params, Object param){
        final String finalUrl = url;
        final Object finalParam = param;
        client.post(BASE_URL + url, params, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {

            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                if(statusCode == 0){
                    //Connection timed out: not connection / server not started
                }
            }
        });
    }

    public static void get(String url, RequestParams params) {
        client.get(BASE_URL + url, params, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                String s = new String(responseBody);
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