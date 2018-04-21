package ruben.idpmonitoring.activities.settings;

import ruben.idpmonitoring.Application;
import ruben.idpmonitoring.R;
import android.os.Bundle;
import android.preference.CheckBoxPreference;
import android.preference.EditTextPreference;
import android.preference.Preference;
import android.preference.PreferenceFragment;
import android.preference.SwitchPreference;
import android.support.v7.app.AppCompatActivity;

public class SettingsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getFragmentManager().beginTransaction().replace(android.R.id.content, new MyPreferenceFragment()).commit();
        this.setTitle("Instellingen");
    }

    public static class MyPreferenceFragment extends PreferenceFragment
    {
        private EditTextPreference etp_firstname;
        private EditTextPreference etp_lastname;
        private EditTextPreference etp_email;
        private EditTextPreference etp_password;
        private SwitchPreference cp_notification;
        private CheckBoxPreference cbp_keep_me_logged_in;

        @Override
        public void onCreate(final Bundle savedInstanceState)
        {
            super.onCreate(savedInstanceState);
            addPreferencesFromResource(R.xml.preferences);
            getViewComponents();
            this.setPreferenceSummaries();
        }

        private void getViewComponents(){
            this.etp_firstname = (EditTextPreference)this.findPreference("user_firstname");
            this.etp_lastname = (EditTextPreference)this.findPreference("user_lastname");
            this.etp_email = (EditTextPreference)this.findPreference("user_email");
            this.etp_password = (EditTextPreference)this.findPreference("user_password");
            this.cp_notification = (SwitchPreference) this.findPreference("notify_questions");
            this.cbp_keep_me_logged_in = (CheckBoxPreference) this.findPreference("keep_me_logged_in");
        }

        @Override
        public void onResume() {
            super.onResume();
        }

        @Override
        public void onPause() {
            super.onPause();
        }

        private void setPreferenceSummaries(){
            this.etp_firstname.setSummary(Application.getSettings().getSharedPreferences().getString("user_firstname", "Pas hier je voornaam aan"));
            this.etp_lastname.setSummary(Application.getSettings().getSharedPreferences().getString("user_lastname", "Pas hier je achternaam aan"));
            this.etp_email.setSummary(Application.getSettings().getSharedPreferences().getString("user_email", "Pas hier je email aan"));
            this.etp_password.setSummary(Application.getSettings().getSharedPreferences().getString("user_password", "Pas hier je wachtwoord aan"));
            this.cp_notification.setChecked(Application.getSettings().getSharedPreferences().getBoolean("notify_questions", true));
            this.cbp_keep_me_logged_in.setChecked(Application.getSettings().getSharedPreferences().getBoolean("keep_me_logged_in", true));
        }
    }

}
