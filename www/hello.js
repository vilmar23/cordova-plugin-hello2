var exec = require('cordova/exec');

var Hello = function(src, successCallback, errorCallback, statusCallback) {

    this.src = src;
    this.successCallback = successCallback;
    this.errorCallback = errorCallback;

    exec(this.successCallback, this.errorCallback, "Hello", "greet", this.src);
}




