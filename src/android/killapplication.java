package com.oceanex.killapplication;

import org.apache.cordova.CordovaPlugin;
import org.apache.cordova.CallbackContext;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import org.apache.cordova.LOG;
/**
* This class echoes a string called from JavaScript.
*/
public class killapplication extends CordovaPlugin {

@Override
public boolean execute(String action, JSONArray args, CallbackContext callbackContext) throws JSONException {

    LOG.v("CordovaActivity", "Gonna check action");

    if (action.equals("test")) {

        LOG.v("CordovaActivity", "action is test");

        String message = args.getString(0);
        this.test(message, callbackContext);
        return true;
    }
    return false;
}

private void test(String message, CallbackContext callbackContext) {

    LOG.v("CordovaActivity", "called test");

    if (message != null && message.length() > 0) {
        callbackContext.success(message);
    } else {
        callbackContext.error("Expected one non-empty string argument.");
    }
}
}