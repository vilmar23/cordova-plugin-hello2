var argscheck = require('cordova/argscheck'),
    utils = require('cordova/utils'),
    exec = require('cordova/exec');

var radioObjects = {};

var RadioAAC = function(src, successCallback, errorCallback, statusCallback) {
    this.id = utils.createUUID();
    this.src = src;
    this.successCallback = successCallback;
    this.errorCallback = errorCallback;
    this.statusCallback = statusCallback;
    this._duration = -1;
    this._position = -1;
    exec(null, this.errorCallback, "RadioAAC", "create", [this.src]);
};

// Radio messages
RadioAAC.MEDIA_STATE = 1;
RadioAAC.MEDIA_DURATION = 2;
RadioAAC.MEDIA_POSITION = 3;
RadioAAC.MEDIA_ERROR = 9;

// Radio states
RadioAAC.MEDIA_NONE = 0;
RadioAAC.MEDIA_STARTING = 1;
RadioAAC.MEDIA_RUNNING = 2;
RadioAAC.MEDIA_PAUSED = 3;
RadioAAC.MEDIA_STOPPED = 4;
RadioAAC.MEDIA_MSG = ["None", "Starting", "Running", "Paused", "Stopped"];


/**
 * Start or resume playing audio file.
 */
RadioAAC.prototype.play = function(url) {
    this.src = url;
    console.log(this.src);
    exec(null, null, "RadioAAC", "startPlayingAudio", [this.src]);
};

/**
 * Stop playing audio file.
 */
RadioAAC.prototype.stop = function() {
    var me = this;
    exec(function() {
        me._position = 0;
    }, this.errorCallback, "RadioAAC", "stopPlayingAudio", [this.id]);
};

/**
 * Seek or jump to a new time in the track..
 */
RadioAAC.prototype.seekTo = function(milliseconds) {
    var me = this;
    exec(function(p) {
        me._position = p;
    }, this.errorCallback, "RadioAAC", "seekToAudio", [this.id, milliseconds]);
};

/**
 * Pause playing audio file.
 */
RadioAAC.prototype.pause = function() {
    exec(null, this.errorCallback, "RadioAAC", "pausePlayingAudio", [this.id]);
};

/**
 * Seek or jump to a new time in the track..
 */
RadioAAC.prototype.reposo = function(milliseconds) {
    exec(null, null, "RadioAAC", "reposo", [milliseconds]);
};


RadioAAC.onStatus = function(id, msgType, value) {

    var radio = radioObjects[id];

    if(radio) {
        switch(msgType) {
            case RadioAAC.MEDIA_STATE :
                radio.statusCallback && radio.statusCallback(value);
                if(value == Media.MEDIA_STOPPED) {
                    radio.successCallback && radio.successCallback();
                }
                break;
            case RadioAAC.MEDIA_DURATION :
                radio._duration = value;
                break;
            case RadioAAC.MEDIA_ERROR :
                radio.errorCallback && radio.errorCallback(value);
                break;
            case RadioAAC.MEDIA_POSITION :
                radio._position = Number(value);
                break;
            default :
                console.error && console.error("Unhandled Media.onStatus :: " + msgType);
                break;
        }
    }
    else {
         console.error && console.error("Received RadioACC.onStatus callback for unknown radioaac :: " + id);
    }

};

module.exports = RadioAAC;

