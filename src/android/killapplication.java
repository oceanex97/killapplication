package com.oceanex.plugins;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.ActivityManager;
import android.app.ActivityManager.AppTask;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.PowerManager;
import android.view.View;
import android.view.Window;

import org.apache.cordova.CallbackContext;
import org.apache.cordova.CordovaActivity;
import org.apache.cordova.CordovaInterface;
import org.apache.cordova.CordovaPlugin;
import org.apache.cordova.CordovaWebView;
import org.apache.cordova.PluginResult;
import org.apache.cordova.PluginResult.Status;

import java.lang.ref.WeakReference;
import java.util.List;

import static android.content.Context.ACTIVITY_SERVICE;
import static android.content.Context.POWER_SERVICE;
import static android.view.WindowManager.LayoutParams.FLAG_ALLOW_LOCK_WHILE_SCREEN_ON;
import static android.view.WindowManager.LayoutParams.FLAG_DISMISS_KEYGUARD;
import static android.view.WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED;
import static android.view.WindowManager.LayoutParams.FLAG_TURN_SCREEN_ON;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.util.Log;

public class Killapplication extends CordovaPlugin {

    @Override
    public boolean execute(String p_action, JSONArray p_args, CallbackContext p_callbackContext) throws JSONException {
        if (p_action.equals("test")) {

            Log.i("oceanexLog", "action is test");

            String w_message = p_args.getString(0);

            this.f_test(w_message, p_callbackContext);
            return true;
        }
        if (p_action.equals("kill")) {
            this.f_kill(p_callbackContext);
            return true;
        }
        return false;
    }

    private void f_kill(CallbackContext p_callbackContext) {
        try {

            this.webView.getPluginManager().postMessage("exit", null); // normal cordova exit sent to all plugins

            // System.exit(0)         // Suggestions de Michael
            // this.finishAffinity()

            Context w_ctx = f_getApp().getApplicationContext();
            w_ctx.stopService(new Intent(w_ctx, CordovaActivity.class));

            // f_getApp().finish();   // method used in exit-app plugin

            ActivityManager w_am = (ActivityManager) f_getService(ACTIVITY_SERVICE);
            w_am.killBackgroundProcesses(f_getApp().getPackageName()); // void killBackgroundProcesses (String packageName)

            p_callbackContext.success("kill success.");
        } catch (Exception p_error) {
            p_callbackContext.error("kill error");
        }
    }

    private void f_test(String p_message, CallbackContext p_callbackContext) {
        Log.i("oceanexLog", "called test");
        if (p_message != null && p_message.length() > 0) {
            p_callbackContext.success("Test Success. Message : " + p_message);
        } else {
            p_callbackContext.error("Expected one non-empty string argument.");
        }
    }

    // ****************************************************
    //                       UTILITIES
    // ****************************************************
    Activity f_getApp() {
        return this.cordova.getActivity();
    }

    /* The launch intent for the main activity.  */
    private Intent f_getLaunchIntent() {
        Context w_app     = f_getApp().getApplicationContext();
        String  w_pkgName = w_app.getPackageName();
        return w_app.getPackageManager().getLaunchIntentForPackage(w_pkgName);
    }

    /* Get the requested system service by name. */
    private Object f_getService(String p_name) {
        return f_getApp().getSystemService(p_name);
    }

}