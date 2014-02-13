window.hello = function(str, callback, errorcallback) {
        cordova.exec(callback, errorcallback, "Hello", "greet", [str]);
    };





