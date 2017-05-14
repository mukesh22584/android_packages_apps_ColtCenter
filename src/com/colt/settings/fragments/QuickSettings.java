package com.colt.settings.fragments;

import android.os.Bundle;
import android.content.ContentResolver;
import android.content.res.Resources;
import android.support.v7.preference.Preference;
import android.support.v7.preference.PreferenceScreen;
import android.support.v7.preference.Preference.OnPreferenceChangeListener;
import android.support.v14.preference.SwitchPreference;
import android.provider.Settings;
import android.text.TextUtils;
import android.view.View;

import com.android.settings.R;
import com.colt.settings.preferences.CustomSeekBarPreference;
import com.android.settings.SettingsPreferenceFragment;
import com.android.settings.Utils;

import com.android.internal.logging.MetricsProto.MetricsEvent;


public class QuickSettings extends SettingsPreferenceFragment
        implements Preference.OnPreferenceChangeListener {

    private static final String PREF_SYSUI_QQS_COUNT = "sysui_qqs_count_key";
    private static final String PREF_ROWS_PORTRAIT = "qs_rows_portrait";
    private static final String PREF_ROWS_LANDSCAPE = "qs_rows_landscape";
    private static final String PREF_COLUMNS = "qs_columns";

    private CustomSeekBarPreference mSysuiQqsCount;
    private CustomSeekBarPreference mRowsPortrait;
    private CustomSeekBarPreference mRowsLandscape;
    private CustomSeekBarPreference mQsColumns;

    @Override
    protected int getMetricsCategory() {
        return MetricsEvent.COLT;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        addPreferencesFromResource(R.xml.quick_settings);

	ContentResolver resolver = getActivity().getContentResolver();


        mSysuiQqsCount = (CustomSeekBarPreference) findPreference(PREF_SYSUI_QQS_COUNT);
        int SysuiQqsCount = Settings.Secure.getInt(resolver,
               Settings.Secure.QQS_COUNT, 5);
	mSysuiQqsCount.setValue(SysuiQqsCount);
	mSysuiQqsCount.setOnPreferenceChangeListener(this);

	 mRowsPortrait = (CustomSeekBarPreference) findPreference(PREF_ROWS_PORTRAIT);
        int rowsPortrait = Settings.Secure.getInt(resolver,
                Settings.Secure.QS_ROWS_PORTRAIT, 3);
        mRowsPortrait.setValue(rowsPortrait);
        mRowsPortrait.setOnPreferenceChangeListener(this);

        int defaultValue = getResources().getInteger(com.android.internal.R.integer.config_qs_num_rows_landscape_default);
        mRowsLandscape = (CustomSeekBarPreference) findPreference(PREF_ROWS_LANDSCAPE);
        int rowsLandscape = Settings.Secure.getInt(resolver,
                Settings.Secure.QS_ROWS_LANDSCAPE, defaultValue);
        mRowsLandscape.setValue(rowsLandscape);
        mRowsLandscape.setOnPreferenceChangeListener(this);

        mQsColumns = (CustomSeekBarPreference) findPreference(PREF_COLUMNS);
        int columnsQs = Settings.Secure.getInt(resolver,
                Settings.Secure.QS_COLUMNS, 3);
        mQsColumns.setValue(columnsQs);
	mQsColumns.setOnPreferenceChangeListener(this);

        }

    @Override
    public boolean onPreferenceChange(Preference preference, Object newValue) {
        ContentResolver resolver = getActivity().getContentResolver();

	int intValue;
	int index;

        if (preference == mSysuiQqsCount) {
	    int SysuiQqsCountValue = (Integer) newValue;
            Settings.Secure.putInt(resolver,
                   Settings.Secure.QQS_COUNT, SysuiQqsCountValue);
            return true;
        } else if (preference == mRowsPortrait) {
            intValue = (Integer) newValue;
            Settings.Secure.putInt(resolver,
                    Settings.Secure.QS_ROWS_PORTRAIT, intValue);
            return true;
        } else if (preference == mRowsLandscape) {
            intValue = (Integer) newValue;
            Settings.Secure.putInt(resolver,
                    Settings.Secure.QS_ROWS_LANDSCAPE, intValue);
            return true;
        } else if (preference == mQsColumns) {
            intValue = (Integer) newValue;
            Settings.Secure.putInt(resolver,
                    Settings.Secure.QS_COLUMNS, intValue);
            return true;
	}
        return false;
    }
}
