package com.vaibhav.upsc;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.os.PowerManager;
import android.provider.Settings;
import android.view.WindowManager;
import com.getcapacitor.JSObject;
import com.getcapacitor.Plugin;
import com.getcapacitor.PluginCall;
import com.getcapacitor.PluginMethod;
import com.getcapacitor.annotation.CapacitorPlugin;

@CapacitorPlugin(name = "FocusLock")
public class FocusLockPlugin extends Plugin {

    private PowerManager.WakeLock wakeLock;

    @PluginMethod
    public void startFocusLock(PluginCall call) {
        Activity activity = getActivity();
        activity.runOnUiThread(() -> {
            try {
                activity.getWindow().addFlags(
                    WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON |
                    WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED |
                    WindowManager.LayoutParams.FLAG_TURN_SCREEN_ON
                );
                PowerManager pm = (PowerManager) activity.getSystemService(Context.POWER_SERVICE);
                if (pm != null && (wakeLock == null || !wakeLock.isHeld())) {
                    wakeLock = pm.newWakeLock(
                        PowerManager.SCREEN_BRIGHT_WAKE_LOCK | PowerManager.ON_AFTER_RELEASE,
                        "vaibhav:focuslock"
                    );
                    wakeLock.acquire(3 * 60 * 60 * 1000L);
                }
                try { activity.startLockTask(); } catch(Exception ignored){}
                JSObject result = new JSObject();
                result.put("success", true);
                call.resolve(result);
            } catch (Exception e) {
                JSObject result = new JSObject();
                result.put("success", false);
                call.resolve(result);
            }
        });
    }

    @PluginMethod
    public void stopFocusLock(PluginCall call) {
        Activity activity = getActivity();
        activity.runOnUiThread(() -> {
            try {
                activity.getWindow().clearFlags(
                    WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON |
                    WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED |
                    WindowManager.LayoutParams.FLAG_TURN_SCREEN_ON
                );
                if (wakeLock != null && wakeLock.isHeld()) {
                    wakeLock.release();
                    wakeLock = null;
                }
                try { activity.stopLockTask(); } catch(Exception ignored){}
                JSObject result = new JSObject();
                result.put("success", true);
                call.resolve(result);
            } catch (Exception e) {
                JSObject result = new JSObject();
                result.put("success", false);
                call.resolve(result);
            }
        });
    }

    @PluginMethod
    public void openSecuritySettings(PluginCall call) {
        Activity activity = getActivity();
        // Open Security Settings directly
        try {
            Intent intent = new Intent(Settings.ACTION_SECURITY_SETTINGS);
            activity.startActivity(intent);
            call.resolve();
        } catch (Exception e) {
            // Fallback to general settings
            try {
                Intent intent = new Intent(Settings.ACTION_SETTINGS);
                activity.startActivity(intent);
                call.resolve();
            } catch (Exception e2) {
                call.reject("Cannot open settings");
            }
        }
    }

    // Keep old method name for compatibility
    @PluginMethod
    public void openAccessibilitySettings(PluginCall call) {
        openSecuritySettings(call);
    }
}
