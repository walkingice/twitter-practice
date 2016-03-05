package julian.test.twitter;

import android.app.Activity;
import android.app.AlertDialog;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceFragment;
import android.preference.PreferenceManager;

public class SettingsActivity extends Activity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getFragmentManager().beginTransaction()
                .replace(android.R.id.content, new SettingsFragment())
                .commit();
    }

    public static class SettingsFragment extends PreferenceFragment {
        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            addPreferencesFromResource(R.xml.settings);
            bindControl();
        }

        // So we are able to clear saved-preferences easily.
        private void bindControl() {
            Preference prefClear = findPreference(getString(R.string.key_clear));
            prefClear.setOnPreferenceClickListener(pref -> {
                // Prompt a confirmation dialog in case of user mis-touch.
                // I know to enter four LONG-LONG-LONG random strings is painful.
                (new AlertDialog.Builder(getActivity()))
                        .setTitle("Are you sure")
                        .setMessage("To clear shared-preferences?")
                        .setPositiveButton("Yes", ((dialog, which) -> {
                            PreferenceManager.getDefaultSharedPreferences(getActivity())
                                    .edit()
                                    .clear()
                                    .commit();
                        }))
                        .setNegativeButton("No", null)
                        .setCancelable(true)
                        .show();
                return true;
            });
        }
    }
}
