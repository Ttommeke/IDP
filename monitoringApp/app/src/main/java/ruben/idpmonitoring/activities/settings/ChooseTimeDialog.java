package ruben.idpmonitoring.activities.settings;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TimePicker;
import ruben.idpmonitoring.R;

public class ChooseTimeDialog extends AppCompatActivity{
    private TimePicker tp;
    private int hour;
    private int minute;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_choose_time);
        setTitle("");

        loadElements();
    }

    private void loadElements(){
        tp = (TimePicker) this.findViewById(R.id.tpPeriode);
        tp.setIs24HourView(true);

        String sTime;
        /*if(this.getKey().equals("user_notifications_from")){
            sTime = Application.getSettings().getSharedPreferences().getString("user_notifications_from", "12:00");
        } else {
            sTime = Application.getSettings().getSharedPreferences().getString("user_notifications_until", "12:00");
        }

        tp.setCurrentHour(Integer.valueOf(sTime.split(":")[0]));
        tp.setCurrentMinute(Integer.valueOf(sTime.split(":")[1]));*/
    }



    /*@Override
    protected void onDialogClosed(boolean positiveResult) {
        super.onDialogClosed(positiveResult);

        if (positiveResult) {
            hour=tp.getCurrentHour();
            minute=tp.getCurrentMinute();
            String sHour = String.valueOf(hour);
            String sMinute = String.valueOf(minute);
            if(minute == 0) sMinute = "00";

            String time=sHour+":"+sMinute;

            if (callChangeListener(time)) {
                persistString(time);
            }
        }
    }*/
}
