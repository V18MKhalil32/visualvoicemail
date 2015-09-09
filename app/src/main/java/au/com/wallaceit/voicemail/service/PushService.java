package au.com.wallaceit.voicemail.service;

import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;
import au.com.wallaceit.voicemail.VisualVoicemail;
import au.com.wallaceit.voicemail.service.*;
import au.com.wallaceit.voicemail.service.CoreService;

public class PushService extends CoreService {
    private static String START_SERVICE = "au.com.wallaceit.voicemail.service.PushService.startService";
    private static String STOP_SERVICE = "au.com.wallaceit.voicemail.service.PushService.stopService";

    public static void startService(Context context) {
        Intent i = new Intent();
        i.setClass(context, PushService.class);
        i.setAction(PushService.START_SERVICE);
        addWakeLock(context, i);
        context.startService(i);
    }

    public static void stopService(Context context) {
        Intent i = new Intent();
        i.setClass(context, au.com.wallaceit.voicemail.service.PushService.class);
        i.setAction(au.com.wallaceit.voicemail.service.PushService.STOP_SERVICE);
        addWakeLock(context, i);
        context.startService(i);
    }

    @Override
    public int startService(Intent intent, int startId) {
        int startFlag = START_STICKY;
        if (START_SERVICE.equals(intent.getAction())) {
            if (VisualVoicemail.DEBUG)
                Log.i(VisualVoicemail.LOG_TAG, "PushService started with startId = " + startId);
        } else if (STOP_SERVICE.equals(intent.getAction())) {
            if (VisualVoicemail.DEBUG)
                Log.i(VisualVoicemail.LOG_TAG, "PushService stopping with startId = " + startId);
            stopSelf(startId);
            startFlag = START_NOT_STICKY;
        }

        return startFlag;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        setAutoShutdown(false);
    }


    @Override
    public IBinder onBind(Intent arg0) {
        // TODO Auto-generated method stub
        return null;
    }
}
