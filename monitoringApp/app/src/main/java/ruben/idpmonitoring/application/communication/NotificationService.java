package ruben.idpmonitoring.application.communication;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import org.json.JSONObject;

import java.util.Map;

import ruben.idpmonitoring.R;
import ruben.idpmonitoring.activities.MainActivity;
import ruben.idpmonitoring.activities.history.OverviewActivity;
import ruben.idpmonitoring.activities.questions.QuestionActivity;
import ruben.idpmonitoring.application.history.History;
import ruben.idpmonitoring.application.questions.Question;

public class NotificationService extends FirebaseMessagingService {

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        Intent intent;
        PendingIntent pendingIntent;
        //Check if message contains a data payload
        if (remoteMessage.getData().size() > 0) {
            Map<String, String> content = remoteMessage.getData();
            String action = content.get("action");
            try{
                JSONObject json = new JSONObject(content.get("notification"));
                String body = json.getString("body");
                String title = json.getString("title");

                switch(action){
                    case "QUESTION":
                        intent = new Intent(this, QuestionActivity.class);
                        break;
                    case "OPEN HISTOGRAM":
                        intent = new Intent(this, OverviewActivity.class);
                        break;
                    default:
                        intent = new Intent(this, MainActivity.class);
                        break;
                }
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                pendingIntent = PendingIntent.getActivity(this, 0 /* Request code */, intent,
                        PendingIntent.FLAG_ONE_SHOT);


                NotificationCompat.Builder notificationBuilder = null;
                notificationBuilder = new NotificationCompat.Builder(this)
                        .setSmallIcon(R.drawable.farm)
                        .setContentTitle(title)
                        .setContentText(body)
                        .setAutoCancel(true)
                        .setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION))
                        .setContentIntent(pendingIntent);

                if (notificationBuilder != null) {
                    NotificationManager notificationManager =
                            (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
                    notificationManager.notify(1, notificationBuilder.build());
                } else {
                    Log.d("NotificationService", "error NotificationManager");
                }

            }catch(Exception ex){
                Log.e("NotificationService", ex.getMessage());
            }
        }
    }

    @Override
    public void onDeletedMessages() {
        super.onDeletedMessages();
    }
}
