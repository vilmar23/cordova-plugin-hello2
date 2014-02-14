package com.example.plugin;

import org.apache.cordova.CordovaPlugin;
import org.apache.cordova.CallbackContext;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import com.spoledge.aacdecoder.MultiPlayer;
import com.spoledge.aacdecoder.PlayerCallback;


/**
 * This class echoes a string called from JavaScript.
 */
public class RadioAAC extends CordovaPlugin {

    @Override
    public boolean execute(String action, JSONArray args, CallbackContext callbackContext) throws JSONException {
        PluginResult.Status status = PluginResult.Status.OK;
        String result = "";

        if (action.equals("startPlayingAudio")) {
            multiPlayer.playAsync("http://livestreaming.esradio.fm/aaclive32");
        }
        else if (action.equals("stopPlayingAudio")) {
            this.multiPlayer.stop();
        }else if (action.equals("create")) {

            this.multiPlayer = new MultiPlayer( clb);

        }
        return false;
    }

    PlayerCallback clb = new PlayerCallback() {
        public void playerStarted() {  }
        public void playerPCMFeedBuffer(boolean isPlaying, int bufSizeMs, int bufCapacityMs) {}
        public void playerStopped( int perf ) {  }
        public void playerException( Throwable t) {  }
        public void playerMetadata( String key, String value ) {  }
    };

    /**
     * Start or resume playing audio file.
     * @param id				The id of the audio player
     * @param file				The name of the audio file.
     */
    public void startPlayingAudio(String id, String file) {
        /* AudioPlayer audio = this.players.get(id);
        if (audio == null) {
            audio = new AudioPlayer(this, id, file);
            this.players.put(id, audio);
        }
        audio.startPlaying(file);  */
    }

    /**
     * Pause playing.
     * @param id				The id of the audio player
     */
    public void pausePlayingAudio(String id) {
       /* AudioPlayer audio = this.players.get(id);
        if (audio != null) {
            audio.pausePlaying();
        }  */
    }

    /**
     * Stop playing the audio file.
     * @param id				The id of the audio player
     */
    public void stopPlayingAudio(String id) {
      /*  AudioPlayer audio = this.players.get(id);
        if (audio != null) {
            audio.stopPlaying();
            //audio.destroy();
            //this.players.remove(id);
        }     */
    }
}

