package ruben.idpmonitoring.activities.settings;

import ruben.idpmonitoring.Application;
import ruben.idpmonitoring.R;
import ruben.idpmonitoring.application.communication.Callback;

import android.content.Intent;
import android.content.SharedPreferences;
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
        private Preference notifications_from, notifications_until;
        private SharedPreferences.OnSharedPreferenceChangeListener listener;

        @Override
        public void onCreate(final Bundle savedInstanceState)
        {
            super.onCreate(savedInstanceState);
            addPreferencesFromResource(R.xml.preferences);
            getViewComponents();
            this.setPreferenceSummaries();
            this.setOnPreferenceChangeListener();
        }

        private void getViewComponents(){
            this.etp_firstname = (EditTextPreference)this.findPreference("user_firstname");
            this.etp_lastname = (EditTextPreference)this.findPreference("user_lastname");
            this.etp_email = (EditTextPreference)this.findPreference("user_email");
            this.etp_password = (EditTextPreference)this.findPreference("user_password");
            this.cp_notification = (SwitchPreference) this.findPreference("notify_questions");
            this.cbp_keep_me_logged_in = (CheckBoxPreference) this.findPreference("keep_me_logged_in");
            this.notifications_from = this.findPreference("notifications_from");
            this.notifications_until = this.findPreference("notifications_until");

            this.notifications_from.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
                public boolean onPreferenceClick(Preference preference) {
                    Intent intent = new Intent(getActivity(), ChooseTimeDialog.class);
                    startActivity(intent);
                    return true;
                }
            });

            this.notifications_until.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
                public boolean onPreferenceClick(Preference preference) {
                    Intent intent = new Intent(getActivity(), ChooseTimeDialog.class);
                    startActivity(intent);
                    return true;
                }
            });
        }

        @Override
        public void onResume() {
            super.onResume();
            Application.getSettings().getSharedPreferences().registerOnSharedPreferenceChangeListener(listener);
        }

        @Override
        public void onPause() {
            super.onPause();
            Application.getSettings().getSharedPreferences().registerOnSharedPreferenceChangeListener(listener);
        }

        private void setPreferenceSummaries(){
            this.etp_firstname.setSummary(Application.getSettings().getSharedPreferences().getString("user_firstname", "Pas hier je voornaam aan"));
            this.etp_lastname.setSummary(Application.getSettings().getSharedPreferences().getString("user_lastname", "Pas hier je achternaam aan"));
            this.etp_email.setSummary(Application.getSettings().getSharedPreferences().getString("user_email", "Pas hier je email aan"));
            this.etp_password.setSummary(Application.getSettings().getSharedPreferences().getString("user_password", "Pas hier je wachtwoord aan"));
            this.cp_notification.setChecked(Application.getSettings().getSharedPreferences().getBoolean("notify_questions", true));
            this.cbp_keep_me_logged_in.setChecked(Application.getSettings().getSharedPreferences().getBoolean("keep_me_logged_in", true));
        }

        private void setOnPreferenceChangeListener(){
            this.listener = new SharedPreferences.OnSharedPreferenceChangeListener() {
                @Override
                public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
                    Preference pref = findPreference(key);
                    switch(key){
                        case "user_firstname":
                            pref.setSummary(sharedPreferences.getString(key, "Pas hier je voornaam aan"));
                            Application.getServerConnection().editUser("firstName", sharedPreferences.getString(key, "firstName"), new Callback() {
                                @Override
                                public void taskCompleted(int status_code, String json) {

                                }
                            });
                            break;
                        case "user_lastname":
                            pref.setSummary(sharedPreferences.getString(key, "Pas hier je voornaam aan"));
                            Application.getServerConnection().editUser("lastName", sharedPreferences.getString(key, "lastName"), new Callback() {
                                @Override
                                public void taskCompleted(int status_code, String json) {

                                }
                            });
                            break;
                        case "user_email":
                            pref.setSummary(sharedPreferences.getString(key, "Pas hier je voornaam aan"));
                            Application.getServerConnection().editUser("email", sharedPreferences.getString(key, "email"), new Callback() {
                                @Override
                                public void taskCompleted(int status_code, String json) {

                                }
                            });
                            break;
                        case "user_password":
                            pref.setSummary(sharedPreferences.getString(key, "Pas hier je voornaam aan"));
                            Application.getServerConnection().editUser("password", sharedPreferences.getString(key, "password"), new Callback() {
                                @Override
                                public void taskCompleted(int status_code, String json) {

                                }
                            });
                            break;
                        case "keep_me_logged_in":
                            boolean keep_me_logged_in = sharedPreferences.getBoolean(key, false);
                            if(keep_me_logged_in){
                                String id = Application.getUser().getId();
                                Application.getSettings().getSharedPreferencesEditor().putString("keep_me_logged_in_id", id).commit();
                            }
                        default: break;
                    }
                }
            };
        }
    }

}
