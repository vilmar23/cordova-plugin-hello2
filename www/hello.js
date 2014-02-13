/*var exec = require('cordova/exec');

var Hello = function(src, successCallback, errorCallback, statusCallback) {

    this.src = src;
    this.successCallback = successCallback;
    this.errorCallback = errorCallback;

    exec(this.successCallback, this.errorCallback, "Hello", "greet", this.src);
} */


cordova.define("cordova/plugin/hello",
    function (require, exports, module) {


        function greet(name, win, fail) {
            exec(win, fail, "Hello", "greet", [name]);
        }

        module.exports = {
            greet: greet
        }
    }
); 
