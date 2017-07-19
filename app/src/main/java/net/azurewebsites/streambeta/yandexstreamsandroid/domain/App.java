package net.azurewebsites.streambeta.yandexstreamsandroid.domain;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatDelegate;

import net.azurewebsites.streambeta.yandexstreamsandroid.R;
import net.danlew.android.joda.JodaTimeAndroid;

import uk.co.chrisjenx.calligraphy.CalligraphyConfig;

/**
 * Created by tetawex on 12.07.17.
 */

public class App extends Application {
    private static App appInstance;
    private static PresenterProvider presenterProvider;
    private static SharedPreferences sharedPreferences;

    private static String SHARED_PREFERENCES_CODE;

    @Override
    public void onCreate() {
        super.onCreate();
        appInstance = this;
        getPresenterProvider();
        getSharedPreferences();
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);
        JodaTimeAndroid.init(this);

        CalligraphyConfig.initDefault(new CalligraphyConfig.Builder()
                .setDefaultFontPath("minecraft.ttf")
                .setFontAttrId(R.attr.fontPath)
                .build());
    }

    public static Context getContext() {
        return appInstance.getApplicationContext();
    }

    public static PresenterProvider getPresenterProvider() {
        if (presenterProvider == null)
            presenterProvider = new PresenterProvider();
        return presenterProvider;
    }

    public static SharedPreferences getSharedPreferences() {
        if (sharedPreferences == null)
            sharedPreferences = getContext().getSharedPreferences(SHARED_PREFERENCES_CODE, 0);
        return sharedPreferences;
    }
}
