package ruben.idpmonitoring;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.wearable.MessageApi;
import com.google.android.gms.wearable.MessageEvent;
import com.google.android.gms.wearable.Node;
import com.google.android.gms.wearable.NodeApi;
import com.google.android.gms.wearable.Wearable;

import java.util.List;

public class SmartphoneConnection implements GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener, MessageApi.MessageListener{

    private GoogleApiClient google_api_client;
    private Node node;
    private Context context;
    private boolean node_connected = false;

    public SmartphoneConnection(Context context){
        this.context = context;
        google_api_client = new GoogleApiClient.Builder(this.context)
                .addApi(Wearable.API)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .build();
        google_api_client.connect();
    }

    public void sendData(String data){
        sendMessage(data);
    }

    public boolean isConnected(){
        return node_connected;
    }

    public void retryConnection(){
        google_api_client.connect();
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {
        node_connected = true;
        new Thread(new Runnable(){
            public void run(){
                NodeApi.GetConnectedNodesResult result = Wearable.NodeApi.getConnectedNodes(google_api_client).await();
                List<Node> nodes = result.getNodes();
                if(nodes.size() > 0)
                    node = nodes.get(0);
            }

        }).start();
        Wearable.MessageApi.addListener(google_api_client, this);
    }

    @Override
    public void onConnectionSuspended(int i) {
        node_connected = false;
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        node_connected = false;
    }

    private void sendMessage(final String message)
    {
        new Thread(new Runnable()
        {
            @Override
            public void run()
            {
                if(node != null && node_connected){
                    byte[]bytes = message.getBytes();
                    Wearable.MessageApi.sendMessage(google_api_client, node.getId(), "/MessageFromWatch", bytes).await();
                }
            }
        }).start();
    }

    @Override
    public void onMessageReceived(MessageEvent messageEvent) {
        if(messageEvent.getPath().equals("/MessageFromPhone")) {
            Log.d("SmartphoneConnection","Message from phone received: " + new String(messageEvent.getData()));

            ObjectiveMeasurement obj = Application.generateObjectiveMeasurement();

            sendData("{heartrate : " + Integer.toString(obj.getHeartrate()) + ", steps: " + obj.getSteps() + "}" + "|");
        }
    }
}
