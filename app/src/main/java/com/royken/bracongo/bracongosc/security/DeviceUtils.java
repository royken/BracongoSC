package com.royken.bracongo.bracongosc.security;

import android.content.Context;
import android.provider.Settings;

import java.io.File;

public class DeviceUtils {

    public String getDeviceId(Context context){
        String android_id = Settings.Secure.getString(context.getContentResolver(), Settings.Secure.ANDROID_ID);
        return android_id;
    }

    public Boolean isDeviceRooted(Context context){
        return  isrooted1() || isrooted2() || isrooted3();
    }

    private  boolean isrooted1() {
        String buildTags = android.os.Build.TAGS;
        return buildTags != null && buildTags.contains("test-keys");
    }

   /* private boolean isrooted2() {
        String[] paths = { "/system/app/Superuser.apk", "/sbin/su", "/system/bin/su", "/system/xbin/su",
                "/data/local/xbin/su", "/data/local/bin/su", "/system/sd/xbin/su",
                "/system/bin/failsafe/su", "/data/local/su", "/su/bin/su"};
        for (String path : paths) {
            if (new File(path).exists()) return true;
        }
        return false;
    }
*/
    private boolean isrooted2() {

        File file = new File("/system/app/Superuser.apk");
        if (file.exists()) {
            return true;
        }
        return false;
    }

    // try executing commands
    private boolean isrooted3() {
        return canExecuteCommand("/system/xbin/which su")
                || canExecuteCommand("/system/bin/which su")
                || canExecuteCommand("which su");
    }

    private static boolean canExecuteCommand(String command) {
        boolean executedSuccesfully;
        try {
            Runtime.getRuntime().exec(command);
            executedSuccesfully = true;
        } catch (Exception e) {
            executedSuccesfully = false;
        }

        return executedSuccesfully;
    }
}
