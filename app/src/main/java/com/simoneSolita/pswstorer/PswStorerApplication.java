package com.simonesolita.pswstorer;

import android.app.Activity;
import android.content.pm.ActivityInfo;
import android.os.Bundle;

import androidx.multidex.MultiDexApplication;

public class PswStorerApplication extends MultiDexApplication {

    private static Activity currentActivity;

    public static Activity getCurrentActivity() {
        return currentActivity;
    }

    public static void setCurrentActivity(Activity currentActivity) {
        PswStorerApplication.currentActivity = currentActivity;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        // <------INIZIO GESTIONE DELLA ROTAZIONE PER FORZARLA IN PORTRAIT ANCHE CON LA ROTAZIONE ATTIVA------> //
        // register to be informed of activities starting up
        registerActivityLifecycleCallbacks(new ActivityLifecycleCallbacks() {
            @Override
            public void onActivityCreated(Activity activity,
                                          Bundle savedInstanceState) {

                // new activity created; force its orientation to portrait
                activity.setRequestedOrientation(
                        ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
                setCurrentActivity(activity);
            }

            // override vuoti necessari a creare ActivityLifecycleCallbacks
            @Override
            public void onActivityDestroyed(Activity activity) {
            }

            @Override
            public void onActivityResumed(Activity activity) {
                setCurrentActivity(activity);
            }

            @Override
            public void onActivitySaveInstanceState(Activity activity, Bundle savedInstanceState) {
            }

            @Override
            public void onActivityStarted(Activity activity) {
                setCurrentActivity(activity);
            }

            @Override
            public void onActivityStopped(Activity activity) {
            }

            @Override
            public void onActivityPaused(Activity activity) {
            }

        });
    }
}
