package com.example.plugin;

import org.apache.cordova.CordovaPlugin;
import org.apache.cordova.CallbackContext;
import org.apache.cordova.PluginResult;
import org.apache.cordova.CordovaInterface;
import org.apache.cordova.CordovaWebView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.BroadcastReceiver;
import android.net.ConnectivityManager;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.telephony.TelephonyManager;
import android.util.Log;

import com.spoledge.aacdecoder.MultiPlayer;


/**
 * This class echoes a string called from JavaScript.
 */
public class RadioAAC extends CordovaPlugin {

    private static MultiPlayer multiPlayer = null;

    private static boolean sonando = false;
    private static boolean restaurar = false;

    private String file = "http://livestreaming.esradio.fm/aaclive32";

    BroadcastReceiver receiver;
    private CallbackContext connectionCallbackContext;
    private boolean registered = false;
    ConnectivityManager sockMan;

    private static final String LOG_TAG = "RadioAAC";

    public void initialize(CordovaInterface cordova, CordovaWebView webView) {
        super.initialize(cordova, webView);
        this.sockMan = (ConnectivityManager) cordova.getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
        this.connectionCallbackContext = null;
        if(RadioAAC.multiPlayer==null)
        	RadioAAC.multiPlayer = new MultiPlayer();

        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("android.intent.action.PHONE_STATE");
        if (this.receiver == null) {
            this.receiver = new BroadcastReceiver() {
                @Override
                public void onReceive(Context context, Intent intent) {
                    if (intent.getStringExtra(TelephonyManager.EXTRA_STATE).equals(
                            TelephonyManager.EXTRA_STATE_RINGING)) {

                            RadioAAC.this.Llamada();
                            Log.d(LOG_TAG, "Recibiendo Llamada.");

                            // Ringing state
                            // This code will execute when the phone has an incoming call
                    } else if ( intent.getStringExtra(TelephonyManager.EXTRA_STATE).equals(
                                    TelephonyManager.EXTRA_STATE_OFFHOOK)) {

                            //RadioAAC.this.FinLlamada();
                            Log.d(LOG_TAG, "Llamada OFFHOOK.");

                    }else if ( intent.getStringExtra(TelephonyManager.EXTRA_STATE).equals(
                            TelephonyManager.EXTRA_STATE_IDLE)) {

		                    RadioAAC.this.FinLlamada();
		                    Log.d(LOG_TAG, "Llamada IDLE.");
		            }

                }
            };
            cordova.getActivity().registerReceiver(this.receiver, intentFilter);
            this.registered = true;
        }

    }

    public void onDestroy() {
        this.Stop();
        cordova.getActivity().unregisterReceiver(this.receiver);
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
