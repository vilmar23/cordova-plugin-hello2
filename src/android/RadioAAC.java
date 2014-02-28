package com.example.plugin;

import org.apache.cordova.CordovaPlugin;
import org.apache.cordova.CallbackContext;
import org.apache.cordova.PluginResult;
import org.apache.cordova.CordovaInterface;
import org.apache.cordova.CordovaWebView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.spoledge.aacdecoder.MultiPlayer;


/**
 * This class echoes a string called from JavaScript.
 */
public class RadioAAC extends CordovaPlugin {

    private static MultiPlayer multiPlayer = null;

    private String file = "http://livestreaming.esradio.fm/aaclive32";

    private static final String LOG_TAG = "RadioAAC";

    private static boolean sonando = false;
    private static boolean restaurar = false;

    public void initialize(CordovaInterface cordova, CordovaWebView webView) {
        super.initialize(cordova, webView);

        if(RadioAAC.multiPlayer==null)
        	RadioAAC.multiPlayer = new MultiPlayer();

    }

    public void onDestroy() {
        this.Stop();
    }


    @Override
    public boolean execute(String action, JSONArray args, CallbackContext callbackContext) throws JSONException {
        PluginResult.Status status = PluginResult.Status.OK;
        String result = "";

        if (action.equals("startPlayingAudio")) {
            this.Play(args.getString(0));
        }
        else if (action.equals("stopPlayingAudio")) {

            this.Stop();

        }else if (action.equals("create")) {

        }
        return false;
    }

    public void Play(String url) {
    	this.Stop();
        if(!RadioAAC.sonando){
            if((url == null) || (url.equals(""))) url =  this.file;
            else this.file = url;
        	RadioAAC.multiPlayer.playAsync(this.file);
        	RadioAAC.sonando = true;
        }
    }

    public void Stop() {
       RadioAAC.multiPlayer.stop();
       RadioAAC.sonando = false;
    }

    public void Llamada() {
        if(RadioAAC.sonando){
        	RadioAAC.restaurar = true;
            this.Stop();
        }
    }

    public void FinLlamada() {
        if(RadioAAC.restaurar){
        	RadioAAC.restaurar = false;
            this.Play(this.file);
        }
    }
}
