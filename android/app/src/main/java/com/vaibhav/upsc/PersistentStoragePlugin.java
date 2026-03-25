package com.vaibhav.upsc;

import android.content.Context;
import android.content.SharedPreferences;
import com.getcapacitor.JSObject;
import com.getcapacitor.Plugin;
import com.getcapacitor.PluginCall;
import com.getcapacitor.PluginMethod;
import com.getcapacitor.annotation.CapacitorPlugin;

@CapacitorPlugin(name = "PersistentStorage")
public class PersistentStoragePlugin extends Plugin {

    // Uses SharedPreferences with MODE_PRIVATE
    // Data stored in /data/data/com.vaibhav.upsc/shared_prefs/
    // Survives app updates but NOT uninstall by default
    // With backup enabled in manifest, survives uninstall too!
    
    private static final String PREFS_NAME = "VaibhavAppData";

    private SharedPreferences getPrefs() {
        return getContext().getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
    }

    @PluginMethod
    public void set(PluginCall call) {
        String key = call.getString("key");
        String value = call.getString("value");
        if (key == null || value == null) {
            call.reject("Key and value are required");
            return;
        }
        getPrefs().edit().putString(key, value).apply();
        JSObject result = new JSObject();
        result.put("success", true);
        call.resolve(result);
    }

    @PluginMethod
    public void get(PluginCall call) {
        String key = call.getString("key");
        if (key == null) {
            call.reject("Key is required");
            return;
        }
        String value = getPrefs().getString(key, null);
        JSObject result = new JSObject();
        result.put("value", value);
        result.put("success", value != null);
        call.resolve(result);
    }

    @PluginMethod
    public void remove(PluginCall call) {
        String key = call.getString("key");
        if (key == null) {
            call.reject("Key is required");
            return;
        }
        getPrefs().edit().remove(key).apply();
        JSObject result = new JSObject();
        result.put("success", true);
        call.resolve(result);
    }

    @PluginMethod
    public void clear(PluginCall call) {
        getPrefs().edit().clear().apply();
        JSObject result = new JSObject();
        result.put("success", true);
        call.resolve(result);
    }

    @PluginMethod
    public void keys(PluginCall call) {
        JSObject result = new JSObject();
        StringBuilder keys = new StringBuilder();
        for (String key : getPrefs().getAll().keySet()) {
            if (keys.length() > 0) keys.append(",");
            keys.append(key);
        }
        result.put("keys", keys.toString());
        call.resolve(result);
    }
}
