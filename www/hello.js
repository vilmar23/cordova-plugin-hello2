 window.hello = function(str, callback) {
        cordova.exec(callback, function(err) {
            callback('Nothing to echo.');
        }, "Hello", "echo", [str]);
    };






