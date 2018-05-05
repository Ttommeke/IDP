package ruben.idpmonitoring.application.communication;

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

public class SmartwatchConnection implements GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener, MessageApi.MessageListener{

    private GoogleApiClient google_api_client;
    private Node node;
    private Context context;
    private boolean node_connected = false;
    private Callback callback;

    public SmartwatchConnection(Context context){
        this.context = context;

        this.google_api_client = new GoogleApiClient.Builder(this.context)
                .addApi(Wearable.API)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .build();
        this.google_api_client.connect();
    }

    public boolean isConnected(){
        return this.node_connected;
    }

    public void retryConnection(){
        google_api_client.connect();
    }

    public void getObjectiveMeasurement(Callback callback){
        this.callback = callback;
        sendMessage("");
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {
        new Thread(new Runnable(){
            public void run(){
                NodeApi.GetConnectedNodesResult result = Wearable.NodeApi.getConnectedNodes(google_api_client).await();
                List<Node> nodes = result.getNodes();
                if(nodes.size() > 0)
                    node = nodes.get(0);
            }

        }).start();
        Wearable.MessageApi.addListener(google_api_client, this);
        this.node_connected = true;
    }

    @Override
    public void onConnectionSuspended(int i) {
        this.node_connected = false;
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        this.node_connected = false;
    }

    private void sendMessage(final String message){
        new Thread(new Runnable(){
            public void run(){
                if(node != null){
                    byte[] bytes = (message.getBytes());
                    Wearable.MessageApi.sendMessage(google_api_client, node.getId(), "/MessageFromPhone", bytes).await();
                }
            }
        }).start();
    }

    @Override
    public void onMessageReceived(MessageEvent messageEvent) {
        if(messageEvent.getPath().equals("/MessageFromWatch")) {
            Log.d("SmartphoneConnection","Message from watch received");
            this.callback.taskCompleted(200, new String(messageEvent.getData()));
        }
    }
}
