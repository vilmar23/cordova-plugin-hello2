package com.example.radioaac;

import org.apache.cordova.CordovaPlugin;
import org.apache.cordova.CallbackContext;
import org.apache.cordova.PluginResult;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.spoledge.aacdecoder.MultiPlayer;
import com.spoledge.aacdecoder.AACPlayer;


/**
 * This class echoes a string called from JavaScript.
 */
public class RadioAAC extends CordovaPlugin {

    private AACPlayer multiPlayer = null;

    @Override
    public boolean execute(String action, JSONArray args, CallbackContext callbackContext) throws JSONException {
        PluginResult.Status status = PluginResult.Status.OK;
        String result = "";

        if (action.equals("startPlayingAudio")) {

            this.multiPlayer.playAsync("http://livestreaming.esradio.fm/aaclive32");

        }
        else if (action.equals("stopPlayingAudio")) {
          
            this.multiPlayer.stop();

        }else if (action.equals("create")) {

            AACPlayer aacPlayer = new AACPlayer();
            this.multiPlayer = aacPlayer;

        }
        return false;
    }

}

