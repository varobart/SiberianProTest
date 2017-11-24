package pro.siberian.siberianproapplication;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

/**
 * Created by Вараздат on 19.11.2017.
 */


public class WeatherReceiver extends BroadcastReceiver {

    public final static String SERVICE_RESTART_ACTION = "pro.siberian.siberianproapplication.ActivityRecognition.ServiceRestart";


    @Override
    public void onReceive(Context context, Intent intent) {
        //starts service after stopping
        context.startService(new Intent(context, WeatherService.class));;
    }
}
